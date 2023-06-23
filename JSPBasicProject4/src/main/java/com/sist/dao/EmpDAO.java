/*
		1. 사용자
		  - 등록
		  - 정보보기
		  - 수정
		  - 삭제
		  - 목록
		2. 관리자
		  - 부서별 통계
		  - 부서별 순위(급여 순위)
 */

package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.dbconn.*;

public class EmpDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	
	// 1. 등록
	public void empInsert(EmpVO vo)
	{
		try {
			conn = db.getConnection();
			String sql = "INSERT INTO myEmp VALUES("
					+ "(SELECT NVL(MAX(empno)+1, 7000) FROM myEmp),"
					+ "?,?,?,SYSDATE,?,?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, vo.getEname());
			ps.setString(2, vo.getJob());
			ps.setInt(3, vo.getMgr());
			ps.setInt(4, vo.getSal());
			ps.setInt(5, vo.getComm());
			ps.setInt(6, vo.getDeptno());
			
			ps.executeUpdate();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
	}
	
	public List<Integer> empGetMgrData()
	{
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT DISTINCT mgr FROM myEmp";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				list.add(rs.getInt(1));
			}
			
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return list;
	}
	
	public List<Integer> empGetSalData()
	{
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT DISTINCT sal FROM myEmp";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				list.add(rs.getInt(1));
			}
			
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return list;
	}
	
	public List<Integer> empGetDeptnoData()
	{
		List<Integer> list = new ArrayList<Integer>();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT DISTINCT deptno FROM myEmp";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				list.add(rs.getInt(1));
			}
			
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return list;
	}
	
	public List<String> empGetJobData()
	{
		List<String> list = new ArrayList<String>();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT DISTINCT job FROM myEmp";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				list.add(rs.getString(1));
			}
			
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return list;
	}
	
	public List<EmpVO> empListData()
	{
		List<EmpVO> list = new ArrayList<EmpVO>();
		
		try {
			conn = db.getConnection();
			
			// 1. 오라클 조인
			String sql = "SELECT empno, ename, job, TO_CHAR(hiredate,'YYYY-MM-DD'), TO_CHAR(sal,'L999,999'), NVL(comm,0), dname, loc, grade "
					+ "FROM myEmp me JOIN myDept md "
					+ "ON me.deptno=md.deptno "
					+ "JOIN myGrade ms "
					+ "ON me.sal BETWEEN ms.losal AND ms.hisal "
					+ "ORDER BY empno DESC";
			
			String sql2 = "SELECT empno, ename, job, TO_CHAR(hiredate, 'YYYY-MM-DD'), TO_CHAR(sal,'L999,999'), NVL(comm, 0), dname, loc, grade "
					+ "FROM myEmp me, myDept md, myGrade mg "
					+ "WHERE me.deptno=md.deptno "
					+ "AND me.sal BETWEEN mg.losal AND mg.hisal "
					+ "ORDER BY empno DESC";
			
			
			ps = conn.prepareStatement(sql2);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				EmpVO vo = new EmpVO();
				
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setDbsal(rs.getString(5));
				vo.setComm(rs.getInt(6));
				vo.getDvo().setDname(rs.getString(7));
				vo.getDvo().setLoc(rs.getString(8));
				vo.getSvo().setGrade(rs.getInt(9));
				list.add(vo);
			}
			rs.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return list;
	}
	
	public EmpVO empDetailData(int empno)
	{
		EmpVO vo = new EmpVO();
		
		try {
			conn = db.getConnection();
			
			// 2. 스칼라 서브쿼리
			String sql="SELECT empno, ename, job, NVL(mgr,0), TO_CHAR(hiredate,'YYYY-MM-DD'), TO_CHAR(sal,'L999,999'), NVL(comm,0), dname, loc, grade "
					+ "FROM myEmp me, myDept md, myGrade mg "
					+ "WHERE me.deptno=md.deptno "
					+ "AND sal BETWEEN mg.losal AND mg.hisal "
					+ "AND empno=?";
			
			String sql2 = "SELECT empno, ename, job, NVL(mgr,0), TO_CHAR(hiredate,'YYYY-MM-DD'), TO_CHAR(sal,'L999,999'), NVL(comm,0),"
					+ "(SELECT dname FROM myDept WHERE deptno=myEmp.deptno), (SELECT loc FROM myDept WHERE deptno=myEmp.deptno),"
					+ "(SELECT grade FROM myGrade WHERE myEmp.sal BETWEEN losal AND hisal) "
					+ "FROM myEmp "
					+ "WHERE empno=?";
			
			ps = conn.prepareStatement(sql2);
			ps.setInt(1, empno);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setMgr(rs.getInt(4));
			vo.setDbday(rs.getString(5));
			vo.setDbsal(rs.getString(6));
			vo.setComm(rs.getInt(7));
			vo.getDvo().setDname(rs.getString(8));
			vo.getDvo().setLoc(rs.getString(9));
			vo.getSvo().setGrade(rs.getInt(10));
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return vo;
	}
	
	// 수정 데이터 읽기
	public EmpVO empUpdateData(int empno)
	{
		EmpVO vo = new EmpVO();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT empno, ename, job, mgr, sal, comm, deptno "
					+ "FROM myEmp "
					+ "WHERE empno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, empno);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			vo.setEmpno(rs.getInt(1));
			vo.setEname(rs.getString(2));
			vo.setJob(rs.getString(3));
			vo.setMgr(rs.getInt(4));
			vo.setSal(rs.getInt(5));
			vo.setComm(rs.getInt(6));
			vo.setDeptno(rs.getInt(7));
			rs.close();
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			db.disConnection(null, ps);
		}
		
		return vo;
	}
}
