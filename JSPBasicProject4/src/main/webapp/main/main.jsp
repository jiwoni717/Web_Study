<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String mode = request.getParameter("mode");
	if(mode==null)
		mode="0";
	String jsp = "";
	int index = Integer.parseInt(mode);
	
	switch(index) {
		case 0:
			jsp="inner.jsp";
			break;
		case 1:
			jsp="request.jsp";
			break;
		case 2:
			jsp="response.jsp";
			break;
		case 3:
			jsp="application.jsp";
			break;
		case 4:
			jsp="pagecontext.jsp";
			break;
		case 5:
			jsp="out.jsp";
			break;
		case 6:
			jsp="etc.jsp";
			break;
		case 7:
			jsp="session.jsp";
			break;
		case 8:
			jsp="action.jsp";
			break;
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
<style type="text/css">
	.container {
		margin-top: 50px;
		border : 1px solid black;
	}
	.row {
		margin: 0px auto;
		width: 900px;
		height : 150px;
		border : 1px solid black;
	}
	.row1 {
		margin: 0px auto;
		width : 900px;
		height : 500px;
		border : 1px solid black;
	}
	h2 {
		text-align: center;
		font-family: 'Gowun Dodum', sans-serif;
	}
	ul {
		list-style: none;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<h2>내장 객체 정리</h2>
		</div>
		
		<div class="row1">
			<div class="col-sm-3">
				<div style="height:100px"></div>
				<table class="table">
					<tr height=35>
						<td class="text-center"><a href="main.jsp?mode=0">Home</a></td>
					</tr>
					<tr height=35>
						<td class="text-center"><a href="main.jsp?mode=1">request</a></td>
					</tr>
					<tr height=35>
						<td class="text-center"><a href="main.jsp?mode=2">response</a></td>
					</tr>
					<tr height=35>
						<td class="text-center"><a href="main.jsp?mode=3">application</a></td>
					</tr>
					<tr height=35>
						<td class="text-center"><a href="main.jsp?mode=4">pageContext</a></td>
					</tr>
					<tr height=35>
						<td class="text-center"><a href="main.jsp?mode=5">out</a></td>
					</tr>
					<tr height=35>
						<td class="text-center"><a href="main.jsp?mode=6">etc</a></td>
					</tr>
					<tr height=35>
						<td class="text-center"><a href="main.jsp?mode=7">session</a></td>
					</tr>
				</table>
			</div>
			<div class="col-sm-9">
				<jsp:include page="<%=jsp %>"></jsp:include>
			</div>
		</div>
		
		<div class="row">
			<h2>개인 정보</h2>
		</div>
	</div>
</body>
</html>