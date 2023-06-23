<%@page import="com.sist.vo.ReplyVO"%>
<%@page import="java.util.*"%>
<%@page import="com.sist.vo.DataBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"/>
<%
	String no = request.getParameter("no");
	DataBoardVO vo = dao.databoardDetailData(Integer.parseInt(no),0);
	
	// 아이디 읽기
	String id = (String)session.getAttribute("id");
	
	// 인기순
	List<DataBoardVO> list = dao.databoardTop10();
	for(DataBoardVO tvo:list)
	{
		String temp = tvo.getSubject();
		if(temp.length()>10)
		{
			temp = temp.substring(0, 10)+"...";
			tvo.setSubject(temp);
		}
		tvo.setSubject(temp);
	}
	
	// 댓글
	List<ReplyVO> rList = dao.replyListData(Integer.parseInt(no));
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
		width: 800px;
	}
	h2 {
		text-align: center;
	}
	a {
		text-decoration: none;
	}
	a:hober {
		text-decoration: underline;
	}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
	let i=0;
	function rm(){
		if(i==0)
		{
			document.querySelector("#del").style.display='';
			//$('#del').show()
			document.querySelector("#delBtn").textContent='취소';
			//$('#delBtn').text("취소")
			i=1;
		}
		else {
			document.querySelector("#del").style.display='none';
			//$('#del').hide()
			document.querySelector("#delBtn").textContent='삭제';
			i=0;
		}
	}
let u = 0;
$(function() {
	$('.updates').click(function() {
		$('.ups').hide();
		$('.updates').text("수정");
		let no = $(this).attr("data-no");
		if(u==0)
		{
			$('#u'+no).show("slow");
			$(this).text("취소");
			u=1;
		}
		else {
			$('#u'+no).hide("slow");
			$(this).text("수정");
			u=0;
		}
	})
})
</script>
</head>
<body>
	<div class="container">
	<h2>내용 보기</h2>
	
		<div calss="row">
			<table class="table">
				<tr>
					<th width=20% class="text-center warning">번호</th>
					<td width=30% class="text-center"><%=vo.getNo() %></td>
					<th width=20% class="text-center warning">작성일</th>
					<td width=30% class="text-center"><%=vo.getDbday() %></td>
				</tr>
				
				<tr>
					<th width=20% class="text-center warning">이름</th>
					<td width=30% class="text-center"><%=vo.getName() %></td>
					<th width=20% class="text-center warning">조회수</th>
					<td width=30% class="text-center"><%=vo.getHit() %></td>
				</tr>
				
				<tr>
					<th width=20% class="text-center warning">제목</th>
					<td colspan="3"><%=vo.getSubject() %></td>
				</tr>
				<%
					if(vo.getFilesize()!=0)
					{
				%>
				<tr>
					<th width=20% class="text-center warning">첨부파일</th>
					<td colspan="3">
						<a href="download.jsp?fn=<%=vo.getFilename()%>"><%=vo.getFilename() %></a>(<%=vo.getFilesize() %>Bytes)
					</td>
				</tr>
				<%
					}
				%>
				<tr>
					<td colspan="4" class="text-left" valign="top" height="200">
						<pre style="white-space: pre-wrap; background-color: white; border: none"><%=vo.getContent() %></pre>
					</td>
				</tr>
				<tr>
					<td colspan="4" class="text-right">
						<a href="update.jsp?no=<%=vo.getNo() %>" class="btn btn-xs btn-default">수정</a>
						<span class="btn btn-xs btn-danger" id="delBtn" onclick="rm()">삭제</span>
						<a href="list.jsp" class="btn btn-xs btn-default">목록</a>
					</td>
				</tr>
				<tr style="display:none" id="del">
					<td colspan="4" class="text-right">
						<form method=post action="delete.jsp">
						비밀번호 : <input type=password name=pwd size=15 class="input-sm" required>
						<input type=hidden name=no value="<%=vo.getNo()%>">
						<input type=submit value="삭제" class="btn btn-sm btn-danger">
						</form>
					</td>
				</tr>
			</table>
		</div>
		<div class="row">
			<div class="col-sm-8">
				<%-- 댓글 --%>
				<table class="table">
					<tr>
						<td>
							<%
								for(ReplyVO rvo:rList){
							%>
								<table class="table">
									<tr>
										<td class="text-left">&nbsp;<%=rvo.getName() %>&nbsp;(<%=rvo.getDbday() %>)</td>
										<td class="text-right">
											<%
												if(id!=null)
												{
													if(id.equals(rvo.getId()))
													{
											%>
														<span class="btn btn-xs btn-default updates" data-no="<%=rvo.getNo()%>">수정</span>
														<a href="reply_delete.jsp?no=<%=rvo.getNo() %>&bno=<%=rvo.getBno() %>" class="btn btn-xs btn-danger">삭제</a>
											<%		
													}
												}
											%>
										</td>
									</tr>
									<tr>
										<td colspan="2" class="text-left" valign="top"><pre style="white-space: pre-wrap;background-color: white;border:none"><%=rvo.getMsg() %></pre></td>
									</tr>
									<tr class="ups" id="u<%=rvo.getNo() %>" style="display: none;">
										<td colspan=2>
											<form method="post" action="reply_update.jsp">
											<textarea rows="4" cols="45" name=msg style="float: left"><%=rvo.getMsg() %></textarea>
											<input type=hidden name=bno value="<%=vo.getNo()%>">
											<input type=hidden name=no value="<%=rvo.getNo()%>">
											<input type=submit value="수정" class="btn btn-sm btn-warning" style="width: 80px; height: 40px">
											</form>
										</td>
									</tr>
								</table>		
							<%		
								}
							%>
						</td>
					</tr>
				</table>
				<div style="height: 10px"></div>
				<%
					if(id!=null)
					{
				%>
				<table class="table">
					<tr>
						<td>
							<form method="post" action="reply_insert.jsp">
							<textarea rows="4" cols="45" name=msg style="float: left"></textarea>
							<input type=hidden name=bno value="<%=vo.getNo()%>">
							<input type=submit value="등록" class="btn btn-sm btn-danger" style="width: 80px; height: 40px">
							</form>
						</td>
					</tr>
				</table>
				<%
					}
				%>
			</div>
			<div class="col-sm-4">
				<%-- 인기 게시물 --%>
				<table class="table">
					<caption>인기글</caption>
					<tr>
						<th>제목</th>
						<th>작성자</th>
					</tr>
					<%
						for(DataBoardVO tvo:list){
					%>
						<tr>
							<td><a href="detail.jsp?no=<%=tvo.getNo()%>"><%=tvo.getSubject() %></a></td>
							<td><%=tvo.getName() %></td>
						</tr>
					<%
						}
					%>
				</table>
			</div>
		</div>
	</div>
</body>
</html>