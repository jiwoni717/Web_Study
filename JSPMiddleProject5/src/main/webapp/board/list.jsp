<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="dao" class="com.sist.dao.ReplyBoardDAO"/>
<%
	String strPage = request.getParameter("page");
	if(strPage==null)
		strPage="1";
	int curpage = Integer.parseInt(strPage);
	List<ReplyBoardVO> list = dao.boardListData(curpage);
	
	int count = dao.boardRowCount();
	int totalpage = (int)(Math.ceil(count/10.0));
	count = count-((curpage*10)-10);
	
	request.setAttribute("curpage", curpage);
	request.setAttribute("totalpage", totalpage);
	request.setAttribute("list", list);
	request.setAttribute("count", count);
	request.setAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
	.container {
		margin-top: 50px;
	}
	.row {
		margin: 0px auto;
		width: 800px;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="text-center">
				<img src="image/qna.jpg" style="width:400px; height: 150px">
			</div>
			
			<div style="height: 20px"></div>
			
			<div class="row">
				<table class="table">
					<tr>
						<td>
							<a href="#" class="btn btn-sm btn-default">새 글</a>
						</td>
					</tr>
				</table>
			
				<table class="table">
					<tr class="warning">
						<th width=10% class="text-center">번호</th>
						<th width=45% class="text-center">제목</th>
						<th width=15% class="text-center">이름</th>
						<th width=20% class="text-center">작성일</th>
						<th width=10% class="text-center">조회수</th>
					</tr>
					<c:forEach var="vo" items="${list }">
					<c:set var="count" value="${count }"/>
					<tr>
						<td width=10% class="text-center">${count }</td>
						<td width=45%>
							<c:if test="${vo.group_tab>0 }">
								<c:forEach var="i" begin="1" end="${vo.group_tab }">
									&nbsp;&nbsp;
								</c:forEach>
								<img src="image/re_icon.png">&nbsp;
							</c:if>
							${vo.subject } &nbsp;
							<sup><img src="image/new.gif"></sup>
						</td>
						<td width=15% class="text-center">${vo.name }</td>
						<td width=20% class="text-center">${vo.dbday }</td>
						<td width=10% class="text-center">${vo.hit }</td>
					</tr>
					<c:set var="count" value="${count-1 }"/>
					</c:forEach>
				</table>
				
				<table class="table">
					<tr>
						<td class="text-left">
							검색&nbsp;
							<select name=fs class="input-sm">
								<option value="name">이름</option>
								<option value="subject">제목</option>
								<option value="content">내용</option>
							</select>
							<input type=text name=ss size=15 class="input-sm">
							<button class="btn btn-sm btn-default">검색</button>
						</td>
						<td class="text-right">
							<a href="list.jsp?page=${curpage>1?curpage-1:curpage }" class="btn btn-sm btn-default">이전</a>
								&nbsp;${curpage } page / ${totalpage } pages&nbsp;
							<a href="list.jsp?page=${curpage<totalpage?curpage+1:curpage }" class="btn btn-sm btn-default">다음</a>
						</td>
					</tr>
				</table>
			</div>
			
		</div>
	</div>
</body>
</html>