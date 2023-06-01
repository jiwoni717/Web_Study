package com.sist.dao;
//오라클만 연결 => SELECT, UPDATE, INSERT, DELETE
import java.util.*;

import com.sist.vo.BoardVO;

import java.sql.*;

public class BoardDAO {
	//연결 객체
	private Connection conn;
	//송수신 객체(오라클 (SQL문장 전송), 실행 결과값을 읽어 온다)
	private PreparedStatement ps;
	//모든 사용자가 1개의 DAO만 사용할 수 있게 만든다(싱글턴)
	private static BoardDAO dao;
	//오라클 연결 주소=> 상수형
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	
	//1. 드라이버 등록
	public BoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//2. 싱글턴 (메모리 누수 ,Connection객체 생성 갯수를 제한)
	public static BoardDAO newInstance() {
		if(dao == null) {
			dao = new BoardDAO();
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
	///////////==> 필수 ==> 클래스화(라이브러리)
	//5. 기능
	//5-1 목록출력 => 페이지 나누기(인라인뷰)
	//1page => 10개
	//BoardVO(게시물 1개)
	public List<BoardVO> boardListData(int page){
		List<BoardVO> list = new ArrayList<BoardVO>();
		
		//연결 => SQL문장 => 전송 => 사용자가 요청한 데이터 첨부 => 실행요청 후 결과값을 받음 => 받은 결과값을 list에 첨부
		try {
			getConnection();
			String sql = "SELECT no,subject, name,TO_CHAR(regdate, 'YYYY-MM-DD'), hit, num "
						+ "FROM (SELECT no, subject, name, regdate, hit, rownum as num "
						+ "FROM (SELECT no, subject, name, regdate, hit "
						+ "FROM freeboard ORDER BY no DESC)) "
						+ "WHERE num BETWEEN ? AND ?";
			//rownum은 중간에서 데이터를 추출 할 수 없다
			ps = conn.prepareStatement(sql);
			
			int rowSize=10;
			int start = (page-1)*rowSize+1;
			
			int end = page*rowSize;
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
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
	//5-1-1. 총페이지 구하기
	public int boardTotalPage() {
		int total=0;
		try {
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/10.0) "
						+ "FROM freeboard";
			
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return total;
	}
	//5-2 상세보기 => 조회수 증가(UPDATE), 상세볼 게시물 읽기(SELECT)
	public BoardVO boardDetailData(int no) {
		BoardVO vo = new BoardVO();
		
		try {
			getConnection();
			String sql = "UPDATE freeboard SET "
						+ "hit=hit+1 "
						+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ps.executeUpdate();
			
			sql="SELECT no,name,subject,content,TO_CHAR(regdate,'yyyy-MM-dd'),hit "
				+ "FROM freeboard "
				+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setDbday(rs.getString(5));
			vo.setHit(rs.getInt(6));

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return vo;
	}
	//5-3 게시물 등록 => INSERT
	public void boardInsert(BoardVO vo) {
		try {
			getConnection();
			String sql = "INSERT INTO freeboard(no,name,subject,content,pwd)"
						+ "VALUES(fb_no_seq.nextval,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			ps.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	//5-4 수정(UPDATE) => 먼저 입력된 게시물 읽기, 실제 수정(비밀번호 검색)
	public boolean boardUpdate(int no, String pwd, String name, String subject, String content) {
		BoardVO vo = new BoardVO();
		boolean bCheck=false;
		try {
			getConnection();
			String sql = "SELECT pwd "
						+ "FROM freeboard "
						+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			System.out.println(rs.getString(1) + " dao");
			if(rs.getString(1).equals(pwd)) {
				sql = "UPDATE freeboard SET "
					+ "name=?, subject=?, content=? "
					+ "WHERE no="+no;
				
				ps=conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, subject);
				ps.setString(3, content);
				ps.executeUpdate();
				bCheck=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return bCheck;
	}
	//5-4-1
	public BoardVO boardData(int no) {
		BoardVO vo = new BoardVO();
		
		try {
			getConnection();
			String sql="SELECT no,name,subject,content,pwd "
				+ "FROM freeboard "
				+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setPwd(rs.getString(5));

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return vo;
	}
	
	//5-5 삭제(DELETE) => 비밀번호검색
	public boolean boardDelete(int no, String pwd) {
		boolean bCheck=false;
		
		try {
			getConnection();
			String sql = "SELECT pwd "
						+ "FROM freeboard "
						+ "WHERE no="+no;
			ps=conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			if(db_pwd.equals(pwd)) {
				sql="DELETE FROM freeboard "
					+ "WHERE no="+no;
				ps=conn.prepareStatement(sql);
				ps.executeUpdate();
				bCheck=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		
		return bCheck;
	}
	//5-6 찾기(이름, 제목, 내용) => LIKE
}
