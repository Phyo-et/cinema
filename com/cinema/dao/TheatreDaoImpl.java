package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Seat;
import com.cinema.model.Theatre;

public class TheatreDaoImpl extends TheatreDao {
	
	private PgSqlConnectionFactory connectionFactory;
	private CinemaDaoImpl cinemaDao;
	private AbstractDao<Seat> seatDao;
	
	public TheatreDaoImpl() {
		this.connectionFactory = new PgSqlConnectionFactory();
		this.cinemaDao = new CinemaDaoImpl();

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

	@Override
	public List<Theatre> getTheatreByCinema(int cinemaId) throws SQLException {
		String query = "SELECT * FROM theatres WHERE cinema_id = ?";
		List<Theatre> objects = new ArrayList<>();
		try {
			Connection connection = this.connectionFactory.createConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, cinemaId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Theatre theatre = this.convertToObject(resultSet);
				objects.add(theatre);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.connectionFactory.closeConnection();
		}


		return objects;

	}
}
