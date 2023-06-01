package com.sist.food;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection.Response;

import java.util.*;
import com.sist.dao.*;
@WebServlet("/FoodSearchServlet")
public class FoodSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		//사용자 요청값을 받는다
		request.setCharacterEncoding("UTF-8");
		String addr = request.getParameter("addr");
		if(addr==null) {
			addr="마포";
		}
		
		String strPage=request.getParameter("page");
		if(strPage==null) {
			strPage="1";
		}
		
		int curpage=Integer.parseInt(strPage);
		FoodDAO dao = FoodDAO.newInstance();
		List<FoodVO> list = dao.foodFindData(addr, curpage);
		int totalpage=(int)(Math.ceil(dao.foodRowCount(addr)/12.0));
		int count=dao.foodRowCount(addr);
		final int BLOCK=5;
		int startPage=((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		
		if(endPage>totalpage) {
			endPage=totalpage; 
		}
		//화면
		PrintWriter out =response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".row{");
		out.println("margin:0px auto;");
		out.println("width:1024px");
		out.println("}");
		
		out.println("</style>");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<div class=container>");
		
		out.println("<div class=row>");
		
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<form method=post action=FoodSearchServlet>");
		out.println("<input type=text name=addr size=25 class=input-sm>");
		out.println("<input type=submit value=검색 class=\"btn btn-sm btn-danger\">");
		out.println("</form>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		out.println("<div style=\"height=30px\"></div>");
		for(FoodVO vo:list) {
			
			out.println("<div class=\"col-md-3\">");
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"#"+"\">");
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:9px\">"+vo.getName()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		
		out.println("</div>");//row
		out.println("<div style=\"height=10px\"></div>");
		
		out.println("<div class=row>");
		out.println("<div class=text-center>");
		out.println("<ul class=pagination>");
		out.println("<li><a href=#>&lt;</a></li>");
		for(int i=startPage;i<=endPage;i++) {
			out.println("<li "+(curpage==i?"class=active":"")+"><a href=FoodSearchServlet?page="+i+">"+i+"</a></li>");
		}
		out.println("<li><a href=#>&gt;</a></li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		
		out.println("</div>");//container
		out.println("</body>");
		
		out.println("</html>");
		/*
			<ul class="pagination">
			  <li><a href="#">1</a></li>
			  <li><a href="#">2</a></li>
			  <li><a href="#">3</a></li>
			  <li><a href="#">4</a></li>
			  <li><a href="#">5</a></li>
			</ul>
		 */
	}
	
	
}
