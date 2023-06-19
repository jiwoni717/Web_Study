package com.sist.servlet;

import java.io.*;
import com.sist.dao.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmpDetailServlet")
public class EmpDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		String empno = request.getParameter("empno");
		EmpDAO dao = new EmpDAO();
		EmpVO vo = dao.empDetailData(Integer.parseInt(empno));
		
		PrintWriter out = response.getWriter();
		
		out.write("<!DOCTYPE html>");
	    out.write("<html>");
	    out.write("<head>");
	    out.write("<meta charset=\"UTF-8\">");
	    out.write("<title>JDBC Project</title>");
	    out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
	    out.write("<style>");
		    out.write(".container{margin-top:50px}");
		    out.write(".row{margin:0px auto; width:600px}");
		    out.write("h2{text-align:center}");
	    out.write("</style>");
	    out.write("</head>");
	    
	    out.write("<body>");
		    out.write("<div class=container>");
		    out.write("<h2>"+vo.getEname()+"님의 정보</h2>");
		    	
		    	out.write("<div class=row>");
			    	out.write("<table class=table>");
			    	
				    	out.write("<tr>");
					    	out.write("<th class=\"text-center danger\" width=20%>사번</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+vo.getEmpno()+"</td>");
					    	out.write("<th class=\"text-center danger\" width=20%>이름</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+vo.getEname()+"</td>");
				    	out.write("</tr>");
				    	
				    	out.write("<tr>");
					    	out.write("<th class=\"text-center danger\" width=20%>직위</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+vo.getJob()+"</td>");
					    	out.write("<th class=\"text-center danger\" width=20%>입사일</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+vo.getDbday()+"</td>");
				    	out.write("</tr>");
				    	
				    	out.write("<tr>");
					    	out.write("<th class=\"text-center danger\" width=20%>급여</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+vo.getDbsal()+"</td>");
					    	out.write("<th class=\"text-center danger\" width=20%>성과급</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+(vo.getComm()==0?"":vo.getComm())+"</td>");
				    	out.write("</tr>");
			    	
				    	out.write("<tr>");
					    	out.write("<th class=\"text-center danger\" width=20%>사수번호</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+(vo.getMgr()==0?"":vo.getMgr())+"</td>");
					    	out.write("<th class=\"text-center danger\" width=20%>부서명</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+vo.getDvo().getDname()+"</td>");
				    	out.write("</tr>");
				    	
				    	out.write("<tr>");
					    	out.write("<th class=\"text-center danger\" width=20%>근무지</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+vo.getDvo().getLoc()+"</td>");
					    	out.write("<th class=\"text-center danger\" width=20%>호봉</th>");
					    	out.write("<td class=\"text-center\" width=30%>"+vo.getSvo().getGrade()+"</td>");
				    	out.write("</tr>");
				    	
				    	out.write("<tr>");
					    	out.write("<td colspan=4 class=text-right>");
						    	out.write("<a href=MainServlet?mode=4&empno="+empno+" class=\"btn btn-sm btn-success\">수정</a>&nbsp;");
						    	out.write("<a href=# class=\"btn btn-sm btn-danger\">삭제</a>&nbsp;");
						    	out.write("<a href=MainServlet class=\"btn btn-sm btn-default\">목록</a>");
					    	out.write("</td>");
				    	out.write("</tr>");
				    	
			    	out.write("</table>");
			    out.write("</div>");
			    
		    out.write("</div>");
	    out.write("</body>");
	    
	    out.write("</html>");
	    out.write("");
	}

}
