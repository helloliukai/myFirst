package com.example.elts_biz;
/**
 * 用于登陆验证，当编号输入错误时候，抛出：请先注册的异常
 * 密码输入错误时，抛出密码错误的异常
 * @author liukai
 *
 */
public class IdOrPasswordExreption extends Exception {
	public IdOrPasswordExreption(String message){
		super(message);
	}

	
}
