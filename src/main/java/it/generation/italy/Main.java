package it.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

	private final static String URL = "jdbc:mysql://localhost:3306/db-nations";
	private final static String USER = "root";
	private final static String PASSWORD = "barbottino81.";

	public static void main(String[] args) {

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

			System.out.println(con.isClosed());

			String sql = "select c.name as country_name, c.country_id, r.name as region_name, c2.name as continents_name from countries c \r\n"
					+ "join regions r on c.region_id = r.region_id \r\n"
					+ "join continents c2 on c2.continent_id = r.continent_id \r\n" + "order by c.name;";

			try (PreparedStatement ps = con.prepareStatement(sql)) {				
				

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						System.out.println(rs.getString(1) + ", " + rs.getInt(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + ".");
					}

				}
			}

		} catch (SQLException e) {
			System.out.println("ERRORE: " + e.getMessage());

		}
	}
}