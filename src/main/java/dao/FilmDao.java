package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Film;

public class FilmDao {
	
	//release_year의 최소값
	public int selectMinReleaseYear() {
		int minYear = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
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
		System.out.println("FilmDao:"+minYear);
		return minYear;
	}
		
	// rating : String[] 여러개의 등급
	public ArrayList<Film> selectFilmList2(int releaseYear, String searchTitle, String[] rating, int fromMinute, int toMinute) {
		ArrayList<Film> list = new ArrayList<Film>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			String sql = "";
			
			if(releaseYear != 0) { // 출시 연도:O
				if(toMinute > fromMinute) { // 출시 연도:O 상영등급:O 상영시간:O between ... and .... 필요
					if(rating == null || rating.length == 5) {
						sql = "SELECT * FROM film WHERE title like ? AND"
								+ " length BETWEEN ? AND ? AND"
								+ " release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setInt(2, fromMinute);
						stmt.setInt(3, toMinute);
						stmt.setInt(4, releaseYear);
					} else if(rating.length == 4) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?, ?, ?)"
								+ " AND length BETWEEN ? AND ?"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setString(5, rating[3]);
						stmt.setInt(6, fromMinute);
						stmt.setInt(7, toMinute);
						stmt.setInt(8, releaseYear);
					} else if(rating.length == 3) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?, ?)"
								+ " AND length BETWEEN ? AND ?"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setInt(5, fromMinute);
						stmt.setInt(6, toMinute);
						stmt.setInt(7, releaseYear);
					} else if(rating.length == 2) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?)"
								+ " AND length BETWEEN ? AND ?"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setInt(4, fromMinute);
						stmt.setInt(5, toMinute);
						stmt.setInt(6, releaseYear);
					} else if(rating.length == 1) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?)"
								+ " AND length BETWEEN ? AND ?"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setInt(3, fromMinute);
						stmt.setInt(4, toMinute);
						stmt.setInt(5, releaseYear);
					} 
				} else { // 출시 연도:O 상영등급:O 상영시간:X // between ... and .... 불필요
					if(rating == null || rating.length == 5) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setInt(2, releaseYear);
					} else if(rating.length == 4) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?, ?, ?)"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setString(5, rating[3]);
						stmt.setInt(6, releaseYear);
					} else if(rating.length == 3) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?, ?)"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setInt(5, releaseYear);
					} else if(rating.length == 2) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?)"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setInt(4, releaseYear);
					} else if(rating.length == 1) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?)"
								+ " AND release_year = ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setInt(3, releaseYear);
					} 
				}
			} else { // 출시 연도:X
				if(toMinute > fromMinute) { // 출시 연도:X 상영등급:X 상영시간:O // between ... and .... 필요
					if(rating == null || rating.length == 5) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setInt(2, fromMinute);
						stmt.setInt(3, toMinute);
					} else if(rating.length == 4) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?, ?, ?)"
								+ " AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setString(5, rating[3]);
						stmt.setInt(6, fromMinute);
						stmt.setInt(7, toMinute);
					} else if(rating.length == 3) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?, ?)"
								+ " AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setInt(5, fromMinute);
						stmt.setInt(6, toMinute);
					} else if(rating.length == 2) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?)"
								+ " AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setInt(4, fromMinute);
						stmt.setInt(5, toMinute);
					} else if(rating.length == 1) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?)"
								+ " AND length BETWEEN ? AND ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setInt(3, fromMinute);
						stmt.setInt(4, toMinute);
					} 
				} else { // 출시 연도:X 상영등급:O 상영시간:O between ... and .... 불필요
					if(rating == null || rating.length == 5) {
						sql = "SELECT * FROM film WHERE title like ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
					} else if(rating.length == 4) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?, ?, ?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
						stmt.setString(5, rating[3]);
					} else if(rating.length == 3) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?, ?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
						stmt.setString(4, rating[2]);
					} else if(rating.length == 2) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?, ?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
						stmt.setString(3, rating[1]);
					} else if(rating.length == 1) {
						sql = "SELECT * FROM film WHERE title like ?"
								+ " AND rating IN (?)";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+searchTitle+"%");
						stmt.setString(2, rating[0]);
					} 
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
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
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
			System.out.println("FilmDao:"+rs);
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
