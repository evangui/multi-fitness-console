package com.stylefeng.guns.rest.common.exception;

/**
 * 验证码错误异常
 *
 * @author guiyj007
 * @date 2018-08-05 23:52
 */
public class BizException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -193388984228301427L;

	private Integer code;

    private String message;

    public BizException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
