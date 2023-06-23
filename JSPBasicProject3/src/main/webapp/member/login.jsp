<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		width: 500px;
	}
	h2 {
		text-align: center;
		font-family: 'Gowun Dodum', sans-serif;
	}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<%-- ↑ import 한거임 여기에 코딩x --%>
<script type="text/javascript">
	// 자바스크립트 라이브러리
	$(function(){ // ← window.onload=()=>{}
//		$('#logBtn').click(function(){
//			alert("Hello Jquery!!")
//		})
		$('#logBtn').on('click', function(){
			let id=$('#id').val()
			if(id.trim()=="")
			{
				$('#id').focus()
				return;
			}
			
			let pwd=$('#pwd').val()
			if(pwd.trim()=="")
			{
				$('#pwd').focus()
				return;
			}
			//$('#frm').submit();
			$.ajax({
				type:'post',
				url:'login_ok.jsp', // 누구한테 값을 보내냐
				data:{"id":id, "pwd":pwd},
				success:function(result){
					let res = result.trim();
					if(res=='NOID')
					{
						$('#id').val("");
						$('#pwd').val("");
						$('#id').focus();
						$('#print').text("아이디가 존재하지 않습니다.");
					}
					else if(res=='NOPWD'){
						$('#pwd').val("");
						$('#pwd').focus();
						$('#print').text("비밀번호가 틀립니다.");
					}
					else{
						location.href="../databoard/list.jsp";
					}
				}
			})
		})
	})
</script>
</head>
<body>
	<div class="container">
		<h2>LOGIN</h2>
		
			<div class="row">
			<form method=post action=login_ok.jsp id="frm">
				<table class="table">
					<tr>
						<td width=20%>ID</td>
						<td width=80%>
							<input type=text name=id size=15 class="input-sm" id=id>
						</td>
					</tr>
					<tr>
						<td width=20%>Password</td>
						<td width=80%>
							<input type=password name=pwd size=15 class="input-sm" id=pwd>
						</td>
					</tr>
					<tr>
						<td colspan=2 class="text-center">
							<span id="print" style="color:red"></span>
						</td>
					</tr>
					<tr>
						<td colspan=2 class="text-center">
							<input type=button class="btn btn-sm btn-default" value="로그인" id="logBtn"></button>
							<a href="../databoard/list.jsp" class="btn btn-sm btn-default">게시판</a>
						</td>
					</tr>
				</table>
			</form>
			</div>
			
	</div>
</body>
</html>