<%--
		내장 객체
		out : JspWriter(출력 버퍼 관리)
			1) 출력(메모리)
		 	  println()
			2) 메모리 확인
		  	  getBufferSize() : 총 버퍼 크기
		  	  getRemaining() : 사용중인 버퍼 크기
		  	3) 버퍼 지우기
		  	  clear()
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*, java.util.*"%>
<!-- 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
-->
<%

EmpDAO dao = new EmpDAO();
List<EmpVO> list = dao.empListData();

out.write("<!DOCTYPE html>");
out.write("<html>");
out.write("<head>");
out.write("<meta charset=\"UTF-8\">");
out.write("<title>JDBC Project</title>");
out.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
out.write("<style>");
    out.write(".container{margin-top:50px}");
    out.write(".row{margin:0px auto; width:800px}");
    out.write("h2{text-align:center}");
    out.write("a{text-decoration:none;}");
out.write("</style>");
out.write("</head>");

out.write("<body>");

    out.write("<div class=container>");
    	out.write("<h2>사원 목록</h2>");
    	
	    	out.write("<div class=row>");
	    	
		    	out.write("<table class=\"table table-striped\">");
			    	out.write("<tr class=success>");
			    		out.write("<th class=text-center>사번</th>");
			    		out.write("<th class=text-center>이름</th>");
			    		out.write("<th class=text-center>직위</th>");
			    		out.write("<th class=text-center>입사일</th>");
			    		out.write("<th class=text-center>급여</th>");
			    		out.write("<th class=text-center>성과급</th>");
			    		out.write("<th class=text-center>부서명</th>");
			    		out.write("<th class=text-center>근무지</th>");
			    		out.write("<th class=text-center>호봉</th>");
			    	out.write("</tr>");
			    	
			    	for(EmpVO vo:list)
			    	{
			    		out.write("<tr>");
				    		out.write("<td class=text-center>"+vo.getEmpno()+"</td>");
				    		out.write("<td class=text-center><a href=\"MainServlet?mode=2&empno="+vo.getEmpno()+"\">"+vo.getEname()+"</a></td>");
				    		out.write("<td class=text-center>"+vo.getJob()+"</td>");
				    		out.write("<td class=text-center>"+vo.getDbday()+"</td>");
				    		out.write("<td class=text-center>"+vo.getDbsal()+"</td>");
				    		out.write("<td class=text-center>"+vo.getComm()+"</td>");
				    		out.write("<td class=text-center>"+vo.getDvo().getDname()+"</td>");
				    		out.write("<td class=text-center>"+vo.getDvo().getLoc()+"</td>");
				    		out.write("<td class=text-center>"+vo.getSvo().getGrade()+"</td>");
			    		out.write("</tr>");
			    	}
			    	
		    	out.write("</table>");
		    	
	    	out.write("</div>");
	    	
    out.write("</div>");
    
out.write("</body>");

out.write("</html>");

%>