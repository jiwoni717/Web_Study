package com.sist.dao;
import java.util.*;
import java.sql.*;

public class FoodDAO {
	//연결 객체
		private Connection conn;
		//송수신 객체(오라클 (SQL문장 전송), 실행 결과값을 읽어 온다)
		private PreparedStatement ps;
		//모든 사용자가 1개의 DAO만 사용할 수 있게 만든다(싱글턴)
		private static FoodDAO dao;
		//오라클 연결 주소=> 상수형
		private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
		
		//1. 드라이버 등록
		public FoodDAO() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//2. 싱글턴 (메모리 누수 ,Connection객체 생성 갯수를 제한)
		public static FoodDAO newInstance() {
			if(dao == null) {
				dao = new FoodDAO();
			}
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
		
		public List<FoodVO> foodAllData(){
			List<FoodVO> list = new ArrayList<FoodVO>();
			try {
				getConnection();
				String sql = "SELECT name, address,poster,phone,type "
							+ "FROM food_house "
							+ "ORDER BY fno ASC";
				ps=conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					FoodVO vo = new FoodVO();
					String name=rs.getString(1);
					vo.setName(name);
					String addr=rs.getString(2);
					addr=addr.substring(0, addr.lastIndexOf("지번"));
					vo.setAddress(addr.trim());
					String poster = rs.getString(3);
					poster=poster.substring(0,poster.indexOf("^"));
					poster=poster.replace("#","&");
					vo.setPoster(poster);
					vo.setTel(rs.getString(4));
					vo.setType(rs.getString(5));
					list.add(vo);
				}
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				disConnection();
			}
			
			return list;
		}
}
