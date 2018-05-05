package net.conallen.fsmdsl;

public class State {
	
	private boolean isInitial = false;
	private boolean isFinal = false;
	private int id = 0;
	private String name = null;
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		if( isInitial ) {
			sb.append(">");
		}
		
		if( isFinal ) {
			sb.append("[");
			sb.append(name);
			sb.append("]");
		} else {
			sb.append("(");
			sb.append(name);
			sb.append(")");
		}
		
		return sb.toString();
	}
	
	public State(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isFinal() {
		return isFinal;
	}
	public void setFinal(boolean finalState) {
		this.isFinal = finalState;
	}
	public boolean isInitial() {
		return isInitial;
	}
	public void setInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
