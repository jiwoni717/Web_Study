package com.sist.dao;

import java.util.*;
import java.sql.*;

public class memberDAO {
	//연결객체
		private Connection conn;
		//송수신
		private PreparedStatement ps;
		//오라클 URL
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		//싱글턴
		private static memberDAO dao;
		//1. 드라이버 등록 => 한번 수행 => 시작과 동시에 한번 수행, 멤버변수 초기화 : 생성자
		public memberDAO() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//ClassNotFoundException => 체크예외처리 => 반드시 예외처리 한다
				//java.io, java.net, java.sql
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//2. 오라클 연결
		public void getConnection() {
			try {
				conn=DriverManager.getConnection(URL, "hr", "happy");
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//3. 오라클 해제
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
		//4. 싱글턴 설정 
		//메모리 누수 현상을 방지
		//싱글턴은 데이터베이스에서는 필수 조건
		public static memberDAO newInstance() {
			if(dao==null)
				dao=new memberDAO();
			
			return dao;
		}
		
		//5. 기능
		//5-1. 우편번호 검색
		public List<zipcodeVO> postfind(String dong){
			List<zipcodeVO> list = new ArrayList<zipcodeVO>();
			
			try {
				getConnection();
				
				String sql = "SELECT zipcode, sido, gugun, dong, NVL(bunji,' ') FROM zipcode "
						+ "WHERE dong LIKE '%'||?||'%'";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dong);
				
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					zipcodeVO vo = new zipcodeVO();
					vo.setZipcode(rs.getString(1));
					vo.setSido(rs.getString(2));
					vo.setGugun(rs.getString(3));
					vo.setDong(rs.getString(4));
					vo.setBunji(rs.getString(5));
					list.add(vo);
				}
				rs.close();
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally {
				disConnection();
			}
			return list;
		}
		
		public int postfindCount(String dong){
			int count=0;
			
			try {
				getConnection();
				
				String sql = "SELECT Count(*) FROM zipcode "
						+ "WHERE dong LIKE '%'||?||'%'";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dong);
				
				ResultSet rs = ps.executeQuery();
				rs.next();
				count = rs.getInt(1);
				rs.close();
				
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
			finally {
				disConnection();
			}
			
			return count;
		}
}
