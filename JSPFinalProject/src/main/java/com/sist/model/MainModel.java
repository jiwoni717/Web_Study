package com.sist.model;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.controller.RequestMapping;
import com.sist.vo.*;
import com.sist.dao.*;

public class MainModel {
	
	@RequestMapping("main/main.do")
	public String main_page(HttpServletRequest request, HttpServletResponse response)
	{
		// home.jsp로 카테고리 보내기
		FoodDAO dao = FoodDAO.newInstance();
		List<CategoryVO> list = dao.foodCategoryListData();
		request.setAttribute("list", list);
		request.setAttribute("main_jsp", "../main/home.jsp");
		return "../main/main.jsp";
	}
}
