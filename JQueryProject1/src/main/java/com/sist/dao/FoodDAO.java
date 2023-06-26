package com.sist.dao;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static FoodDAO dao;
	
	public void getConnection() {
		try {
			Context init = new InitialContext();
			Context c = (Context)init.lookup("java://comp/env");
			DataSource ds = (DataSource)c.lookup("jdbc/oracle");
			conn = ds.getConnection();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch (Exception ex) {}
	}
	
	public static FoodDAO newInstance(){
		if(dao==null)
			dao = new FoodDAO();
		return dao; 
	}
	
	public List<FoodBean> foodAllData(){
		List<FoodBean> list = new ArrayList<FoodBean>();
		
		try {
			getConnection();
			
			String sql = "SELECT poster, name, phone FROM food_house";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				FoodBean vo = new FoodBean();
				vo.setName(rs.getString(2));
				vo.setTel(rs.getString(3));
				
				String poster = rs.getString(1);
				poster = poster.substring(0, poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				
				list.add(vo);
			}
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
		
		return list;
	}
	
}
