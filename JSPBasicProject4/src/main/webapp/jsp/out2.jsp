<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--
	버퍼 크기 : <%=out.getBufferSize() %><br>
	사용중인 버퍼 크기 : <%=out.getRemaining() %><br>
	현재 사용중인 버퍼 크기 : <%=out.getBufferSize()-out.getRemaining() %>
-->
<%
	for(int i=1;i<=10;i++)
	{
		if(i%2==0)
		{
			out.println(i+"&nbsp;");
		}
	}
%>
</body>
</html>