package com.cinema.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Cinema;
import com.cinema.model.Seat;
import com.cinema.model.Theatre;

public class TheatreDao extends AbstractDao<Theatre> {
	
	private PgSqlConnectionFactory connectionFactory;
	private AbstractDao<Cinema> cinemaDao;
	private AbstractDao<Seat> seatDao;
	
	public TheatreDao() {
		this.connectionFactory = new PgSqlConnectionFactory();
		this.cinemaDao = new CinemaDao();

	}

	@Override
	public String getTableName() {
		return "theatres";
	}

	@Override
	public String getUpdateQuary() {
		return "";
	}

	@Override
	public void setUpdateParameter(PreparedStatement preparedStatement, Theatre entity) {

	}

	@Override
	public Theatre convertToObject(ResultSet resultSet) throws SQLException {
		
			Theatre theatre = new Theatre();
			theatre.setId(resultSet.getInt("id"));
			theatre.setName(resultSet.getString("name"));
			int cinema_id = resultSet.getInt("cinema_id");
			theatre.setCinema(this.cinemaDao.findbyId(cinema_id));
			this.seatDao = new SeatDaoImpl();
		//	theatre.setSeats(this.seatDao.getAllSeatByTheatre(theatre.getId()));
			return theatre;
	}

	@Override
	public String getInsertValues() {
		return "(name, cinema_id) values (?, ?)";
	}

	@Override
	public void setParameters(PreparedStatement preparedStatement, Theatre entity) throws SQLException {
		preparedStatement.setString(1, entity.getName());
		preparedStatement.setInt(2, entity.getCinema().getId());
	}
}
