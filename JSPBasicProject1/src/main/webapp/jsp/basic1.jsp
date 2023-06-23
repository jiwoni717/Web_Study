<%--
		1. 구성요소
		  Client : 브라우저
		  Server : WebServer / WAS
		  
		2. JSP 동작 과정
		  1) 사용자 요청
		    브라우저 주소창만 이용 가능
		    http://localhost:8080/프로젝트명/폴더/파일명.jsp → 서버에 접근
		  2) 확장자
		    .html / .css / .json → 웹서버에서 직접 처리
		    .jsp
		      브라우저에서는 일반 텍스트파일로 인식하기 때문에 자바로 변환(톰캣)
		    
		  (WAS → TOMCAT, 형상관리 → GIT)
		      
		3. Servlet VS JSP
		  - 웹 서비스 기능(사용자가 요청하면 HTML로 변환해서 화면 출력)
		  1) Servlet
		    - 변경할 때마다 컴파일을 해야함
		    - HTML을 자바안에서 코딩
		    - CSS를 구사하기 어려움
		    ↓ 단점 보완(HTML을 쉽게 다루도록)
		  2) JSP
		    - HTML과 자바가 분리되어 있음
		    - 컴파일 하지 않고 바로 확인이 가능
		    
				servlet
				   │
				 init()
				   │
			    service()
			       │
			 ┌─────┴─────┐
		  doGet()     doPost()
		  
		  
		          JSP
		           │
		       _jspInit() → web.xml : 저장된 내용 읽어서 저장
		           │                  에러코드, 서블릿 저장, 환경 설정 위치, 한글변환...
		     _jspService() : 사용자 요청 처리 결과
		           │
		     _jspDestroy() : 페이지 이동, 새로고침 → 초기화
		                     request에 담긴 모든 정보를 잃는다 ┌forward, include → request 유지
		                                                └sendRedirect() → request 초기화
		
		4. JSP 문법 사항
		  JSP → HTML + Java
		  1) 스크립트 : 자바 코딩하는 영역
		    - 선언문 : <%! %> → 메소드 선언, 멤버변수 선언(사용빈도 낮음)
		    - 표현식 : <%= %> → 출력(out.println)
		    - 스트립트릿 : <% %> → 일반 자바코드
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
	.container {
		margin-top: 50px;
	}
	.row {
		margin: 0px auto;
		width: 1080px;
	}
	h2 {
		text-align: center
	}
	img {
		width: 120px;
		height: 160px;
	}
</style>
</head>
<body>
	<div class="container">
	<h2>구구단</h2>
	
		<div class="row">
			<table class="table">
				<tr>
					<%
						for(int i=2;i<=9;i++)
						{
					%>
						<th class="text-center"><%=i+"단"%></th>
					<%		
						}
					%>
				</tr>
				<%
					for(int i=1;i<=9;i++)
					{
				%>
						<tr>
				<%		
						for(int j=2;j<=9;j++)
						{
				%>
							<td class="text-center">
								<%=j+" x "+i+" = "+i*j %>
							</td>
				<%
						}
				%>
						</tr>
				<%
					}
				%>
			</table>
		</div>
	
	</div>
</body>
</html>