package net.conallen.fsmdsl;

public class Transition {
	
	public Transition(State start, String input, State end) {
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
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	private State start;
	private State end;
	private String input;

}
