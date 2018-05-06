package net.conallen.fsmdsl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CompilerMain {

	public static void main(String[] args) throws IOException {

		
		// create the command line parser
		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption( "h", "help", false, "display help" );
		options.addOption( "i", "input", true, "the file or directory of files with .fsm extension to compile" );
		options.addOption( "o", "output", true, "the directory to put the .jff output files" );

		try {
		    // parse the command line arguments
		    CommandLine line = parser.parse( options, args );
		    
			File input = null;
			File output = null;

		    // validate that block-size has been set
		    if( line.hasOption( 'h' ) ) {
		    	HelpFormatter formatter = new HelpFormatter();
		    	formatter.printHelp( "fsm2jff", options );
		    } else {
		    	
		    	if( line.hasOption('i') ) {
		    		String i = line.getOptionValue('i');
		    		input = new File(i);
		    		if( input.exists() ) {
		    			System.out.println( "input exists");
		    		} else {
		    			System.out.println( "Cannot file input: " + i);
		    		}
		    	} else {
		    		input = new File(".");
		    	}
		    	
		    	if( line.hasOption('o') ) {
		    		String o = line.getOptionValue('o');
		    		output = new File(o);
		    		if( output.exists() && output.isFile() ) {
		    			System.out.println( "Output exists as a file, not directory");
		    		} 
		    	} else {
		    		output = new File(".");
		    	}
		    }
		    
			processInput( input, output );

		}
		catch( ParseException exp ) {
		    System.out.println( "Unexpected exception:" + exp.getMessage() );
		}


	}

	
	static private void processInput(File input, File output) throws IOException {
		if( input.isDirectory() ) {
			String[] files = input.list(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					String lowercaseName = name.toLowerCase();
					if (lowercaseName.endsWith(".fsm")) {
						return true;
					} else {
						return false;
					}
				}
			});
			
			System.out.println( "Input directory: " + input.getAbsolutePath() + "\n" );
			
			for(String filename : files ) {
				File inFile = new File(filename);
				try {
					processFile(inFile, output);
				} catch( Exception ex ) {
					System.err.println( "Problems processing file " + inFile.getName() );
				}
			}
			
		} else {
			try {
				processFile(input, output);
			} catch( Exception ex ) {
				System.err.println( "Problems processing file " + input.getName() + "[" + ex.getMessage() + "]");
			}
		}
	}
	
	static private void processFile(File input, File output) throws Exception {
		System.out.println( "Processing input file: " + input.getName() );
		String inputFilename = input.getName();
		String outputFilename = null;
		if( inputFilename.endsWith(".fsm") ) {
			outputFilename = inputFilename.substring(0,inputFilename.length()-3) + "jff";
		} else {
			outputFilename = inputFilename + ".jff";
		}
		
		Machine machine = new Machine();
		machine.compile( input );
		
		System.out.println( "\tNumber States: " + machine.stateCount() + ", Transitions: " + machine.transitionCount() );
		
		File outFile = new File( output.getAbsolutePath(), outputFilename);
		JflapWriter writer = new JflapWriter();
		writer.writeFile(outFile, machine.transitions);
		
		System.out.println("\tFile saved!");
		
	}
	

}
