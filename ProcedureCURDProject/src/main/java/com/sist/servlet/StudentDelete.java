package com.sist.servlet;

import java.io.*;
import com.sist.dao.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentDelete")
public class StudentDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hakbun = request.getParameter("hakbun");
		StudentDAO dao = StudentDAO.newInstance();
		dao.studentDelete(Integer.parseInt(hakbun));
		
		response.sendRedirect("StudentList");
	}

}
