package com.firenay.mall.member.exception;

/**
 * <p>Title: PhoneExistException</p>
 * Description：
 * date：2020/6/25 19:17
 */
public class PhoneExistException extends RuntimeException {
	public PhoneExistException() {
		super("手机号存在");
	}
}
