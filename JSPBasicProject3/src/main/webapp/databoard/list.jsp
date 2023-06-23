<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, com.sist.vo.*, java.util.*, java.text.*"%>
<%
	// 1. 사용자가 보내준 요청값을 받는다
	String strPage = request.getParameter("page");
	if(strPage==null)
		strPage = "1";
	int curpage = Integer.parseInt(strPage);
	// 2. DAO에서 요청한 page의 값을 읽어온다
	DataBoardDAO dao = DataBoardDAO.newInstance();
	List<DataBoardVO> list = dao.databoardListData(curpage);
	int count = dao.databoardRowCount();
	int totalpage = (int)(Math.ceil(count/10.0));
	
	final int BLOCK = 5;
	int startPage = ((curpage-1)/BLOCK*BLOCK)+1;
	int endPage = ((curpage-1)/BLOCK*BLOCK)+BLOCK;
	if(endPage>totalpage)
		endPage = totalpage;
	// 3. 오라클에서 가져온 데이터를 출력
	count = (count-((curpage*10)-10));
	
	String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	String id = (String)session.getAttribute("id");
	
	
	
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
</head>
<body>
	<div class="container">
	<h2>자료실</h2>
	
		<div class="row">		
		
			<table class="table">
				<tr>
					<td class="text-left">
						<a href="insert.jsp" class="btn btn-sm btn-default">새 글</a>
					</td>
				<%
					if(id!=null)
					{
				%>
					<td class="text-right">
						<%=session.getAttribute("name") %>님 환영합니다.
						<a href="../member/logout.jsp" class="btn btn-sm btn-default">로그아웃</a>
					</td>
				<%
					}
					else
					{
				%>
					<td class="text-right">
						<a href="../member/login.jsp" class="btn btn-sm btn-default">로그인</a>
					</td>
				<%
					}
				%>
				</tr>
			</table>
		
			<table class="table table-hover">
				<tr class="warning">
					<th class="text-center" width=10%>번호</th>
					<th class="text-center" width=45%>제목</th>
					<th class="text-center" width=15%>이름</th>
					<th class="text-center" width=20%>작성일</th>
					<th class="text-center" width=10%>조회수</th>
				</tr>
				<%
					for(DataBoardVO vo:list)
					{
				%>
						<tr>
							<td class="text-center" width=10%><%=count-- %></td>
							<td width=45%><a href="detail.jsp?no=<%=vo.getNo() %>"><%=vo.getSubject() %></a>
								&nbsp;
								<%
									if(today.equals(vo.getDbday()))
									{
								%>
									<super style="color:orange">new!</super>
								<%
									}
								%>
							</td>
							<td class="text-center" width=15%><%=vo.getName() %></td>
							<td class="text-center" width=20%><%=vo.getDbday() %></td>
							<td class="text-center" width=10%><%=vo.getHit() %></td>
						</tr>
				<%
					}
				%>
			</table>
		</div>
		
		<div class="row">
			<div class="text-center">
				<ul class="pagination">
				<%
					if(startPage>1)
					{
				%>
				  	<li><a href="list.jsp?page=<%=startPage-1%>">&lt;</a></li>
				  <%
				  	}
				  	for(int i=startPage;i<=endPage;i++)
				  	{	
				  %>
				  	<li <%=curpage==i?"class=active":"" %>><a href="list.jsp?page=<%=i %>"><%=i %></a></li>
				  <%		
				  	}
				  %>
				  <% if(endPage<totalpage)
				  	{
					  
				  %>
				  <li><a href="#">&gt;</a></li>
				  <%
				  	}
				  %>
				</ul>
			</div>
		</div>
	
	</div>
</body>
</html>