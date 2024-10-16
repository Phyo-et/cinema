package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.ScheduleDao;
import com.cinema.dao.SeatDaoImpl;
import com.cinema.model.Cinema;
import com.cinema.model.Movie;
import com.cinema.model.Schedule;
import com.cinema.model.Seat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class SeatView {

    private JFrame frame;
    private JScrollPane scrollPane;
    private JTable seatsTable;
    private JButton bookingBtn;
    private AbstractDao<Schedule> scheduleDao;
    private SeatDaoImpl seatDao;
    private Schedule schedule;
    private String[] columns ={"id","name"};
    private Cinema cinema ;
    private Movie movie ;

    public SeatView(int scheduleId) throws SQLException {
        this.scheduleDao = new ScheduleDao();
        this.seatDao = new SeatDaoImpl();
        this.schedule = this.scheduleDao.findbyId(scheduleId);
        this.cinema = schedule.getThreatre().getCinema();
        this.movie = schedule.getMovie();
        initializeComponent();
    }

    public void initializeComponent() throws SQLException {
        this.bookingBtn = new JButton("Book Tickets");
        actionBookinBtn();

        this.seatsTable = new JTable(getSeatsData(),columns);
        this.scrollPane =new JScrollPane(seatsTable);

        this.frame = new JFrame("Available Seats - " +movie.getTitle()+ "("+cinema.getName()+")");
        this.frame.setSize(500,700);
        //this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());

        this.frame.setLocation(100,100);
        this.frame.add(scrollPane,BorderLayout.CENTER);
        this.frame.add(bookingBtn,BorderLayout.SOUTH);
        this.frame.setVisible(true);

    }

    public void actionBookinBtn(){
        this.bookingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Clicking Booking Btn !!!");
                int selectRow = seatsTable.getSelectedRow();
                if(selectRow != -1){
                    //System.out.println("Selected Row"+selectRow);
                    try {
                        int seatId = Integer.parseInt(getSeatsData()[selectRow][0]);
                        TicketInfoPage ticketInfoPAge = new TicketInfoPage(seatId,schedule.getId());
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(frame, "Please select a seat ");
                }
            }
        });
    }

    public String[][] getSeatsData() throws SQLException {

        List<Seat> seats = seatDao.getAllSeatByTheatre(schedule.getThreatre().getId());
       // System.out.println("Seats : "+seats);
        String[][] seatsData = new String[seats.size()][columns.length];
        int rowCount = 0;
        for(Seat seat: seats) {
            for(int i =0 ; i < columns.length; i++) {
                seatsData[rowCount][i] = seat.toArray()[i];
            }
            rowCount++;
        }

        return seatsData;
    }
}
