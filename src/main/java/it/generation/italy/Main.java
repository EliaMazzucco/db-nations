package it.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	private final static String URL = "jdbc:mysql://localhost:3306/db-nations";
	private final static String USER = "root";
	private final static String PASSWORD = "barbottino81.";

	public static void main(String[] args) {

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Insert a key word for found the country: ");
			String filtro = scan.nextLine();

			String sql = "select c.name as country_name, c.country_id, r.name as region_name, c2.name as continents_name from countries c \r\n"
					+ "join regions r on c.region_id = r.region_id \r\n"
					+ "join continents c2 on c2.continent_id = r.continent_id \r\n" + "where c.name like ? \r\n"
					+ "order by c.name;\r\n" + "\r\n";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setString(1, "%" + filtro + "%");

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						System.out.println(rs.getString(1) + ", " + rs.getInt(2) + ", " + rs.getString(3) + ", "
								+ rs.getString(4) + ".");
					}

				}
			}

			System.out.println("Insert a ID for found the country: ");
			int id = scan.nextInt();

			sql = "select l.`language` from country_languages cl \r\n"
					+ "join languages l on l.language_id = cl.language_id \r\n" + "where country_id = ? ;";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setInt(1, id);

				try (ResultSet rs = ps.executeQuery()) {
					System.out.println("Language");
					while (rs.next()) {
						System.out.print(rs.getString(1) + " ");

					}System.out.println("");

				}
			}
			sql = "select cs.`year`, cs.population , cs.gdp \r\n" + "from country_stats cs\r\n"
					+ "where country_id = ? \r\n" + "order by year desc \r\n" + "limit 1;";

			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setInt(1, id);

				try (ResultSet rs = ps.executeQuery()) {
				
					while (rs.next()) {
						System.out.print("Year: ");
						System.out.println(rs.getInt(1));
						System.out.print("Population: ");
						System.out.println(rs.getInt(2));
						System.out.print("GDP: ");
						System.out.println(rs.getLong(3) + ".");

					}

				}

			}

		} catch (SQLException e) {
			System.out.println("ERRORE: " + e.getMessage());

		}
	}

}