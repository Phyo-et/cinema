package com.cinema.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Cinema;

public class CinemaDaoImpl extends CinemaDao{
	
	private PgSqlConnectionFactory connectionFactory;
	
	public CinemaDaoImpl() {
		this.connectionFactory = new PgSqlConnectionFactory();
	}

	@Override
	public String getTableName() {
		return "cinemas";
	}

	@Override
	public String getUpdateQuary() {
		return "";
	}

	@Override
	public void setUpdateParameter(PreparedStatement preparedStatement, Cinema entity) {

	}

	@Override
	public Cinema convertToObject(ResultSet resultSet) throws SQLException {
		Cinema cinema = new Cinema();
		
			cinema.setId(resultSet.getInt("id"));
			cinema.setName(resultSet.getString("name"));
			cinema.setAddress(resultSet.getString("address"));
			return cinema;
	}

	@Override
	public String getInsertValues() {
		return "(name, address) values (?, ?)";
	}

	@Override
	public void setParameters(PreparedStatement preparedStatement, Cinema entity) throws SQLException {
		preparedStatement.setString(1, entity.getName());
		preparedStatement.setString(2, entity.getAddress());
	}

}
