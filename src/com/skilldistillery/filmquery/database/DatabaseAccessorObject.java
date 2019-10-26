package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	// FIELDS
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// CONSTRUCTORS

	// METHODS
	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String user = "student";
		String password = "student";
		String sql = "SELECT id, title FROM film WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, user, password);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();

			if (filmResult.next()) {
				film = new Film();
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setActorsInFilm(findActorsByFilmId(filmId));

				filmResult.close();
				stmt.close();
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return film;
	}

	public Actor findActorById(int actorId) {
		Actor actor = null;
		String user = "student";
		String password = "student";
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";

		try {
			Connection conn = DriverManager.getConnection(URL, user, password);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();

			if (actorResult.next()) {
				actor = new Actor(); // Create the object
				// Here is our mapping of query columns to our object fields:
				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));

				actorResult.close();
				stmt.close();
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return actor;
	}

	public List<Actor> findActorsByFilmId(int filmId) {
		Actor actor = null;
		List<Actor> actors = new ArrayList<>();
		String user = "student";
		String password = "student";

		try {
			Connection conn = DriverManager.getConnection(URL, user, password);
			String sql = "SELECT id, first_name, last_name FROM actor JOIN film_actor ON actor.id = film_actor.actor_id " + " WHERE film_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				actor = new Actor(); // Create the object
				// Here is our mapping of query columns to our object fields:
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}
}
