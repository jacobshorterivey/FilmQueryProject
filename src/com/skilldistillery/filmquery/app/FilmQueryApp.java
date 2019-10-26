package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//    app.test();
		app.launch();
	}

//  private void test() {
//    Film film = db.findFilmById(256);
//    System.out.println(film);
//  }

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		boolean flag = true;
		Film film;
		List<Film> films;
		System.out.println("Menu: ");

		do {
			System.out.println("1.) Look up a film by ID.");
			System.out.println("2.) Look up a film by a search keyword.");
			System.out.println("3.) Exit");
			int choice = input.nextInt();
			
			switch (choice) {
			case 1:
				System.out.print("Enter film ID: ");
				
				int filmId = input.nextInt();
				
				film = db.findFilmById(filmId);
				if (film == null) {
					System.out.println("Sorry, film not found.");
				} else {
					System.out.println(film);
				}
				break;
				
			case 2:
				System.out.print("Enter your search keyword: ");
				
				String keyword = input.next();
				
				films = db.findFilmByKeyword(keyword);
				if (films.size()==0) {
					System.out.println("Sorry, we didn't find a match.\n");
				} else {
					System.out.println(films);
				}
				break;
				
			case 3:
				System.out.println("Exiting...");
				flag = false;
				break;
				
			default:
				System.out.println("Invalid entry.");
				break;
			}
		} while (flag);

	}// end of startUserInterface

}
