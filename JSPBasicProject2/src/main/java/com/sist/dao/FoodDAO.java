package com.sist.dao;

import java.util.*;
import java.sql.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	private static FoodDAO dao;
	
	public FoodDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		}catch(Exception ex) {}
	}
	
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	
	public static FoodDAO newInstance() {
		if(dao==null)
			dao = new FoodDAO();
		return dao;
	}
	
	public List<FoodVO> foodListData(){
		List<FoodVO> list = new ArrayList<FoodVO>();
		
		try {
			getConnection();
			
			String sql="SELECT fno, poster, name, rownum FROM food_location WHERE rownum<=20";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				FoodVO vo = new FoodVO();
				vo.setFno(rs.getInt(1));
				String poster = rs.getString(2);
				poster = poster.substring(0, poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(3));
				list.add(vo);
			}
			rs.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			disConnection();
		}
		
		return list;
	}
	
	public FoodVO foodDetailData(int fno) {
		FoodVO vo = new FoodVO();
		
		try {
			getConnection();
			
			String spl="SELECT fno, poster, name, score, tel, type, time, parking, price, menu, address FROM food_location "
					+ "WHERE fno=?";
			ps = conn.prepareStatement(spl);
			ps.setInt(1, fno);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setPoster(rs.getString(2).replace("#", "&"));
			vo.setName(rs.getString(3));
			vo.setScore(rs.getDouble(4));
			vo.setTel(rs.getString(5));
			vo.setType(rs.getString(6));
			vo.setTime(rs.getString(7));
			vo.setParking(rs.getString(8));
			vo.setPrice(rs.getString(9));
			vo.setMenu(rs.getString(10));
			vo.setAddress(rs.getString(11));
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
}
