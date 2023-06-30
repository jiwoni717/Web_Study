<%--
		Session과 Cookie의 차이점
		
					Session				Cookie
	   ──────────────────────────────────────────────
	   클래스명    	내장 객체			  필요시마다 생성
	        	 (HttpSession)		   (Cookie)
	   ──────────────────────────────────────────────
	   저장값  자바에서 사용되는 모든 클래스	 문자열만 저장 가능
	   ──────────────────────────────────────────────
	   저장		   서버 안에 저장	   클라이언트(브라우저) 저장
	   위치	   (구분 : sessionId)
	   ──────────────────────────────────────────────
	   보안		  	   O					X
	   ──────────────────────────────────────────────
	   사용처		로그인, 장바구니...		   최근 방문 기록
	   ──────────────────────────────────────────────
	   
		Cookie
		  1) Cookie 생성
		  	Cookie cookie = new Cookie(String key, String value)
		  2) 저장 기간 설정
		  	cookie.setMaxAge(초) → 0이면 삭제
		  3) 저장 경로 지정
		  	cookie.setPath("/")
		  4) 저장된 쿠키를 클라이언트 브라우저로 전송
		  	response.addCookie(cookie)
		  5) 쿠키 읽기
		  	Cookie[] cookies = request.getCookies() → 저장된 쿠키 모두 읽어옴
		  6) key 읽기
		  	getName()
		  7) value 읽기
		  	getValue()
		  	
		Session
		  JSP에서는 내장 객체(session)
		  전역변수
		  1) 세션 저장
		  	session.setAttribute(String key, Object obj)
		  2) 세션 저장 기간
		  	session.setMaxInactiveInterval(초) → 디폴트값은 30분(1800)
		  3) 세션 읽기
		  	session.getAttribute(String key) → 리턴형 Object임 형변환 잘 하기
		  4) 일부 삭제
		  	session.removeAttribute(String key)
		  5) 전체 삭제
		  	session.invalidate()
		  6) 생성 여부 확인
		  	session.isNew()
		  7) 세션에 등록된 sessionId → 각 클라이언트마다 1개 생성
		  	session.getId()
		
		Map방식으로 저장 → key값이 중복되면 덮어쓴다
		
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%
	String strPage = request.getParameter("page");
	if(strPage==null)
		strPage="1";
	int curpage = Integer.parseInt(strPage);
	
	FoodDAO dao = FoodDAO.newInstance();
	List<FoodBean> list = dao.foodListData(curpage);
	int totalpage = dao.foodTotalPage();
	
	Cookie[] cookies = request.getCookies();
	List<FoodBean> cList = new ArrayList<FoodBean>();
	if(cookies!=null)
	{
		for(int i=cookies.length-1;i>=0;i--){
			if(cookies[i].getName().startsWith("food_"))
			{
				String fno = cookies[i].getValue();
				FoodBean vo = dao.foodDetailData(Integer.parseInt(fno));
				cList.add(vo);
			}
		}
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
				for(FoodBean vo:list){
			%>
					<div class="col-md-3">
				      <div class="thumbnail">
				        <a href="detail_before.jsp?fno=<%=vo.getFno() %>" target="_blank">
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
				<a href="main.jsp?page=<%=curpage>1?curpage-1:curpage %>" class="btn btn-sm btn-default">이전</a>
				<%=curpage %> page / <%=totalpage %> pages
				<a href="main.jsp?page=<%=curpage<totalpage?curpage+1:curpage %>" class="btn btn-sm btn-default">다음</a>
			</div>
		</div>
		
		<div style="height:20px"></div>
		
		<div class="row">
		<h4>최근 방문 맛집</h4>
		<a href="all_delete.jsp" class="btn btn-xs btn-danger">전체 삭제</a>
		<a href="all_cookie.jsp" class="btn btn-xs btn-default">더보기</a>
		<div style="height:20px"></div>
		<%
			int i=0;
			if(cookies!=null && cList.size()>0){
		%>
			<table class="table">
				<tr>
					<td>
		<%
				for(FoodBean vo:cList){
					if(i>8) break;
		%>
					<td class="text-center">
						<img src="<%=vo.getPoster() %>" style="width:100px; height:100px" title="<%=vo.getName()%>">
						<p>
						<a href="cookie_delete.jsp?fno=<%=vo.getFno() %>" class="btn btn-xs btn-default">삭제</a>
					</td>
		<%			
					i++;
				}
		%>
				</tr>
			</table>
		<%
			}
			else {
		%>
				<h5 class="text-center" style="color:gray">방문한 맛집이 없습니다.</h5>
		<%
			}
		%>
		
		<div style="height:20px"></div>
		
		</div>
	</div>
</body>
</html>