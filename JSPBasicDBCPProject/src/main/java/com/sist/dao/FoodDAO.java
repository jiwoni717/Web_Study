/*
 		<Resource 
	      	driverClassName="oracle.jdbc.driver.OracleDriver"
	      	url="jdbc:oracle:thin:@localhost:1521:XE"
	      	username="hr"
	      	password="happy"
	      	----------------- ↑Connection 생성시 필요한 정보 -----------------
	      	maxActive="10" : POOL의 Connection 객체를 다 사용하면 추가
	      	maxIdle="8" : POOL안에 Connection 객체 저장 갯수
	      	maxWait="-1" : Connection 다 사용시 반환 대기 시간(-:무한)
	      	name="jdbc/oracle" : Connection 객체를 찾기 위한 이름(key), lookup(key) → 객체 반환
	      	auth="Container" : DBCP를 관리 → 톰캣
	      	type="javax.sql.DataSource" : Connection 주소를 얻은 경우에 DataSource는 데이터베이스 전체 연결 정보를 가지고 있음
		/>
 */
package com.sist.dao;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*; // jdbc/oracle이란 이름의 객체 주소 찾기 context

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static FoodDAO dao;
	
	// 사용자 요청 → Connection객체 얻기
	public void getConnection() {
		try {
			Context init = new InitialContext();
			Context cdriver = (Context)init.lookup("java://comp/env");
			DataSource ds = (DataSource)cdriver.lookup("jdbc/oracle");
			conn = ds.getConnection();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
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
	
	public List<FoodBean> foodFindData(int page, String fd){
		List<FoodBean> list = new ArrayList<FoodBean>();
		
		try {
			getConnection();
			
			String sql="SELECT fno, name, poster, address, num "
					+ "FROM (SELECT fno, name, poster, address, rownum AS num "
					+ "FROM (SELECT /*+ INDEX_ASC(food_location fl_fno_pk)*/fno, name, poster, address FROM food_location WHERE address LIKE '%'||?||'%')) "
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, fd);
			int rowSize = 12;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				FoodBean vo = new FoodBean();
				vo.setFno(rs.getInt(1));
				vo.setName(rs.getString(2));
				
				String poster = rs.getString(3);
				poster = poster.substring(0, poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				
				String addr = rs.getString(4);
				String addr1 = addr.substring(addr.indexOf(" "));
				addr1 = addr.trim();
				String addr2 = addr1.substring(0, addr1.indexOf(" "));
				vo.setAddress(addr2);
				
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
	
	public int foodTotalPage(String addr) {
		int total = 0;
		
		try {
			getConnection();
			
			String sql="SELECT CEIL(COUNT(*)/20.0) FROM food_location WHERE address LIKE '%'||?||'%'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, addr);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
		
		return total;
	}
}
