<!--
		DBCP : DataBaseConnectionPool
		  1. 연결 / 해제 반복
		    → 오라클 연결시에 시간이 많이 소모 → 미리 Connection 개체를 생성해서 저장 후에 사용 → Connection 객체 생성을 제한 → 사용후에는 재사용이 가능
		  2. Connection을 미리 생성하기 때문에 Connection 객체 관리가 쉽다
		  3. 쉽게 서버가 다운되지 않는다
		  4. 라이브러리가 만들어져 있다 (commons-dbcp, commons-pool)
		  
		  사용법
		    1) server.xml → Connection 객체 생성(maxActive:최대, maxIdle)
		    2) POOL영역에 저장
		    3) 사용자가 요청시에 Connection의 주소를 얻어온다
		    4) 사용자 요청에 따라 수행
		    5) 반드시 POOL안에 Connection 객체를 반환
		    
		  코딩 방법
		    1) getConnection() : 미리 생성된 Connection 객체 얻기
		    2) disConnection()
		    3) 기능
		
-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%
	// 1. 사용자가 전송한 데이터 받기
	String strPage = request.getParameter("page");
	String fd = request.getParameter("addr");
	if(fd==null)
		fd = "마포";
	
	// 2. 실행과 동시에 페이지 전송이 불가능 → 디폴트값 설정
	if(strPage==null)
		strPage="1";
	
	// 3. 현재 페이지 지정
	int curpage = Integer.parseInt(strPage);
	
	// 4. 현재 페이지에 대한 데이터 읽기
	FoodDAO dao = FoodDAO.newInstance();
	List<FoodBean> list = dao.foodListData(curpage);
	
	// 5. 총 페이지 읽기
	int totalpage = dao.foodTotalPage();
	
	// 6. 블록별 처리
	final int BLOCK=10;
	
	// 7. 시작 위치
	int startPage = ((curpage-1)/BLOCK*BLOCK)+1;
	
	// 8. 끝나는 위치
	int endPage = ((curpage-1)/BLOCK*BLOCK)+BLOCK;
	if(endPage>totalpage){
		endPage = totalpage;
	}
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
			<%
				for(FoodBean vo:list)
				{
			%>
					<div class="col-md-3">
				      <div class="thumbnail">
				        <a href="#" target="_blank">
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
	</div>
</body>
</html>