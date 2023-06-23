<%@page import="com.sist.vo.DataBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.io.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%
	// _ok.jsp : 기능 처리
	// 1. 한글 처리
	request.setCharacterEncoding("UTF-8");

	// 1-1. 파일 업로드 클래스 생성
	String path ="c:\\download";
	int size = 1024*1024*100;
	String enctype="UTF-8";
	MultipartRequest mr = new MultipartRequest(request, path, size, enctype, new DefaultFileRenamePolicy());

	// 2. 요청데이터 받기
	String name = mr.getParameter("name");
	String subject = mr.getParameter("subject");
	String content = mr.getParameter("content");
	String pwd = mr.getParameter("pwd");
	
	// 3. vo에 묶기
	DataBoardVO vo = new DataBoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	//String filename=mr.getOriginalFileName("upload"); → 사용자가 보낸 파일 이름
	String filename = mr.getFilesystemName("upload"); // → 중복되어 이름 바뀐 파일까지
	if(filename==null)
	{
		// 업로드가 안된 상태
		vo.setFilename("");
		vo.setFilesize(0);
	}
	else
	{
		File file = new File(path+"\\"+filename);
		vo.setFilename(filename);
		vo.setFilesize((int)file.length());
	}
	
	// 4. dao에 전송
	DataBoardDAO dao = DataBoardDAO.newInstance();
	dao.databoardInsert(vo);
	
	// 5. 화면 이동
	response.sendRedirect("list.jsp");
	
%>