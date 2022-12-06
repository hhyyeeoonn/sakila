package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Film;

public class FilmDao {
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
