package com.dyman.im.constant;


public enum RequestResultEnum {

	Error(400,"服务端错误" ),
	SUCCESS(200,"操作成功!" ),
    SHOP_ERROR_ARGS(421, "参数错误");


	private int code;
	private String msg;
	RequestResultEnum(int code, String msg){
		this.code=code;
		this.msg=msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
