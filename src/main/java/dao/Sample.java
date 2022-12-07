package dao;

public class Sample {
	public ArrayList<Film> selectFilmList2(String col, String sort, String rentalRate, String releaseYear, String[] rating, int fromMinute, int toMinute, String searchTitle) {
		ArrayList<Film> list = new ArrayList<Film>();

		String sql = "SELECT * FROM film WHERE release_year like ? AND title like ? AND rental_rate LIKE ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila", "root", "java1234");
			if(fromMinute > toMinute) { // 상영시간 BETWEEN AND 필요
				if(rating == null || rating.length == 5) { // 등급 검색X
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					
				} else if(rating.length == 4) {
					sql += " AND rating IN(?, ?, ?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					stmt.setString(6, rating[0]);
					stmt.setString(7, rating[1]);
					stmt.setString(8, rating[2]);
					stmt.setString(9, rating[3]);
					
				} else if(rating.length == 3) {
					sql += " AND rating IN(?, ?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					stmt.setString(6, rating[0]);
					stmt.setString(7, rating[1]);
					stmt.setString(8, rating[2]);
					
				} else if(rating.length == 2) {
					sql += " AND rating IN(?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					stmt.setString(6, rating[0]);
					stmt.setString(7, rating[1]);
					
				} else if(rating.length == 1) {
					sql += " AND rating IN(?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setInt(4, fromMinute);
					stmt.setInt(5, toMinute);
					stmt.setString(6, rating[0]);
				}
				
			} else { // 상영시간 BETWEEN AND 필요X
				if(rating == null || rating.length == 5) { // 등급 검색X
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					
				} else if(rating.length == 4) {
					sql += " AND rating IN(?, ?, ?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setString(4, rating[0]);
					stmt.setString(5, rating[1]);
					stmt.setString(6, rating[2]);
					stmt.setString(7, rating[3]);
					
				} else if(rating.length == 3) {
					sql += " AND rating IN(?, ?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setString(4, rating[0]);
					stmt.setString(5, rating[1]);
					stmt.setString(6, rating[2]);
					
				} else if(rating.length == 2) {
					sql += " AND rating IN(?, ?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.setString(3, "%" + rentalRate + "%");
					stmt.setString(4, rating[0]);
					stmt.setString(5, rating[1]);
					
				} else if(rating.length == 1) {
					sql += " AND rating IN(?)";
					sql += "ORDER BY "+ col + " " + sort;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%" + releaseYear + "%");
					stmt.setString(2, "%" + searchTitle + "%");
					stmt.
					rs = stmt.executeQuery();
					while(rs.next()) {
						Film f = new Film();
						f.setFilmId(rs.getInt("film_id"));
						f.setTitle(rs.getString("title"));
						f.setRating(rs.getString("rating"));
						f.setLength(rs.getInt("length"));
						f.setReleaseYear(rs.getString("release_year"));
						f.setRentalRate(rs.getDouble("rental_rate"));
						list.add(f);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					
				} finally {
					try {
						rs.close();
						stmt.close();
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				return list;
			}
	
	
	
}
