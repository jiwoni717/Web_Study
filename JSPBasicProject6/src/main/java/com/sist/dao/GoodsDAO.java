package com.sist.dao;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;

public class GoodsDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static GoodsDAO dao;
	
	public void getConnection() {
		try {

			Context init = new InitialContext();
			Context cdriver = (Context)init.lookup("java://comp/env"); // lookup → 문자열(key)로 객체 찾기(RMI)
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
			
	public static GoodsDAO newInstance(){
		if(dao==null)
			dao = new GoodsDAO();
		return dao;
	}
	
	// 로그인 처리
	public String isLogin(String id, String pwd) {
		String result = "";
		
		try {
			getConnection();
			
			// 아이디 존재 여부 확인
			String sql = "SELECT COUNT(*) FROM jspMember WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			
			// 비밀번호 확인
			if(count==0) {
				result = "NOID";
			}
			else {
				sql = "SELECT pwd, name FROM jspMember WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				rs.next();
				String db_pwd = rs.getString(1);
				String name = rs.getString(2);
				rs.close();
				
				if(db_pwd.equals(pwd)) {
					result=name;
				}
				else {
					result="NOPWD";
				}
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
		
		return result;
	}
	
	public int goodsTotalPage() {
		int total = 0;
		
		try {
			getConnection();
			
			String sql = "SELECT CEIL(COUNT(*)/12.0) FROM goods_all";
			ps = conn.prepareStatement(sql);
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
	
	// 목록 출력
	public List<GoodsBean> goodsListData(int page){
		List<GoodsBean> list = new ArrayList<GoodsBean>();
		
		try {
			getConnection();
			
			String sql = "SELECT no, goods_name, goods_poster, num "
					+ "FROM (SELECT no, goods_name, goods_poster, rownum AS num "
					+ "FROM (SELECT /*+ INDEX_ASC(goods_all ga_no_pk)*/no, goods_name, goods_poster "
					+ "FROM goods_all)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			int rowSize = 12;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				GoodsBean vo = new GoodsBean();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setPoster(rs.getString(3));
				
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
	
	// 상세 보기
	public GoodsBean goodsDetailData(int no) {
		GoodsBean vo = new GoodsBean();
		
		try {
			getConnection();
			
			String sql = "SELECT no, goods_name, goods_sub, goods_price, goods_discount, goods_first_price, goods_delivery, goods_poster "
					+ "FROM goods_all WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSub(rs.getString(3));
			vo.setPrice(rs.getString(4));
			vo.setDiscount(rs.getInt(5));
			vo.setFp(rs.getString(6));
			vo.setDelivery(rs.getString(7));
			vo.setPoster(rs.getString(8));
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			disConnection();
		}
		
		return vo;
	}
	
	// 장바구니 → session
	
	// 구매
}
