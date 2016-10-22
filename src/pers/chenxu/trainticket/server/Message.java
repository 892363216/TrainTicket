package pers.chenxu.trainticket.server;


public class Message implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private  String info;
	
	
	public Message(int type,String info){
	this.type=type;
	this.info=info;
	}
	
	public Message(){}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	};
	
	
	
	
}

