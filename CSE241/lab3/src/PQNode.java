package lab3;

public class PQNode<T> {
	private int key;
	private Handle h;
	private T value;
	
	public PQNode(int key, T value){
		this.key = key;
		this.value=value;
		h = new Handle(-1);
	}
	
	public int getKey(){
		return this.key;
	}
	public void setKey(int x){
		this.key = x;
	}
	public T getValue(){
		return this.value;
	}
	public Handle getHandle(){
		return this.h;
	}
}