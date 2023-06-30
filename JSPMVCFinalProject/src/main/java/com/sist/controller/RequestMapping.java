/*
		@ → TYPE
		class class_name
		{
			@ → FIELD
			private A a;
			
			@ → CONSTRUCTOR
			public class_name(){
			}
			@METHOD
			public void display(){
			}
		}
		
		@RequestMapping("list.do") → 구분자
		public void display1(){}
		@RequestMapping
		public void display2(){}
		@RequestMapping
		public void display3(){}
 */
package com.sist.controller;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface RequestMapping {
	public String value();
}
