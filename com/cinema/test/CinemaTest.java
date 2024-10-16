package com.cinema.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import com.cinema.service.CinemaService;
import com.cinema.service.CustomerService;
import com.cinema.service.MovieService;
import com.cinema.service.ScheduleService;
import com.cinema.service.SeatService;
import com.cinema.service.TheatreService;
import com.cinema.service.TicketService;

public class CinemaTest {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void actionMenu() {
		System.out.println("Choose an action:");
		System.out.println("1: Customer Actions ");
		System.out.println("2: Cinema Actions ");
		System.out.println("3: Theatre Actions ");
		System.out.println("4: Seat Actions ");
		System.out.println("5: Movie Actions ");
		System.out.println("6: Schedule Actions ");
		System.out.println("7: Ticket Actions ");
		System.out.println("8: Exit");
	}
	
	public static void main(String[] args) throws SQLException, NumberFormatException, IOException {

		boolean exit = true;
		do {
			actionMenu();
			int choice = Integer.parseInt(br.readLine());

			switch (choice) {
			case 1:
				callCustomerService();
				break;
			case 2:
				callCinemaService();
				break;
			case 3:
				callTheatreService();
				break;
			case 4:
				callSeatService();
				break;
			case 5:
				callMovieService();
				break;
			case 6:
				callScheduleService();
				break;
			case 7:
				callTicketService();
				break;
			case 8:
				exit = false;
				System.out.println("Exiting the application...");
				break;
			}
		} while (exit);

	}

	private static void callTicketService() throws NumberFormatException, IOException, SQLException {
		TicketService ticketService = new TicketService();
		ticketService.call();
	}

	private static void callScheduleService() throws NumberFormatException, IOException, SQLException {
		ScheduleService scheduleService = new ScheduleService();
		scheduleService.call();
		
	}

	private static void callMovieService() throws NumberFormatException, IOException, SQLException {
		MovieService movieService = new MovieService();
		movieService.call();
	}

	private static void callSeatService() throws NumberFormatException, IOException, SQLException {
		SeatService seatService = new SeatService();
		seatService.call();
		
	}

	private static void callTheatreService() throws NumberFormatException, IOException, SQLException {
		TheatreService theatreService = new TheatreService();
		theatreService.call();
	}

	private static void callCinemaService() throws NumberFormatException, IOException, SQLException {
		CinemaService cinemaService = new CinemaService();
		cinemaService.call();
		
	}

	private static void callCustomerService() throws NumberFormatException, IOException, SQLException {
		CustomerService customerService = new CustomerService();
		customerService.call();
	}

}
