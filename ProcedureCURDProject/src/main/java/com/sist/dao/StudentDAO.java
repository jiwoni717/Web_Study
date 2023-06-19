package com.sist.dao;

import java.util.*;
import oracle.jdbc.OracleTypes;
import java.sql.*;

public class StudentDAO {
	private Connection conn;
	private CallableStatement cs; //함수 호출
	private final String URL="jdbc:oracle:thin:@211.238.142.111:1521:xe";
	private static StudentDAO dao;
	
	public StudentDAO()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}catch(Exception ex) {}
	}
	
	public static StudentDAO newInstance()
	{
		if(dao==null)
			dao = new StudentDAO();
		return dao;
	}
	
	// 데이터 추가
	/*
	 		CREATE OR REPLACE PROCEDURE studentInsert(
			    pname student.name%TYPE,
			    pkor student.kor%TYPE,
			    peng student.eng%TYPE,
			    pmath student.math%TYPE
			)
			IS
			BEGIN
			    INSERT INTO student VALUES(
			        (SELECT NVL(MAX(hakbun)+1,1) FROM student),
			        pname, pkor, peng, pmath
			    );
			    COMMIT;
			END;
	 */
	public void studentInsert(StudentVO vo)
	{
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql="{CALL studentInsert(?,?,?,?)}";
			cs= conn.prepareCall(sql);
			
			cs.setString(1, vo.getName());
			cs.setInt(2, vo.getKor());
			cs.setInt(3, vo.getEng());
			cs.setInt(4, vo.getMath());
			cs.executeQuery();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null) conn.close();
			}catch(Exception ex) {}
		}
	}
	
	// 데이터 수정
	/*
		CREATE OR REPLACE PROCEDURE studentUpdate(
		    phakbun student.hakbun%TYPE,
		    pname student.name%TYPE,
		    pkor student.kor%TYPE,
		    peng student.eng%TYPE,
		    pmath student.math%TYPE
		)
		IS
		BEGIN
		    UPDATE student SET
		    name=pname,kor=pkor,eng=peng,math=pmath
		    WHERE hakbun=phakbun;
		    COMMIT;
		END;
	 */
	public void studentUpdate(StudentVO vo)
	{
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql="{CALL studentUpdate(?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			
			cs.setInt(1, vo.getHakbun());
			cs.setString(2, vo.getName());
			cs.setInt(3, vo.getKor());
			cs.setInt(4, vo.getEng());
			cs.setInt(5, vo.getMath());
			cs.executeQuery();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null) conn.close();
			}catch(Exception ex) {}
		}
	}
	
	// 데이터 삭제
	public void studentDelete(int hakbun)
	{
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql="{CALL studentDelete(?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, hakbun);
			cs.executeQuery();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null) conn.close();
			}catch(Exception ex) {}
		}
	}
	
	// 데이터 상세
	public StudentVO studentDetail(int hakbun)
	{
		StudentVO vo = new StudentVO();
		
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql="{CALL studentDetailData(?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, hakbun);
			// OUT이라고 설정 되었을 때
			cs.registerOutParameter(2, OracleTypes.VARCHAR);
			cs.registerOutParameter(3, OracleTypes.INTEGER);
			cs.registerOutParameter(4, OracleTypes.INTEGER);
			cs.registerOutParameter(5, OracleTypes.INTEGER);
			cs.executeQuery();
			
			vo.setHakbun(hakbun);
			vo.setName(cs.getString(2));
			vo.setKor(cs.getInt(3));
			vo.setEng(cs.getInt(4));
			vo.setMath(cs.getInt(5));
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null) conn.close();
			}catch(Exception ex) {}
		}
		return vo;
	}
	
	// 데이터 전체
	public List<StudentVO> studentListData()
	{
		List<StudentVO> list = new ArrayList<StudentVO>();
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
			String sql="{CALL studentListData(?)}";
			cs = conn.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.executeQuery();
			
			ResultSet rs = (ResultSet)cs.getObject(1); //커서는 ResultSet으로 변환
			while(rs.next())
			{
				StudentVO vo = new StudentVO();
				vo.setHakbun(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setKor(rs.getInt(3));
				vo.setEng(rs.getInt(4));
				vo.setMath(rs.getInt(5));
				vo.setTotal(rs.getInt(6));
				vo.setAvg(rs.getDouble(7));
				list.add(vo);
			}
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(cs!=null) cs.close();
				if(conn!=null) conn.close();
			}catch(Exception ex) {}
		}
		return list;
	}
}
