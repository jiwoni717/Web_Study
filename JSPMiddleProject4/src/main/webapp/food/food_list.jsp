<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="dao" class="com.sist.dao.FoodDAO"/>

<%
	String cno = request.getParameter("cno");
	CategoryVO cvo = dao.foodCategoryInfoData(Integer.parseInt(cno));
	List<FoodVO> list = dao.foodCategoryListData(Integer.parseInt(cno));
%>
<c:set var="cvo" value="<%=cvo %>"/>
<c:set var="list" value="<%=list %>"/>

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
		width: 700px;
	}
	.jumbotron {
		background-color: white;
	}
	a {
		text-decoration: none;
		color : black;
	}
	a:hover {
		text-decoration: underline;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<h2 class="text-center">${cvo.title }</h2>
			<h3 class="text-center">${cvo.subject }</h3>
			<div style="height: 10px"></div>
			
			<div class="row">
				<table class="table">
					<tr>
						<td>
							<c:forEach var="vo" items="${list }">
								<table class="table">
									<tr>
										<td class="text-center" width=35% rowspan=4>
											<a href="food_detail.jsp?fno=${vo.fno }">
											<img src="${vo.poster }" style="width:200px; height: 200px" class="img-rounded">
											</a>
										</td>
										<td width=65%>
											<h3><a href="food_detail.jsp?fno=${vo.fno }">${vo.name }&nbsp;</a><span style="color:orange">${vo.score }</span></h3>
										</td>
									</tr>
									<tr>
										<td width="65%">${vo.address }</td>
									</tr>
									<tr>
										<td width="65%">${vo.phone }</td>
									</tr>
									<tr>
										<td width="65%">${vo.type }</td>
									</tr>
								</table>
							</c:forEach>
						</td>
					</tr>
				</table>
			</div>
			
		</div>
	</div>
</body>
</html>