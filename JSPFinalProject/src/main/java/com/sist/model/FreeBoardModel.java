package com.sist.model;

import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.controller.RequestMapping;
import com.sist.vo.*;
import com.sist.dao.*;

public class FreeBoardModel {
	
	@RequestMapping("board/list.do")
	public String board_list(HttpServletRequest request,HttpServletResponse response) {
	      
	      // JSP 첫줄에 <% %> 이 부분
	      String page=request.getParameter("page");
	      if(page==null)
	         page="1";
	      int curpage=Integer.parseInt(page);
	      
	      // dao 연동
	      FreeBoardDAO dao=FreeBoardDAO.newInstance();
	      
	      // 게시물 목록
	      List<FreeBoardVO> list=dao.freeboardListData(curpage);
	      
	      int totalpage=dao.freeboardTotalPage();
	      
	      // 총페이지
	      request.setAttribute("list", list);
	      request.setAttribute("curpage", curpage);
	      request.setAttribute("totalpage", totalpage);
	      request.setAttribute("main_jsp", "../board/list.jsp");
	      
	      CommonModel.commonRequestData(request);
	      return "../main/main.jsp";
	      // header와 footer 사이 main 부분을 바꿔주는 것 (일부 화면만 바꿔준다) 
	      
	 }
	
	@RequestMapping("board/insert.do")
	public String board_insert(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("main_jsp", "../board/insert.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("board/insert_ok.do")
	public String board_insert_ok(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			request.setCharacterEncoding("UTF-8");
			
			
		}catch(Exception ex) {}
		
		FreeBoardVO vo = new FreeBoardVO();
		vo.setName(request.getParameter("name"));
		vo.setSubject(request.getParameter("subject"));
		vo.setContent(request.getParameter("content"));
		vo.setPwd(request.getParameter("pwd"));
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		dao.freeboardInsert(vo);
		
		return "redirect:../board/list.do"; // 재호출
	}
	
	// 상세보기
	@RequestMapping("board/detail.do")
	public String board_detail(HttpServletRequest request, HttpServletResponse response) {
		
		String no = request.getParameter("no");
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		FreeBoardVO vo = dao.freeDetailData(Integer.parseInt(no));
		
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../board/detail.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	// Ajax
	@RequestMapping("board/delete.do")
	public void board_delete(HttpServletRequest request, HttpServletResponse response) {
		String no = request.getParameter("no");
		String pwd = request.getParameter("pwd");
		
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		String res = dao.freeboardDelete(Integer.parseInt(no), pwd);
		
		try {
			PrintWriter out = response.getWriter();
			out.println(res);
		}catch(Exception ex) {}
	}
	
	@RequestMapping("board/update.do")
	public String board_update(HttpServletRequest request, HttpServletResponse response) {
		
		String no = request.getParameter("no");
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		FreeBoardVO vo = dao.freeUpdateData(Integer.parseInt(no));
		
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../board/update.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("board/update_ok.do")
	public String board_update_ok(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		}catch(Exception ex) {}
		
		FreeBoardVO vo = new FreeBoardVO();
		vo.setName(request.getParameter("name"));
		vo.setSubject(request.getParameter("subject"));
		vo.setContent(request.getParameter("content"));
		vo.setPwd(request.getParameter("pwd"));
		vo.setNo(Integer.parseInt(request.getParameter("no")));
		
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		boolean bCheck = dao.freeboardUpdate(vo);
		
		request.setAttribute("bCheck", bCheck);
		request.setAttribute("no", vo.getNo());
		
		return "../board/update_ok.jsp";
	}
}
