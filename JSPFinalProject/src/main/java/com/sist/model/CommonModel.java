package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import com.sist.dao.*;
import com.sist.vo.*;

public class CommonModel {
	public static void commonRequestData(HttpServletRequest request)
	{
		// footer
		FoodDAO dao = FoodDAO.newInstance();
		List<FoodVO> flist = dao.foodTop7();
		request.setAttribute("flist", flist);
		
	}
}
