<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, com.sist.vo.*"%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"></jsp:useBean>
<%
	request.setCharacterEncoding("UTF-8");
	String name=request.getParameter("name");
	String no=request.getParameter("no");
	String subject=request.getParameter("subject");
	String content=request.getParameter("content");
	String pwd=request.getParameter("pwd");
	
	DataBoardVO vo = new DataBoardVO();
	vo.setName(name);
	vo.setNo(Integer.parseInt(no));
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	//DAO 연결
	boolean bCheck = dao.databoardUpdate(vo);
	
	// 화면 이동(비밀번호 확인 필수)
	if(bCheck==false){
%>
		<script>
			alert=("비밀번호가 틀렸습니다!!");
			history.back();
		</script>
<%		
	}
	else {
		response.sendRedirect("detail.jsp?no="+no);
	}
	
%>