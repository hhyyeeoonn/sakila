package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Film;

public class FilmDao2 {
	
	//release_year의 최소값
	public int selectMinReleaseYear() {
		int minYear = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Class.forName("ord.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mariadb://lacalhost:3306/sakila", "root", "java1234");
				String sql = "SELECT MIN(release_year) y FROM film";
		        stmt = conn.prepareStatement(sql);
		        rs = stmt.executeQuery();
		        if(rs.next()) {
		        	minYear = rs.getInt("y"); // rs.getInt(1)
		        }
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
					stmt.close();
					rs.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return minYear;
	}
	
	
	
	public ArrayList<Film> filmSearchList(int fromMinute, int toMinute, String[] rating, String searchTitle) {
		ArrayList<Film> list = new ArrayList<Film>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String word1 = "";
		String word2 = ""; // title like ?
		String word3 = ""; // rating IN(?)
		String where = " WHERE";
		String and = " AND";
		if(fromMinute != 0 && toMinute != 0) {
			word1 = " length between " + fromMinute + " AND " + toMinute;
			
		} 
		if(searchTitle != null) {
			word2 = " title like ?";
		}
		if(rating.length == 4) {
			word3 = " rating IN (?, ?, ?, ?))";
		} else if(rating.length == 3) {
			word3 = " rating IN (?, ?, ?))";
		} else if(rating.length == 2) {
			word3 = " rating IN (?, ?))";
		} else if(rating.length == 1) {
			word3 = " rating IN (?))";
		}
			System.out.println("FilmDao:"+word1+word2+word3);
		
		try { 
			Class.forName("ord.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://lacalhost:3306/sakila", "root", "java1234");
			
			if(word1.equals("") && word2.equals("") && word3.equals("")) {
				sql = "SELECT * FROM film ORDER BY film_no";
			} else if(word1.equals("") && word2.equals("")) {
				sql = "SELECT * FROM film"+where+word3+" ORDER BY film_no";
			} else if(word1.equals("") && word3.equals("")) {
				sql = "SELECT * FROM film"+where+word2+" ORDER BY film_no";
			} else if(word2.equals("") && word3.equals("")) {
				sql = "SELECT * FROM film"+where+word1+" ORDER BY film_no";
			} else {
				// WHERE 1>0 WHERE가 참이면 다 출력해라
				sql = "SELECT * FROM film"+where+word1+and+word2+and+word3+" ORDER BY film_no"; 
			}
			stmt = conn.prepareStatement(sql);
			if(word1.equals("")) {
				
			}
			rs = stmt.executeQuery();
			while(rs.next()) {
				Film f = new Film();
				f.setFilmId(rs.getInt("film_id"));
				f.setTitle(rs.getString("title"));
				f.setRating(rs.getString("rating"));
				f.setLength(rs.getInt("length"));
				list.add(f);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	
	//  rating : String[] 여러개의 등급
	public ArrayList<Film> selectFilmList2(String[] rating) {
		ArrayList<Film> list = new ArrayList<Film>();
		String sql = "";
		if(rating == null || rating.length == 5) {
			sql = "SELECT * FROM film";
		} else if(rating.length == 4) {
			sql = "SELECT * FROM film WHERE rating IN (?, ?, ?, ?)";
		} else if(rating.length == 3) {
			sql = "SELECT * FROM film WHERE rating IN (?, ?, ?)";
		} else if(rating.length == 2) {
			sql = "SELECT * FROM film WHERE rating IN (?, ?)";
		} else if(rating.length == 1) {
			sql = "SELECT * FROM film WHERE rating IN (?)";
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			stmt= conn.prepareStatement(sql);
			if(rating != null) {
				for(int i = 0; i < rating.length; i += 1) {
					stmt.setString(i + 1, rating[i]);
				}
			}
			rs = stmt.executeQuery();
			while(rs.next()) {
				Film f = new Film();
				f.setFilmId(rs.getInt("film_id"));
				f.setTitle(rs.getString("title"));
				f.setRating(rs.getString("rating"));
				f.setLength(rs.getInt("length"));
				list.add(f);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		/*
		rating == null || rating.length == 5 
		SELECT * FROM film 
		
		rating.length == 4
		SELECT * FROM film WHERE rating IN (?, ?, ?, ?)
		 */
		
		return list;
	}
	
	
	
	// sort : asc /desc | col : 컬럼명
	public ArrayList<Film> selectFilmListBySearch(String col, String sort, String searchWord, String searchCol) {
		ArrayList<Film> list = new ArrayList<Film>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			String sql = "";
			
			if(searchCol == null || searchWord == null) { // 검색버튼 조회가 아닌경우
				System.out.println("FilmDao:검색X");
				sql = "SELECT film_id filmId, title, description, release_year releaseYear, language_id languageId, original_language_id OriginalLanguageId, rental_duration rentalDuration, rental_rate rentalRate, length, replacement_cost replacementCost, rating, special_features specialFeatures, last_update lastUpdate"
					+ "	FROM film"
					+ "	ORDER BY "+col+" "+sort;
				stmt = conn.prepareStatement(sql);
			} else {
				System.out.println("FilmDao:검색O");
				String whereCol = "";
				if(searchCol.equals("titleAndDescription")) {
					whereCol = "CONCAT(title,' ',description)";
				} else {
					whereCol = searchCol;
				}
				sql = "SELECT film_id filmId, title, description, release_year releaseYear, language_id languageId, original_language_id OriginalLanguageId, rental_duration rentalDuration, rental_rate rentalRate, length, replacement_cost replacementCost, rating, special_features specialFeatures, last_update lastUpdate"
						+ "	FROM film"
						+ " WHERE "+whereCol+" LIKE ?"
						+ "	ORDER BY "+col+" "+sort;
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+searchWord+"%");
			}
			
			rs = stmt.executeQuery();
			while(rs.next()) { // 데이터 많으니까 if xxx
				Film film = new Film();
				film.setFilmId(rs.getInt("filmId"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getString("releaseYear"));
				film.setLanguageId(rs.getInt("languageId"));
				film.setOriginalLanguageId(rs.getInt("originalLanguageId"));
				film.setRentalDuration(rs.getInt("rentalDuration"));
				film.setRentalRate(rs.getDouble("rentalRate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacementCost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("specialFeatures"));
				film.setLastUpdate(rs.getString("lastUpdate"));
				list.add(film);
			}
		} catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	        	 rs.close();
	        	 conn.close();
	        	 stmt.close();
	         } catch(Exception e) {
		            e.printStackTrace();
		         }
	      }
		return list;
	}
	
	public ArrayList<Film> selectFilmListBySearch2(String col, String sort, String searchWord) {
		ArrayList<Film> list = new ArrayList<Film>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String text = null;
		if(searchWord == null || searchWord.equals("")) {
			text =" ";
		} else {
			text = " where title like ? AND description like ? ";
		}
		String sql = "SELECT film_id filmId,"
				+ " title, description,"
				+ " release_year releaseYear,"
				+ " language_id languageId,"
				+ " original_language_id originalLanguageId,"
				+ " rental_duration rentalDuration,"
				+ " rental_rate rentalRate,"
				+ " length,"
				+ " replacement_cost replacementCost,"
				+ " rating, special_features specialFeatures,"
				+ " last_update lastUpdate"
				+ " FROM film"+ text
				+ "ORDER BY " + col + " " + sort;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			stmt = conn.prepareStatement(sql); // ?에는 값만 올 수 있다
			
			stmt.setString(1, "%" + searchWord + "%");
			stmt.setString(2, "%" + searchWord + "%");
			
			rs = stmt.executeQuery();
			while(rs.next()) { // 데이터 많으니까 if xxx
				Film film = new Film();
				film.setFilmId(rs.getInt("filmId"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getString("releaseYear"));
				film.setLanguageId(rs.getInt("languageId"));
				film.setOriginalLanguageId(rs.getInt("originalLanguageId"));
				film.setRentalDuration(rs.getInt("rentalDuration"));
				film.setRentalRate(rs.getDouble("rentalRate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacementCost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("specialFeatures"));
				film.setLastUpdate(rs.getString("lastUpdate"));
				list.add(film);
			}
		} catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	        	 rs.close();
	        	 conn.close();
	        	 stmt.close();
	         } catch(Exception e) {
		            e.printStackTrace();
		         }
	      }
		return list;
	}
	
	public ArrayList<Film> selectFilmListBySearch3(String col, String sort, String searchCol, String searchWord) {
		ArrayList<Film> list = new ArrayList<Film>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String text = null;
		if(searchWord == null || searchWord.equals("")) {
			text =" ";
		} else {
			text = " where " +searchCol+ "like ? ";
		}
		String sql = "SELECT film_id filmId,"
				+ " title, description,"
				+ " release_year releaseYear,"
				+ " language_id languageId,"
				+ " original_language_id originalLanguageId,"
				+ " rental_duration rentalDuration,"
				+ " rental_rate rentalRate,"
				+ " length,"
				+ " replacement_cost replacementCost,"
				+ " rating, special_features specialFeatures,"
				+ " last_update lastUpdate"
				+ " FROM film"+text
				+ "ORDER BY " + col + " " + sort;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			stmt = conn.prepareStatement(sql); // ?에는 값만 올 수 있다
			
			stmt.setString(1, "%" + searchWord + "%");
			
			rs = stmt.executeQuery();
			while(rs.next()) { // 데이터 많으니까 if xxx
				Film film = new Film();
				film.setFilmId(rs.getInt("filmId"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getString("releaseYear"));
				film.setLanguageId(rs.getInt("languageId"));
				film.setOriginalLanguageId(rs.getInt("originalLanguageId"));
				film.setRentalDuration(rs.getInt("rentalDuration"));
				film.setRentalRate(rs.getDouble("rentalRate"));
				film.setLength(rs.getInt("length"));
				film.setReplacementCost(rs.getDouble("replacementCost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("specialFeatures"));
				film.setLastUpdate(rs.getString("lastUpdate"));
				list.add(film);
			}
		} catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	        	 rs.close();
	        	 conn.close();
	        	 stmt.close();
	         } catch(Exception e) {
		            e.printStackTrace();
		         }
	      }
		return list;
	}
	
	
	
}
