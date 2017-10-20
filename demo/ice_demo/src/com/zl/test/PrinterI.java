package com.zl.test;

import Ice.Current;

public class PrinterI extends Demo._PrinterDisp{

	@Override
	public void printString(String s, Current __current) {
		System.out.println(s);
	}
	
}
