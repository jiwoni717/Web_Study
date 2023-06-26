package com.sist.dao;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;

public class FoodDAO {
	
	private Connection conn;
	private PreparedStatement ps;
	private static FoodDAO dao;
	
	// 출력갯수
	private final int ROW_SIZE=20;
	
	// Pool 영역에서 Connection객체 얻어오기
	public void getConnection() {
/*
		Connection 객체 주소를 메모리에 저장
		저장 공간 → POOL 영역(디렉토리 형식으로 저장) → JNDI
		java://env//comp → C드라이버 → jdbc/oracle
 */
		
		try {
			// 1. 탐색기 열기
			Context init = new InitialContext();
			
			// 2. C드라이버에 연결
			Context cdriver = (Context)init.lookup("java://comp/env"); // lookup → 문자열(key)로 객체 찾기(RMI)
			
			// 3. 저장된 Connection 객체 찾기
			DataSource ds = (DataSource)cdriver.lookup("jdbc/oracle");
			
			// 4. Connection 주소를 연결
			conn = ds.getConnection();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Connection객체 사용 후에 반환
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	
	public static FoodDAO newInstance(){
		if(dao==null)
			dao = new FoodDAO();
		return dao;
	}
	
	// 기능
	public List<FoodBean> foodListData(int page){
		List<FoodBean> list = new ArrayList<FoodBean>();
		
		try {
			getConnection();
			
			String sql = "SELECT fno, poster, name, num "
					+ "FROM (SELECT fno, poster, name, rownum AS num "
					+ "FROM (SELECT /*+ INDEX_ASC(food_location fl_fno_pk)*/fno, poster, name FROM food_location)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			int rowSize = ROW_SIZE;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				FoodBean vo = new FoodBean();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(3));
				
				String poster = rs.getString(2);
				poster = poster.substring(0, poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				
				list.add(vo);
			}
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection(); // 반환
		}
		
		return list;
	}
	
	public int foodTotalPage() {
		int totalpage = 0;
		
		try {
			getConnection();
			
			String sql="SELECT CEIL(COUNT(*)/"+ROW_SIZE+") FROM food_location";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			totalpage = rs.getInt(1);
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
		
		return totalpage;
	}
}
