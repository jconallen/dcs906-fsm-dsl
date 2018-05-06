package net.conallen.fsmdsl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.conallen.fsmdsl.FsmParser.End_stateContext;
import net.conallen.fsmdsl.FsmParser.MachineContext;
import net.conallen.fsmdsl.FsmParser.Start_stateContext;
import net.conallen.fsmdsl.FsmParser.StatementContext;

public class Machine extends FsmBaseListener {
	
	public List<Transition> transitions = new ArrayList<Transition>();
	public Map<String,State> states = new HashMap<String,State>();
	public State initialState = null;

	
	private State getState( String name, boolean isFinal ) {
		State state = states.get(name);
		if(state == null ) {
			// we need to add a new one
			state = new State(name);
			state.setId( states.size() );
			states.put(name, state);
		}
		if( isFinal) {
			state.setFinal(true);
		}
		if( initialState == null ) {
			initialState = state;
			state.setInitial(true);
		}
		return state;
	}
	
	public List<Transition> compile(File inFile) throws Exception {
		transitions = new ArrayList<Transition>();
		states = new HashMap<String,State>();
		initialState = null;
		
		CharStream cs = CharStreams.fromPath(inFile.toPath());
        FsmLexer lexer = new FsmLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        FsmParser parser = new FsmParser(tokens);
        MachineContext machineCtx = parser.machine();
        try {
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(this, machineCtx);
        } catch( Exception re ) {
        	throw new Exception("Error encountered when parsing.");
        }
        
        return transitions;
	}
	
	@Override
    public void enterStatement(StatementContext statementCtx) {
		
		State start = null;
		State end = null;
		
		Start_stateContext startCtx = statementCtx.start_state();
		if( startCtx.state().intermediate_state() != null ) {
			String name = startCtx.state().intermediate_state().state_name().getText();
			start = getState(name, false);
		} else if( startCtx.state().final_state() != null ) {
			String name = startCtx.state().final_state().state_name().getText();
			start = getState(name, true);
		}
		
		End_stateContext endCtx = statementCtx.end_state();
		if( endCtx.state().intermediate_state() != null ) {
			String name = endCtx.state().intermediate_state().state_name().getText();
			end = getState(name, false);
		} else if( endCtx.state().final_state() != null ) {
			String name = endCtx.state().final_state().state_name().getText();
			end = getState(name, true);
		}

		String input = statementCtx.transition().input().getText();
		
		Transition transition = new Transition(start, input, end);
		transitions.add(transition);
		
       
    }
	
	public int stateCount() {
		return this.states.size();
	}
	
	public int transitionCount() {
		return this.transitions.size();
	}
	
	
}
