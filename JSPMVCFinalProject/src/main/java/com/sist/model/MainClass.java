package com.sist.model;

import java.lang.reflect.Method;
import java.util.Scanner;
import com.sist.controller.*;

class Board {
	@RequestMapping("list.do")
	public void boardList() {
		System.out.println("목록 출력~~");
	}
	@RequestMapping("insert.do")
	public void boardInsert() {
		System.out.println("데이터 추가~~");
	}
	@RequestMapping("update.do")
	public void boardUpdate() {
		System.out.println("데이터 수정~~");
	}
	@RequestMapping("delete.do")
	public void boardDelete() {
		System.out.println("데이터 삭제~~");
	}
	@RequestMapping("find.do")
	public void boardFind() {
		System.out.println("데이터 찾기~~");
	}
}

public class MainClass {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("URL 입력 : ");
		String url = scan.next();
		
		try {
			Class clsName = Class.forName("com.sist.model.Board");
			Object obj = clsName.getDeclaredConstructor().newInstance();
			Method[] methods = clsName.getDeclaredMethods();
			
			for(Method m:methods) {
				// 메소드 위에 어노테이션 확인
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				if(rm.value().equals(url))
				{
					m.invoke(obj, null);
				}
			}
		}catch(Exception ex) {}
		
	}

}
