package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.WordDTO;



public class WordDAO {

	private String username = "system";
	private String password = "11111111";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String driverName = "oracle.jdbc.driver.OracleDriver";
	private Connection conn = null;

	public static WordDAO wordDao = null;

	private WordDAO() {
		init();
	}

	private void init() {
		try {
			Class.forName(driverName);
			System.out.println("오라클 드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private boolean conn() {
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("커넥션 자원 획득 성공");
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	public void insert(WordDTO worddto) {
		if (conn()) {
			try {
				// 쿼리문 작성
				String sql = "insert into word values (word_seq.nextval,?,?)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				// Mapping
				psmt.setString(1, worddto.getEword());
				psmt.setString(2, worddto.getKword());

				int resultInt = psmt.executeUpdate();
				if (resultInt > 0) {
					conn.commit();
				} else {
					conn.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { // conn 자원 반납
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e2) {
				}
			}
		} else {
			System.out.println("데이터베이스 커넥션 실패");

		}

	}

	public ArrayList<WordDTO> selectAll() {
		ArrayList<WordDTO> ilist = new ArrayList<WordDTO>();
		if (conn()) {
			try {
				String sql = "select * from word";
				PreparedStatement psmt = conn.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery();
				// ResultSet은 테이블 형식으로 가져온다고 이해
				while (rs.next()) { // next() 메서드는 rs에서 참조하는 테이블에서
									// 튜플을 순차적으로 하나씩 접근하는 메서드
					WordDTO iTemp = new WordDTO();
					// rs.getString("id") >> rs가 접근한 튜플에서 id 컬럼의 값을 가져옴
					iTemp.setEword(rs.getString("eword"));
					iTemp.setKword(rs.getString("kword"));
					ilist.add(iTemp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally { // conn 자원 반납
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e2) {

				}
			}
		}
		return ilist;
	}

	public void delete(String eword) {
		if (conn()) {
			try {
				String sql = "delete from word where apple=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, eword);
				psmt.executeUpdate();
			} catch (Exception e) {
			} finally { // conn 자원 반납
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	public WordDTO selectOne(String eword) {
		if (conn()) {
			try {
				String sql = "select * from word where eword = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, eword);
				ResultSet rs = psmt.executeQuery();
				if (rs.next()) {
					WordDTO iTemp = new WordDTO();
					iTemp.setEword(rs.getString("eword"));
					iTemp.setKword(rs.getString("kword"));
					
					return iTemp;
				}
			} catch (Exception e) {
			} finally { // conn 자원 반납
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e2) {

				}
			}
		}
		return null;
	}

	public void update(WordDTO wdto) {
		if (conn()) {
			try {
				String sql = "update word set eword=?, kword=? where word=? ";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, wdto.getEword());
				psmt.setString(2, wdto.getKword());
				psmt.setString(3, wdto.getEword());
				psmt.executeUpdate();
			} catch (Exception e) {
			} finally {
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	public ArrayList<WordDTO> select(String searchW) {
		ArrayList<WordDTO> ilist = new ArrayList<WordDTO>();
		if (conn()) {
			try {
				String sql = "select * from word where " + "title like '%" + searchW + "%'";
				System.out.println(sql);
				PreparedStatement psmt = conn.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery();
				// Resultset은 테이블 형식으로 가져온다고 이해합니다.
				while (rs.next()) { // next()메서드는 rs에서 참조하는 테이블에서
									// 튜플을 순차적으로 하나씩 접근하는 메서드
					WordDTO wTemp = new WordDTO();
					wTemp.setEword(rs.getString("eword"));
					wTemp.setKword(rs.getString("kword"));
					ilist.add(wTemp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ilist;
	}
	
	

}
