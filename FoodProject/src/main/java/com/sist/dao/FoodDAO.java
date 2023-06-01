package com.sist.dao;
import java.sql.*;
import java.util.*;
import com.sist.vo.*;
/*
 * 1. 드라이버 등록
 * 	  ----- 오라클 연결하는 라이브러리 (ojdbc8.jar)
 * 		OracleDriver => 메모리 할당
 * 2. 오라클 연결
 * 		Connection
 * 3. SQL문장을 전송
 * 		PreparedStatement
 * 4. SQL문장 실행 요청
 * 		=executeUpdate() => INSERT,UPDATE,DELETE
 * 		 ------------- COMMIT(AutoCommit)
 * 		=excuteQuery() => SELECT
 * 		 ------------- 결과값을 가지고 온다
 * 					   ----
 * 					   ResultSet
 * 
 * 5. 닫기
 * 	  생성 반대로 닫는다
 * 	  rs.close(), ps.close(), conn.close()
 */

public class FoodDAO {
	//기능 => INSERT => 데이터수집(파일)
	private Connection conn; //오라클 연결객체(데이터베이스 연결)
	private PreparedStatement ps; //SQL문장 전송 / 결과값 읽기
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	//mySQL => jdbc:mysql://localhost/mydb
	private static FoodDAO dao; //싱글톤 패턴
	//DAO객체를 한개만 사용이 가능하게 만든다
	//드라이버 설치 => 소프트웨어(메모리 할당 요청) Class.forName()
	//클래스의 정보를 전송
	//드라이버 설치는 1번만 수행
	public FoodDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	//오라클 연결
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//오라클 연결 해제
	public void disConnection() {
		try {
			if(ps != null)
				ps.close();
			if(conn != null) 
				conn.close();
			//=>오라클 전송 : exit
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//DAO객체를 1개만 생성해서 사용 => 메모리 누수현상을 방지(싱글톤)
	public static FoodDAO newInstance() {
		//newInstance, getInstance() => 싱글턴
		if(dao==null)
			dao = new FoodDAO();
		return dao;
	}
	//===기본 세팅(모든 DAO)
	//기능
	//1.데이터 수집(INSERT)
	/*
	 * Statment => SQL => 생성과 동시에 데이터 추가 "'" + ....
	 * preparedStatement => 미리 SQL문장을 만들고 나중에 값을 채운다
	 * CallableStatement => Procedure 호출
	 */
	public void foodCategoryInsert(CategoryVO vo) {
		try {
			//1. 연결
			getConnection();
			//2. SQL 문장 생성
			String sql = "INSERT INTO food_category VALUES("
						+ "fc_cno_seq.nextval,?,?,?,?)";
			
			//3. SQL 오라클 전송
			ps = conn.prepareStatement(sql);
			//3-1 ?에 값을 채운다
			ps.setString(1, vo.getTitle()); //=> 자동으로 ' ' 추가
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getLink());
			//단점 => 번호가 잘못되면 오류발생, 데이터형이 다르면 오류
			//IN,OUT~
			//4. SQL 문장을 실행
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection(); // => 오라클 연결 해제 무조건
		}
	}
	
	//1-1 실제 맛집 정보 저장
	public void foodDataInsert(FoodVO vo) {
		try {
			//1. 오라클 연결
			getConnection();
			//2. sql 문장
			String sql ="INSERT INTO food_house VALUES("
						+ "fh_fno_seq.nextval, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//3. 오라클 전송
			ps=conn.prepareStatement(sql);
			//4. ?에 값을 채움
			ps.setInt(1, vo.getCno());
			ps.setString(2, vo.getName());
			ps.setDouble(3, vo.getScore());
			ps.setString(4, vo.getAddress());
			ps.setString(5, vo.getPhone());
			ps.setString(6, vo.getType());
			ps.setString(7, vo.getPrice());
			ps.setString(8, vo.getParking());
			ps.setString(9, vo.getTime());
			ps.setString(10, vo.getMenu());
			ps.setInt(11, vo.getGood());
			ps.setInt(12, vo.getSoso());
			ps.setInt(13, vo.getBad());
			ps.setString(14, vo.getPoster());
			
			/*
			fno NUMBER,
			cno NUMBER,
			name VARCHAR2(100) CONSTRAINT fh_name_nn NOT NULL,
			score number(2,1),
			address VARCHAR2(300) CONSTRAINT fh_addr_nn NOT NULL,
			phone VARCHAR2(20) CONSTRAINT fh_phone_nn NOT NULL,
			type VARCHAR2(30) CONSTRAINT fh_type_nn NOT NULL,
			price VARCHAR2(30),
			parking VARCHAR2(30),
			time VARCHAR2(20),
			menu CLOB,
			good NUMBER,
			soso NUMBER,
			bad NUMBER,  
			poster VARCHAR2(4000) CONSTRAINT fh_poster_nn NOT NULL,
			 */
			
			//실행 요청
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	
	//2. SELECT => 전체 데이터 읽기
	
	public List<CategoryVO> foodCategoryData(){
		List<CategoryVO> list = new ArrayList<CategoryVO>();
		try {
			//1.오라클 연결
			getConnection();
			//2. SQL 문장
			String sql = "SELECT cno, title,subject,poster,link "
						+ "FROM food_category";
			//3. 오라클 전송
			ps = conn.prepareStatement(sql);
			//4. 결과값 받기
			ResultSet rs = ps.executeQuery();
			//rs에 있는 데이터를 list에 저장
			while(rs.next()) {
				CategoryVO vo = new CategoryVO();
				vo.setCno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSubject(rs.getString(3));
				String poster = rs.getString(4);
				poster = poster.replace('#', '&');
				vo.setPoster(poster);
				vo.setLink("https://www.mangoplate.com" + rs.getString(5));
				list.add(vo);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	//3. 상세보기 => WHERE
	
//	public static void main(String[] args) {
//		FoodDAO dao = FoodDAO.newInstance();
//		List<CategoryVO> list = new ArrayList<CategoryVO>();
//		list = dao.foodCategoryData();
//		
//		System.out.println(list.get(0).getTitle());
//		
//	}
}
