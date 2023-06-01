package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;
import com.sist.vo.BoardVO;

@WebServlet("/BoardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardVO vo=null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//입력폼 전송 => HTML(HTML을 브라우저로 보낸다)
				response.setContentType("text/html;charset=UTF-8");
				//메모리에 HTML을 저장 => 브라우저에서 읽어서 출력
				PrintWriter out = response.getWriter();
				int no = Integer.parseInt(request.getParameter("no"));
				BoardDAO dao = BoardDAO.newInstance();
				vo = new BoardVO();
				vo = dao.boardData(no);
				System.out.println(vo.getPwd() + " doget");
				System.out.println(vo.getContent());
				
				out.println("<html>");
				out.println("<head>");
				out.println("<link rel=stylesheet href=html/table.css");
				out.println("</head>");
				out.println("<body>");
				out.println("<center>");
				out.println("<h1>수정하기</h1>");
				out.println("<form method=post action=BoardUpdateServlet>");
				//입력된 데이터를 한번에 => action에 등록된 class로 전송
				//메소드 => method=post => doPost()
				out.println("<table class=table_content width=700>");
				out.println("<tr>");
				out.println("<th width=15%>이름</th>");
				out.println("<td width=85%><input type=text name=name size=15 value=\""+vo.getName()+"\" required></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>제목</th>");
				out.println("<td width=85%><input type=text name=subject value=\""+vo.getSubject()+"\" size=50 required></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>내용</th>");
				out.println("<td width=85%><textarea rows=10 cols=50 name=content required>"+vo.getContent()+"</textarea></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<th width=15%>비밀번호</th>");
				out.println("<td width=85%><input type=password name=pwd size=10 required></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td colspan=2 align=center>");
				out.println("<input type=submit value=수정하기>");
				out.println("<input type=button value=취소 onclick=\"javascript:history.back()\">");
				out.println("</td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</form>");
				out.println("</center>");
				
				
				out.println("");
				out.println("</html>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {
			request.setCharacterEncoding("UTF-8");//디코딩 한글변환
			//디코딩 => byte[]을 한글로 변환
			//자바 => 2byte => 브라우저는 1byte로 받는다 => 2byte
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		BoardDAO dao = BoardDAO.newInstance();
		
		PrintWriter out = response.getWriter();
		System.out.println(request.getParameter("pwd") + " dopost");
		boolean bCheck = dao.boardUpdate(vo.getNo(), request.getParameter("pwd"), request.getParameter("name"), 
				request.getParameter("subject"), request.getParameter("content"));
		if(bCheck) {
			response.sendRedirect("BoardDetailServlet?no="+vo.getNo());
		} else {
			//삭제창으로 이동
			out.println("<script>");
			out.println("alert(\"비밀번호가 틀립니다!\");");
			out.println("history.back();");
			out.println("</script>");
		}
		
	}

}
