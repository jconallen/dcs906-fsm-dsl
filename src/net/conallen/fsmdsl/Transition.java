package net.conallen.fsmdsl;

public class Transition {
	
	public Transition(State start, char input, State end) {
		this.start = start;
		this.input = input;
		this.end = end;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append( start.toString() );
		sb.append( " -" );
		sb.append( input );
		sb.append( "-> " );
		sb.append( end.toString() );
		
		return sb.toString();
	}
	
	public State getStart() {
		return start;
	}
	public void setStart(State start) {
		this.start = start;
	}
	public State getEnd() {
		return end;
	}
	public void setEnd(State end) {
		this.end = end;
	}
	public char getInput() {
		return input;
	}
	public void setInput(char input) {
		this.input = input;
	}
	private State start;
	private State end;
	private char input;

}
