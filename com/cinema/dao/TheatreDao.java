package com.cinema.dao;

import com.cinema.model.Seat;
import com.cinema.model.Theatre;

import java.sql.SQLException;
import java.util.List;

public abstract class TheatreDao extends AbstractDao<Theatre> {

    public abstract List<Theatre> getTheatreByCinema (int cinemaId)throws SQLException;

}
