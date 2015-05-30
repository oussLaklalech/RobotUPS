package datatype;

public class Position {

	private int posX;
	private int posY;
	
	public Position(int x, int y){
		posX = x;
		posY = y;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public String toString(){
		return "X = "+posX+" | Y = "+posY;
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Position))return false;
	    Position otherMyClass = (Position)other;
	    if(otherMyClass.getPosX() == posX && otherMyClass.getPosY() == posY)
	    	return true;
	    else 
	    	return false;
	}
	
}

