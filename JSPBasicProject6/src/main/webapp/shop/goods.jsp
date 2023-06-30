<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.util.*"%>
<jsp:useBean id="dao" class="com.sist.dao.GoodsDAO"></jsp:useBean>
<%
	String strPage = request.getParameter("page");
	if(strPage==null)
		strPage="1";
	
	int curpage = Integer.parseInt(strPage);
	List<GoodsBean> list = dao.goodsListData(curpage);
	
	for(GoodsBean vo:list){
		String name = vo.getName();
		if(name.length()>27){
			name = name.substring(0, 27)+"...";
			vo.setName(name);
		}
		vo.setName(name);
	}
	
	int totalpage = dao.goodsTotalPage();
	
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
		width: 960px;
	}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="text-right">
			<%
				String id = (String)session.getAttribute("id");
				if(id!=null)
				{
			%>
					<%=session.getAttribute("name") %>님 환영합니다!
					&nbsp;
					<a href="logout.jsp" class="btn btn-sm btn-default">로그아웃</a>
			<%		
				}
				else {
			%>
				<a href="login.jsp" class="btn btn-sm btn-default">로그인</a>
			<%
				}
			%>
			</div>
		</div>
		
		<div style="height:20px"></div>
		
		<div class="row">
			<%
				for(GoodsBean vo:list){
			%>
					<div class="col-md-3">
				      <div class="thumbnail">
				        <a href="goods_detail.jsp?no=<%=vo.getNo() %>" target="_blank">
				          <img src="<%=vo.getPoster() %>" style="width:100%">
				          <div class="caption">
				            <p><%=vo.getName() %></p>
				          </div>
				        </a>
				      </div>
				    </div>
			<%		
				}
			%>
		</div>
		
		<div style="height:20px"></div>
		
		<div class="row">
			<div class="text-center">
				<a href="goods.jsp?page=<%=curpage>1?curpage-1:curpage %>" class="btn btn-sm btn-default">이전</a>
				<%=curpage %> page / <%=totalpage %> pages
				<a href="goods.jsp?page=<%=curpage<totalpage?curpage+1:curpage %>" class="btn btn-sm btn-default">다음</a>
			</div>
		</div>
</body>
</html>