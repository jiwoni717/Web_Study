<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 세션 해제 → 저장된 !모든! 정보를 지움
	// 하나씩 지울 때는 removeAttribute("key");
	session.invalidate();
	response.sendRedirect("../databoard/list.jsp");
%>