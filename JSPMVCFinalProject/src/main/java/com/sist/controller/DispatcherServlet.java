/*
		Controller : Servlet → 최대한 고정
		  Spring : DispatcherServlet
		  Struts : ActionServlet
		  Struts2 : FilerDispatcher
		Model : 요청 처리 → java
		View : 화면 출력 → jsp
		
		MVC 동작 과정
		  1. 사용자가 링크나 버튼을 클릭해서 요청(~.do)
		  2. DispatcherServlet이 URI, URL을 받아 Model과 연결
		  3. Model이 결과값을 request에 받아 전송
		  
		MVC 목적
		  보안
		  역할 분담
		  자바와 HTML을 분리하는 이유 : 확장성, 재사용, 변경이 쉽다(JSP는 한번 사용하면 버림)	  
 */
package com.sist.controller;

import java.io.*;
import java.lang.reflect.Method;
import java.net.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> clsList = new ArrayList<String>();
	
	
	// 초기화 → XML에 등록된 클래스 읽기
	public void init(ServletConfig config) throws ServletException {
		try {
			URL url = this.getClass().getClassLoader().getResource(".");
			File file = new File(url.toURI());
			String path = file.getPath();
			path = path.replace("\\", file.separator);
			path = path.substring(0, path.lastIndexOf(file.separator));
			path = path+File.separator+"application.xml";
			
			// XML 파싱
			// 파서기 XML → DocumentBuilder / HTML → Jsoup
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			org.w3c.dom.Document doc = db.parse(new File(path));
			org.w3c.dom.Element beans = doc.getDocumentElement();
			
			// 같은 태그를 묶어서 사용
			NodeList list = beans.getElementsByTagName("bean");
			
			for(int i=0;i<list.getLength();i++) {
				// bean 태그 1개씩 가지고 오기
				org.w3c.dom.Element bean = (org.w3c.dom.Element)list.item(i);
				String id = bean.getAttribute("id");
				String cls = bean.getAttribute("class");
				System.out.println(id+":"+cls);
				clsList.add(cls);
			}
			
		}catch(Exception ex) {}
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String path = request.getRequestURI();
			path = path.substring(request.getContextPath().length()+1);
			// http://localhost
			// /JSPMVCFinalProeject/food/category.do
			// food/category.do
			
			for(String cls:clsList) {
				// 클래스 정보 읽기
				Class clsName = Class.forName(cls);
				Object obj = clsName.getDeclaredConstructor().newInstance();
				Method[] methods = clsName.getDeclaredMethods();
				
				for(Method m:methods) {
					RequestMapping rm = m.getAnnotation(RequestMapping.class);
					if(rm.value().equals(path)) {
						String jsp = (String)m.invoke(obj, request, response);
						if(jsp==null)
						{
							return;
						}
						else if(jsp.startsWith("redirect:")) {
							response.sendRedirect(jsp.substring(jsp.indexOf(":")+1));
						}
						else {
							RequestDispatcher rd = request.getRequestDispatcher(jsp);
							rd.forward(request, response);
						}
						// for문 종료
						return;
					}
				}
				
			}
			
		} catch(Exception ex) {}
	}

}
