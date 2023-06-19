<%-- 
		JSP → 서버에서 실행하는 자바파일
		JSP는 실행시에 servlet 클래스로 변경 → 변경을 Tomcat(WAS → WebApplicationServlet)이 해줌
		post.jsp ==========> post_jsp.java
								 컴파일
								   ↓
							 post_jsp.class
							       ↓
							    인터프리터
							       ↓
							 메모리에 HTML만 출력
		
		1. 지시자
		  1) 페이지 지시자
		  	<% page 속성 = 값 %> → JSP 파일 정보
		  	속성
		  	 - contentType : 브라우저와 연결 → 파일 형태를 알려주는 역할
		  	     text/html → 디폴트값
		  	     text/xml
		  	     text/plain → json
		  	     !!디폴트가 영어로 되어있기 때문에 charset=UTF-8 필수!!
		  	 - import : 다른 라이브러리 로딩시 사용
		  	   <% page import="java.util.*, java.io.*,..." %>
		  	 - errorPage : 에러 발생시 이동하는 파일을 지정
		  	   <% page errorPage="error.jps" %>
		  	 → 파일의 확장자는 변경이 가능
		  2) include 지시자 : JSP안에 특정위치 다른 JSP를 포함
		  	<% include file="a.jsp" %>
		  3) taglib 지시자 : 자바의 제어문, 변수선언, String → 태그로 만들어서 제공
		    <%
		    	for(int i=0;i<10;i++){
		    %>
		    		<ul></ul>
		    		...
		    <%
		    	}
		    %>
		    			↓
		    <c:forEach let="i" begin="0" end="10" step="1">
		    	<ul></ul>
		    </c:forEach>
		    
		1-1. 스크립트릿
		  - 자바 언어 분리
		  1) 선언식 : <%! 메소드 선언, 전역변수 %> → 사용 빈도 없음
		  2) 표현식 : <%= 화면 출력 내용 %> == out.println()
		  3) 스크립트릿 : <% 일반 자바 코드 → 지역변수, 메소드 호출, 객체 생성, 제어문... %>
		  
		2. 내장객체***
		  - 자바에서 지원하는 객체
		  1) request* : 사용자가 보내준 데이터 관리
		  2) response* : 응답(html, cookie)
		  3) session* : 서버에 사용자 정보 일부를 저장, 장바구니...
		  4) application* : 서버 관리 → getRealPath()****(실제 톰캣이 읽어가는 파일 위치)
		  5) out* → <% %>
		  6) pageContext* : 페이지 흐름
		  7) config
		  8) exception* → try~catch
		  9) page* → this
		  
		3. JSP 액션 태그
		  <jsp:useBean> : 클래스 객체 생성
		  <jsp:setProperty> : set메소드에 값을 채운다
		  <jsp:getProperty> : get메소드 호출
		  <jsp:param> : 데이터 전송
		  <jsp:include> : 특정 위치에 다른 jsp 추가할 때
		  <jsp:forward> : 화면 이동 → request를 계속 유지
			→ sendRedirect() : request를 초기화
			
		4. JSTL / EL****
		  - JSTL : 태그로 자바 라이브러리를 만들어 준다
		  - EL : 화면 출력 → <% %>(x) → ${}
		
		5. MVC***
		  - 자바와 HTML을 분리해서 코딩
		  - 자바 → Model / HTML → View
		  	------------------------- Controller가 연결(서블릿)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%
	memberDAO dao = memberDAO.newInstance();
	request.setCharacterEncoding("UTF-8");
	String dong=request.getParameter("dong");
	int count = 0;
	List<zipcodeVO> list = null;
	
	if(dong!=null){
		count = dao.postfindCount(dong);
		list = dao.postfind(dong);
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
	.container {
		margin-top: 50px;
	}
	.row {
		margin: 0px auto;
		width: 450px;
	}
	h2 {
		text-align: center
	}
	td,th {
		font-size: 12px;
	}
	a {
		text-decoration: none;
		color: black;
	}
	a:hover {
		text-decoration: underline;
		color : #1E90FF;
	}
</style>
<script type="text/javascript">
	let ok=(zip,addr)=>{
		
		opener.frm.post1.value=zip.substring(0,3);
		opener.frm.post2.value=zip.substring(4,7);
		opener.frm.addr.value=addr;
		
		self.close();
	}
</script>
</head>
<body>
	<div class="container">
	<h2>우편번호 검색</h2>
	
		<div class="row">
		<form method=post action="post.jsp">
			<table class="table">
				<tr>
					<td>
						우편번호 : <input type="text" name=dong size=15 class="input-sm">&nbsp;<input type="submit" value="검색" class="btn btn-sm btn-danger">
					</td>
				</tr>
			</table>
		</form>
		<div style="height:20px"></div>
		<%
			if(list!=null)
			{
		%>
				<table class="table">
					<tr>
						<th width=20% class="text-center">우편번호</th>
						<th width=80% class="text-center">주소</th>
					</tr>
					<%
						if(count==0)
						{
					%>
							<tr>
								<td colspan="2" class="text-center">
									<h3>검색된 결과값이 없습니다</h3>
								</td>
							</tr>
					<%
						}
						else{
							for(zipcodeVO vo:list)
							{
								%>
									<tr>
										<td width="20%" class="text-center">
											<%=vo.getZipcode() %>
										</td>
										<td width="80%" class="text-center">
											<a href="javascript:ok('<%=vo.getZipcode() %>','<%=vo.getAddress() %>')"><%=vo.getAddress() %></a>
										</td>
									</tr>
								<%
							}
						}
					%>
				</table>
		<%		
			}
		%>
		</div>
	
	</div>
</body>
</html>