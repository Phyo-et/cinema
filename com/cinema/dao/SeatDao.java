package com.cinema.dao;

import com.cinema.model.Seat;

import java.sql.SQLException;
import java.util.List;

 abstract class SeatDao extends AbstractDao<Seat> {

    public abstract List<Seat> getAllSeatByTheatre(int id)throws SQLException;
}
