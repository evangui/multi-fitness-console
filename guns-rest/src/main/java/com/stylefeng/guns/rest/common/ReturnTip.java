package com.stylefeng.guns.rest.common;

public class ReturnTip {
	protected int cn;
	protected int code;
    protected String message;
    protected Object data;
    
    public ReturnTip(int cn, String message) {
        this.cn = cn;
        this.code = cn;
        this.message = message;
    }
    
    public ReturnTip(int cn, String message, Object data) {
        this.cn = cn;
        this.code = cn;
        this.data = data;
        this.message = message;
    }
    
    public int getCn() {
		return cn;
	}
	public void setCn(int cn) {
		this.cn = cn;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
