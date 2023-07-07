package com.sist.dao;

import com.sist.common.CreateDataBase;
import com.sist.vo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class FreeBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FreeBoardDAO dao;
	
	public static FreeBoardDAO newInstance() {
		if(dao==null)
			dao = new FreeBoardDAO();
		return dao;
	}
	
	public List<FreeBoardVO> freeboardListData(int page) {
		List<FreeBoardVO> list=new ArrayList<FreeBoardVO>();
	      
	      try {
	         
	         conn=db.getConnection();
	         String sql="SELECT no, subject, name, TO_CHAR(regdate, 'yyyy-MM-dd'), hit, num "
	               + "FROM (SELECT no, subject, name, regdate, hit, rownum as num "
	               + "FROM (SELECT /*+ INDEX_DESC(project_freeboard pf_no_pk)*/ no, subject, name, regdate, hit "
	               + "FROM project_freeboard)) "
	               + "WHERE num BETWEEN ? AND ?";
	         ps=conn.prepareStatement(sql);
	         int rowSize=10;
	         int start=(rowSize*page)-(rowSize-1);
	         int end=rowSize*page;
	         ps.setInt(1, start);
	         ps.setInt(2, end);
	         
	         ResultSet rs=ps.executeQuery();
	         while(rs.next()) {
	            FreeBoardVO vo=new FreeBoardVO();
	            vo.setNo(rs.getInt(1));
	            vo.setSubject(rs.getString(2));
	            vo.setName(rs.getString(3));
	            vo.setDbday(rs.getString(4));
	            vo.setHit(rs.getInt(5));
	            vo.setRownum(rs.getInt(6));
	            list.add(vo);
	         }
	         rs.close();
	         
	      } catch(Exception ex) {
	         ex.printStackTrace();
	      } finally {
	         db.disConnection(conn, ps);
	      }
	      
	      return list;
	   }
	   
	   // 총페이지 구하기
	   public int freeboardTotalPage() {
	      
	      int total=0;
	      
	      try {
	         
	         conn=db.getConnection();
	         String sql="SELECT CEIL(COUNT(*)/10.0) FROM project_freeboard";
	         ps=conn.prepareStatement(sql);
	         
	         
	      } catch(Exception ex) {
	         ex.printStackTrace();
	      } finally {
	         db.disConnection(conn, ps);
	      }
	      
	      return total;
	   }
	   
	   
	   // top7 => rownum : hit 많은 순서대로 7개
	   
	   
	   // 글쓰기
	   public void freeboardInsert(FreeBoardVO vo) {
	      
	      try {
	         
	         conn=db.getConnection();
	         String sql="INSERT INTO project_freeboard VALUES(pf_no_seq.nextval,?,?,?,?,SYSDATE,0)";
	         ps=conn.prepareStatement(sql);
	         ps.setString(1, vo.getName());
	         ps.setString(2, vo.getSubject());      
	         ps.setString(3, vo.getContent());
	         ps.setString(4, vo.getPwd());   
	         ps.executeUpdate();
	         
	      } catch(Exception ex) {
	         ex.printStackTrace();
	      } finally {
	         db.disConnection(conn, ps);
	      }
	   }
	   
	   // 상세보기
	   public FreeBoardVO freeDetailData(int no) {
		   FreeBoardVO vo = new FreeBoardVO();
		   try {
			   conn = db.getConnection();
			   
			   String sql = "UPDATE project_freeboard SET hit=hit+1 WHERE no=?";
			   ps = conn.prepareStatement(sql);
			   ps.setInt(1, no);
			   ps.executeQuery();
			   
			   // 실제 데이터 읽기
			   sql = "SELECT no, subject, name, content, TO_CHAR(regdate, 'yyyy-MM-dd'), hit "
			   		+ "FROM project_freeboard "
			   		+ "WHERE no=?";
			   ps = conn.prepareStatement(sql);
			   ps.setInt(1, no);
			   
			   ResultSet rs = ps.executeQuery();
			   rs.next();
			   vo.setNo(rs.getInt(1));
			   vo.setSubject(rs.getString(2));
			   vo.setName(rs.getString(3));
			   vo.setContent(rs.getString(4));
			   vo.setDbday(rs.getString(5));
			   vo.setHit(rs.getInt(6));
			   rs.close();
			   
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }finally {
			   db.disConnection(conn, ps);
		   }
		   return vo;
	   }
	   
	   // 수정하기 => ajax
	   public FreeBoardVO freeUpdateData(int no) {
		   FreeBoardVO vo = new FreeBoardVO();
		   try {
			   conn = db.getConnection();
			   
			   String sql = "SELECT no, subject, name, content "
			   		+ "FROM project_freeboard "
			   		+ "WHERE no=?";
			   ps = conn.prepareStatement(sql);
			   ps.setInt(1, no);
			   
			   ResultSet rs = ps.executeQuery();
			   rs.next();
			   vo.setNo(rs.getInt(1));
			   vo.setSubject(rs.getString(2));
			   vo.setName(rs.getString(3));
			   vo.setContent(rs.getString(4));
			   rs.close();
			   
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }finally {
			   db.disConnection(conn, ps);
		   }
		   return vo;
	   }
	   
	   public boolean freeboardUpdate(FreeBoardVO vo) {
		   boolean bCheck = false;
		   
		   try {
			   conn = db.getConnection();
			   
			   String sql = "SELECT pwd FROM project_freeboard WHERE no=?";
			   ps = conn.prepareStatement(sql);
			   ps.setInt(1, vo.getNo());
			   ResultSet rs = ps.executeQuery();
			   
			   rs.next();
			   String db_pwd = rs.getString(1);
			   rs.close();
			   
			   if(db_pwd.equals(vo.getPwd())) {
				   bCheck = true;
				   sql = "UPDATE project_freeboard SET name=?, subject=?, content=? WHERE no=?";
				   ps = conn.prepareStatement(sql);
				   ps.setString(1, vo.getName());
				   ps.setString(2, vo.getSubject());
				   ps.setString(3, vo.getContent());
				   ps.setInt(4, vo.getNo());
				   ps.executeUpdate();
			   }
			   
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }finally {
			   db.disConnection(conn, ps);
		   }
		   
		   return bCheck;
	   }
	   
	   // 삭제하기 => ajax
	   public String freeboardDelete(int no, String pwd) {
		   String res = "NO";
		   
		   try {
			   conn = db.getConnection();
			   
			   String sql = "SELECT pwd FROM project_freeboard WHERE no=?";
			   ps = conn.prepareStatement(sql);
			   ps.setInt(1, no);
			   ResultSet rs = ps.executeQuery();
			   rs.next();
			   String db_pwd = rs.getString(1);
			   rs.close();
			   
			   if(db_pwd.equals(pwd)) {
				   res="YES";
				   sql = "DELETE FROM project_freeboard WHERE no=?";
				   ps = conn.prepareStatement(sql);
				   ps.setInt(1, no);
				   ps.executeUpdate();
			   }
			   
		   }catch(Exception ex) {
			   ex.printStackTrace();
		   }finally {
			   db.disConnection(conn, ps);
		   }
		   
		   return res;
	   }
	
}
