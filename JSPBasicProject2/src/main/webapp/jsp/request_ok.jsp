<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//request.jsp에서 전송된 데이터를 받음
	//1. 한글변환
	request.setCharacterEncoding("UTF-8");
	
	String name = request.getParameter("name");
	String sex = request.getParameter("sex");
	String tel = request.getParameter("tel");
	String tel2 = request.getParameter("tel2");
	String tel3 = request.getParameter("tel3");
	String content = request.getParameter("content");
	String[] hobby = request.getParameterValues("hobby");
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>전송된 값</h2>
	이름 : <%=name %><br>
	성별 : <%=sex %><br>
	전화번호 : <%=tel+"-"+tel2+"-"+tel3 %><br>
	소개 : <%=content %><br>
	취미 :
	<ul>
	<%
		try{
			for(String h:hobby)
			{
	%>
				<li><%=h%></li>
	<%
			}
		}catch(Exception ex){
	%>
			<font color="red">X</font>
	<%		
		}
	%>
	</ul>
</body>
</html>