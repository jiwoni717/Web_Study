package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sist.vo.*;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:xe";
	private static MemberDAO dao;
	
	public MemberDAO()
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
	
	public static MemberDAO newInstance() {
		if(dao==null)
			dao = new MemberDAO();
		return dao;
	}
	
	// 로그인 처리
	public MemberVO isLogin(String id, String pwd) {
		MemberVO vo = new MemberVO();
		
		try {
			getConnection();
			
			String sql="SELECT COUNT(*) FROM jspMember WHERE id=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			
			if(count==0)
			{
				// ID가 없는 상태
				vo.setMsg("NOID");
			}
			else {
				sql="SELECT id, name, sex, pwd FROM jspMember WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				rs = ps.executeQuery();
				
				rs.next();
				String db_id = rs.getString(1);
				String name = rs.getString(2);
				String sex = rs.getString(3);
				String db_pwd = rs.getString(4);
				rs.close();
				
				if(db_pwd.equals(pwd))
				{
					vo.setId(db_id);
					vo.setName(name);
					vo.setSex(sex);
					vo.setPwd(db_pwd);
					vo.setMsg("OK");
				}
				else {
					vo.setMsg("NOPWD");
				}
				
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
}
