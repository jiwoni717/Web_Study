<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, com.sist.vo.*"%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>
<%
	String no = request.getParameter("no");
	DataBoardVO vo = dao.databoardDetailData(Integer.parseInt(no), 1);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300;400&display=swap" rel="stylesheet">
<style type="text/css">
	* {
		font-family: 'Noto Sans KR', sans-serif;
	}
	.container {
		margin-top: 50px;
	}
	.row {
		margin: 0px auto;
		width: 900px;
	}
	h2 {
		text-align: center;
	}
</style>
<script type="text/javascript">
	function board_write(){
		// 유효성 검사
		let frm = document.frm;
		
		if(frm.name.value=="")
		{
			frm.name.focus();
			return;
		}
		if(frm.subject.value=="")
		{
			frm.subject.focus();
			return;
		}
		if(frm.content.value=="")
		{
			frm.content.focus();
			return;
		}
		if(frm.pwd.value=="")
		{
			frm.pwd.focus();
			return;
		}
		
		frm.submit(); // submit 버튼을 함수화
	}
</script>
</head>
<body>
	<div class="container">
	<h2>글쓰기</h2>
	
		<div class="row">
			
			<form method=post action="update_ok.jsp" name=frm>
			
			<table class="table">
				<tr>
					<th class="text-center warning" width=15%>이름</th>
					<td width=85%>
						<input type=text name=name class="input-sm" size=10 value="<%=vo.getName() %>">
						<input type=hidden name=no value="<%=no %>">
					</td>
				</tr>
				<tr>
					<th class="text-center warning" width=15%>제목</th>
					<td width=85%>
						<input type=text name=subject class="input-sm" size=50 value="<%=vo.getSubject() %>">
					</td>
				</tr>
				<tr>
					<th class="text-center warning" width=15%>내용</th>
					<td width=85%>
						<textarea rows="10" cols="50" name=content><%=vo.getContent() %></textarea>
					</td>
				</tr>
				<tr>
					<th class="text-center warning" width=15%>비밀번호</th>
					<td width=85%>
						<input type=password name=pwd class="input-sm" size=5>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type=button value="수정" class="btn btn-sm btn-default" onclick="board_write()">
						<input type=button value="취소" class="btn btn-sm btn-warning" onclick="javascript:history.back()">
					</td>
				</tr>
			
			</table>
			
			</form>
		</div>
		
	</div>
</body>
</html>