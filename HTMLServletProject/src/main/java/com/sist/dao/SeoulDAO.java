/*
		request : 사용자 요청값을 받는 경우
		response : 브라우저로 전송 → html, cookie
				   한 파일에서 1번만 수행이 가능하다
				   서버에서 화면 변경(sendRedirect())
		cookie : 브라우저에 정보 저장 (최근 방문한~...)
		session : 서버에 정보 저장 (예약, 장바구니...)
 */
package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.dbconn.*;

public class SeoulDAO {
	private String[] tables = {"seoul_location", "seoul_nature", "seoul_shop", "seoul_hotel"};
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static SeoulDAO dao;
	
	// 1. 목록 출력
	public List<SeoulVO> seoulListData(int page, int type)
	{
		List<SeoulVO> list = new ArrayList<SeoulVO>();
		
		try {
			db.getConnection();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return list;
	}
	
	// 2. 총페이지 구하기
	public int seoulTotalPage(int type)
	{
		int total = 0;
		
		try {
			conn = db.getConnection();
			String sql = "SELECT CEIL(COUNT(*)/12.0) FROM "+tables[type];
/*
			 컬럼명이나 FROM 뒤에는 ? 쓰면 안됨
			 ps.setString(1, tables[type]) → 자동으로 값에 ' '를 붙여줌
*/
			
			ps = conn.prepareStatement(sql);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return total;
	}
	
	// 3. 상세보기
	public SeoulVO seoulDetailData(int no, int type)
	{
		SeoulVO vo = new SeoulVO();
		
		try {
			db.getConnection();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			db.disConnection(conn, ps);
		}
		
		return vo;
	}
}
