<%--
		jsp 액션 태그
	 	  - <jsp:forward> : 화면 이동
	 	  - <jsp:include>* : 화면 연결	┐
	 	  - <jsp:param>* : 값 전송	┘두개만 기억하고 있으면 됨
	 	  - <jsp:useBean> : 객체 생성
	 	  - <jsp:setProperty> : 멤버변수에 값 주입
	 	  - <jsp:hetProperty> : 멤버변수값 읽기
	 	  
	 	<jsp:useBean> 속성
	 	  - id : 객체명
	 	  - class : 클래스명 → 패키지명.클래스명
	 	  - scope : 사용범위
			└page : 한개의 JSP에서만 사용(디폴트값) → 생략 가능
	 	  	└request : 사용자 요청이 있는 경우
	 	  	└session : 프로그램을 유지하고 있는 동안
	 	  	└application : 프로그램 종료시까지 유지
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.temp.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	
%>
<jsp:useBean id="bean" class="com.sist.temp.SawonBean">
	<jsp:setProperty name="bean" property="*"/>
	<%-- id값과 setProperty의 name값이 같아야함 --%>
</jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	이름 : <%=bean.getName() %><br>
	성별 : <%=bean.getSex() %><br>
	부서 : <%=bean.getDept() %><br>
	직위 : <%=bean.getJob() %><br>
	연봉 : <%=bean.getPay() %><br>
</body>
</html>