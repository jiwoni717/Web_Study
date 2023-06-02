package com.sist.dao;

import java.util.*;
import java.sql.*;

public class GoodsDAO {
		private Connection conn;
		private PreparedStatement ps;
		private static GoodsDAO dao;
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		
		//1. 드라이버 등록
		public GoodsDAO() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//2. 싱글턴 (메모리 누수 ,Connection객체 생성 갯수를 제한)
		public static GoodsDAO newInstance() {
			
			if(dao == null)
				dao = new GoodsDAO();
			return dao;
		}
		//3. 오라클 연결
		public void getConnection() {
			try {
				conn=DriverManager.getConnection(URL, "hr", "happy");
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//4. 오라클 해제
		public void disConnection() {
			try {
				if(ps!=null)
					ps.close();
				if(conn!=null)
					conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		//5. 기능
		public GoodsVO goodsDetailData(int no)
		{
			GoodsVO vo = new GoodsVO();
			try {
				getConnection();
				
				String sql="SELECT no, goods_name, goods_sub, goods_price, goods_discount, goods_first_price, goods_delivery, goods_poster "
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
				vo.setFirst_price(rs.getString(6));
				vo.setDelivery(rs.getString(7));
				vo.setPoster(rs.getString(8));
				
				rs.close();
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally {
				disConnection();
			}
			return vo;
		}
		
}
