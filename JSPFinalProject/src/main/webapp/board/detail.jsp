<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
	.row {
		margin: 0px auto;
		width: 600px;
	}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
let i=0; // 전역변수
let u=0;
$(function(){
	$('#del').click(function(){
		if(i==0)
		{
			$(this).text("취소");
			$('#delTr').show();
			i=1;
		}
		else
		{
			$(this).text("수정");
			$('#delTr').hide();
			i=0;
		}
	})
	
	$('#delBtn').click(function(){
		let pwd=$('#pwd1').val();
		let no=$('#delBtn').attr("data-no");
		if(pwd.trim()=="")
		{
			$('#pwd1').focus();
			return;
		}
		// delete.do?no=1&pwd=1111
		$.ajax({
			type:'post',
			url:'../board/delete.do',
			data:{"no":no,"pwd":pwd},
			success:function(result)
			{
				let res=result.trim();
				if(res==='NO')
				{
					alert("비밀번호가 틀립니다")
					$('#pwd1').val("")
					$('#pwd1').focus()
				}
				else
				{
					location.href="../board/list.do"
				}
			}
		})
	})
	
	// 댓글 수정
	$('.ups').click(function() {
		let no=$(this).attr("data-no");
		$(this).text("수정");
		$('.updates').hide();
		if(u===0)
		{
			$('#u'+no).show();
			$(this).text("취소");
			u=1;
		}
		else
		{
			$('#u'+no).hidden();
			$(this).text("수정");
			u=0;
		}
	})
})
</script>
</head>
<body>
	<div class="wrapper row3">
		<main class="container clear"> 
  			<h2 class="sectiontitle">상세보기</h2>
  				<table class="table">
  					<tr>
  						<th width=20% class="text-center">번호</th>
  						<td width=30% class="text-center">${vo.no }</td>
  						<th width=20% class="text-center">작성일</th>
  						<td width=30% class="text-center">${vo.dbday }</td>
  					</tr>
  					
  					<tr>
  						<th width=20% class="text-center">이름</th>
  						<td width=30% class="text-center">${vo.name }</td>
  						<th width=20% class="text-center">조회수</th>
  						<td width=30% class="text-center">${vo.hit }</td>
  					</tr>
  					
  					<tr>
  						<th width=20% class="text-center">제목</th>
  						<td colsapn=3>${vo.subject }</td>
  					</tr>
  					
  					<tr>
  						<td colspan=4 class="text-left" valign="top" height="200">
  							<pre style="white-space: pre-wrap; border: none; background-color: white">${vo.content }</pre>
  						</td>
  					</tr>
  					
  					<tr>
  						<td colspan=4 class="text-right">
  							<a href="../board/update.do?no=${vo.no }" class="btn btn-xs btn-default">수정</a>
  							<span class="btn btn-xs btn-danger" id="del">삭제</span>
  							<a href="../board/list.do" class="btn btn-xs btn-default">목록</a>
  						</td>
  					</tr>
  					<tr style="display: none" id="delTr">
  						<td colspan=4 class="text-right inline">
  							비밀번호 : <input type=password name=pwd id=pwd1 size=10 class="input-sm">
  							<input type=button value="삭제" class="btn btn-sm btn-danger" id=delBtn data-no="${vo.no }">
  						</td>
  					</tr>
  				</table>
  				
  				<div style="height: 20px"></div>
  				
  				<div class="col-sm-8">
	  				<table class="table">
	  					<tr>
	  						<td>
	  							<c:forEach var="rvo" items="${list }">
	  								<table class="table">
	  									<tr>
	  										<td class="text-left">
	  											<c:if test="${rvo.group_tab>0 }">
	  												<c:forEach var="i" begin="1" end="${rvo.group_tab }">
	  													&nbsp;&nbsp;
	  												</c:forEach>
	  												<img src="image/re_icon.png">
	  											</c:if>
	  											♥${rvo.name }&nbsp;(${rvo.dbday })
	  										</td>
	  										<td class="text-right">
	  											<span class="btn btn-xs btn-default ups" data-no="${rvo.no }">수정</span>
	  											<a href="#" class="btn btn-xs btn-default">삭제</a>
	  											<a href="#" class="btn btn-xs btn-default">답댓글 달기</a>
	  										</td>
	  									</tr>
	  									<tr>
	  										<td colspan=2>
	  											<pre style="white-space: pre-wrap; background-color: white; border: none;">${rvo.msg }</pre>
	  										</td>
	  									</tr>
	  									<tr style="display: none" class="updates" id="u${rvo.no }">
					  						<td colspan=2>
					  							<form method="post" action="../board/reply_update.do" class="inline">
					  								<input type=hidden name=bno value="${vo.no }">
					  								<input type=hidden name=no value="${rvo.no }">
					  								<textarea rows="5" cols="60" name="msg" style="float: left">${rvo.msg }</textarea>
					  								<input type=submit class="btn btn-sm btn-default" value="댓글 수정" style="width:120px; height: 106px">
					  							</form>
					  						</td>
					  					</tr>
	  								</table>
	  							</c:forEach>
	  						</td>
	  					</tr>
	  				</table>
	  				
	  				<c:if test="${sessionScope.id!=null }">
		  				<table calss="table">
		  					<tr>
		  						<td>
		  							<form method="post" action="../board/reply_insert.do" class="inline">
		  								<input type=hidden name=bno value="${vo.no }">
		  								<textarea rows="5" cols="60" name="msg" style="float: left"></textarea>
		  								<input type=submit class="btn btn-sm btn-default" value="댓글 쓰기" style="width:120px; height: 106px">
		  							</form>
		  						</td>
		  					</tr>
		  				</table>
	  				</c:if>
  				</div>
  				
  				<div class="col-sm-4">
  				
  				</div>
  		</main>
  	</div>
	
</body>
</html>