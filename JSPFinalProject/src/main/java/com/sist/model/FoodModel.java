/*
		MVC
		  1. DAO 연결 → 오라클 연결 담당
		  2. DAO, 브라우저(JSP) → 연결 → 결과값 → Model
		  3. 브라우저로 값 전송 → 요청 → Controller
		  4. 전송받은 데이터 출력 : View(JSP)
		
		동작
		  1. 사용자가 요청 : <a>, <form>, ajax → 지정된 URL(*.do) 사용
		  2-1. DispatcherServlet이 요청 내용을 받음 → service()
		  2-2. 요청 처리에 해당되는 메소드를 찾는다 → 어노테이션(@RequestMapping)
		  		메소드 찾기 : @Target(METHOD)
		  		클래스 찾기 : Model만 찾음
		  		생성자 찾기
		  		매개변수 찾기
		  2-3. 메소드 찾아서 → 요청 데이터를 Model로 전송 → m.invoke(obj, request, response) → 메소드명은 자유롭게 만들 수 있음
		  3. Model → 요청 처리, 결과값 담기 → DAO(오라클) → 연결 → 결과값을 request에 담음
		  4. DispatcherServlet이 request에 담긴 데이터를 JSP로 전송
		  		RequestDispatcher rd = request.getRequestDispatcher(jsp)
		  		rd.forward(request, response) → 해당 JSP를 찾아서 request 전송 역할
		  5. JSP가 request에 담긴 데이터 출력
		  ---------------------------------------------------------------------------------------------------------
		  어노테이션 : 밑(옆)에 있는 class, method, 변수... 찾기 → if문 추가
		  		@Controller → 메모리 할당 관련
		  		class A
		  		{
		  			@Autowired
		  			B b; → 자동으로 b의 주소값 대입
		  			@RequestMapping("food/list.do")
		  			public void disp(@Resource int a)
					{
					}
		  		}
		  
		  회원가입 / 로그인 → main.do
		  예약 / 장바구니 / 결제 → mypage.do
 */
package com.sist.model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.controller.RequestMapping;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;

public class FoodModel {
	@RequestMapping("food/location_find.do")
	public String food_fine(HttpServletRequest request, HttpServletResponse response)
	{	
		// 검색어 받기
		try {
			request.setCharacterEncoding("UTF-8");
		}catch(Exception ex) {}
		String fd = request.getParameter("fd");
		if(fd==null) {
			fd = "마포";
		}
		
		String page = request.getParameter("page");
		if(page==null)
			page="1";
		int curpage = Integer.parseInt(page);
		
		// 값 출력 하기 전에 데이터 받기 → DAO 연결해서 값 넘겨주기
		FoodDAO dao = FoodDAO.newInstance();
		List<FoodVO> list = dao.foodLocationFindData(fd, curpage);
		int totalpage = dao.foodLocationTotalPage(fd);
		
		final int BLOCK = 10;
		int startPage = ((curpage-1)/BLOCK*BLOCK)+1;
		int endPage = ((curpage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalpage) {
			endPage = totalpage;
		}
		
		// location_find.jsp로 전송할 데이터 모아줌
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("list", list);
		request.setAttribute("fd", fd);
		request.setAttribute("main_jsp", "../food/location_find.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("food/food_category_list.do")
	public String food_list(HttpServletRequest request, HttpServletResponse response)
	{
		String cno = request.getParameter("cno");
		FoodDAO dao = FoodDAO.newInstance();
		CategoryVO cvo = dao.foodCategoryInfoData(Integer.parseInt(cno));
		List<FoodVO> list = dao.foodCategoryListData(Integer.parseInt(cno));
		
		request.setAttribute("cvo", cvo);
		request.setAttribute("list", list);
		request.setAttribute("main_jsp", "../food/food_category_list.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("food/food_detail_before.do")
	public String food_detail_before(HttpServletRequest request, HttpServletResponse response)
	{
		// 쿠키 생성
		String fno = request.getParameter("fno");
		Cookie cookie = new Cookie("food_"+fno, fno); // 문자열만 저장 가능, 요청한 사용자의 브라우저로 전송
		// 저장 위치 결정
		cookie.setPath("/");
		// 저장 기간 설정
		cookie.setMaxAge(60*60*24);
		// 전송
		response.addCookie(cookie);
		
		return "redirect:../food/food_detail.do?fno="+fno;
	}
	
	@RequestMapping("food/food_detail.do")
	public String food_detail(HttpServletRequest request, HttpServletResponse response)
	{
		String fno = request.getParameter("fno");
		FoodDAO dao = FoodDAO.newInstance();
		FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));
		String address = vo.getAddress();
		String addr1 = address.substring(0, address.indexOf("지번"));
		String addr2 = address.substring(address.indexOf("지번")+2);
		
		request.setAttribute("addr1", addr1.trim());
		request.setAttribute("addr2", addr2.trim());
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../food/food_detail.jsp");
		CommonModel.commonRequestData(request);
		
		// 댓글 읽기
		ReplyDAO rdao = ReplyDAO.newInstance();
		List<ReplyVO> list = rdao.replyListData(1, Integer.parseInt(fno));
		request.setAttribute("rList", list);
		
		return "../main/main.jsp";
	}
}
