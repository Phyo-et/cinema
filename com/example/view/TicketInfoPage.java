package com.example.view;

import com.cinema.dao.*;
import com.cinema.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicketInfoPage {
    private SeatDaoImpl seatDao;
    private AbstractDao<Customer> customerDao;
    private AbstractDao<Ticket> ticketDao;
    private AbstractDao<Schedule> scheduleDao;
    private JFrame frame;
    private JLabel cinemaLabel;
    private JLabel cinemaValueLabel;
    private JLabel movieLabel;
    private JLabel movieValueLabel;
    private JLabel theatreLabel;
    private JLabel theatreValueLabel;
//    private JLabel movieDurationLabel;
//    private JLabel movieDurationValueLabel;
    private JLabel priceLabel;
    private JLabel priceValueLabel;
    private JLabel publicDateLabel;
    private JLabel publicDateValueLabel;
    private JLabel startTimelabel;
    private JLabel startTimeValuelabel;
    private JLabel seatLabel;
    private JLabel seatValueLabel;
    private JLabel customerIdLabel;
    private JTextField customerIdTextField;
    private JButton saveBtn;
    private JButton cancelBtn;
    private JPanel detailsPanel;
    private Seat seat;
    private Schedule schedule;

    public TicketInfoPage(int seatId, int scheduleId){
        this.seatDao = new SeatDaoImpl();
        this.scheduleDao = new ScheduleDao();
        this.customerDao = new CustomerDao();
        this.ticketDao = new TicketDao();
        this.seat = this.seatDao.findbyId(seatId);
        this.schedule = this.scheduleDao.findbyId(scheduleId);

        initializeComponent();
        addToPanel();

    }

    private double priceCalculator(){
        if(this.seat.isNormal()){
            return 300;
        }else if(this.seat.isVIP()){
             return 500;
        }else{
              return 200;
        }
    }

    private void addToPanel(){
        this.detailsPanel.add(this.cinemaLabel);
        this.detailsPanel.add(this.cinemaValueLabel);
        this.detailsPanel.add(this.theatreLabel);
        this.detailsPanel.add(this.theatreValueLabel);
        this.detailsPanel.add(this.movieLabel);
        this.detailsPanel.add(this.movieValueLabel);
        this.detailsPanel.add(this.seatLabel);
        this.detailsPanel.add(this.seatValueLabel);
        this.detailsPanel.add(this.priceLabel);
        this.detailsPanel.add(this.priceValueLabel);
        this.detailsPanel.add(this.publicDateLabel);
        this.detailsPanel.add(this.publicDateValueLabel);
        this.detailsPanel.add(this.startTimelabel);
        this.detailsPanel.add(this.startTimeValuelabel);
        this.detailsPanel.add(this.customerIdLabel);
        this.detailsPanel.add(this.customerIdTextField);
        this.detailsPanel.add(this.saveBtn);
        this.detailsPanel.add(this.cancelBtn);

        this.frame.add(this.detailsPanel,BorderLayout.NORTH);

    }

    private void initializeComponent() {
        this.frame = new JFrame("Ticktet Iformation");
        this.frame.setSize(400,300);
        this.frame.setLayout(new BorderLayout());
        this.frame.setLocation(300,300);
        this.frame.setVisible(true);

        this.detailsPanel =new JPanel();
        this.detailsPanel.setLayout(new GridLayout(9,2));

        this.cinemaLabel = new JLabel("Cinema Name : ");
        this.cinemaValueLabel = new JLabel(this.schedule.getThreatre().getCinema().getName());

        this.theatreLabel = new JLabel("Theatre Name : ");
        this.theatreValueLabel = new JLabel(this.schedule.getThreatre().getName());

        this.movieLabel = new JLabel("Movie Title : ");
        Movie movie = this.schedule.getMovie();
        this.movieValueLabel = new JLabel(this.schedule.getMovie().getTitle()+" ( "+movie.getDuration()+" ) ");

        this.seatLabel = new JLabel("Seat : ");
        this.seatValueLabel = new JLabel(this.seat.getTitle()+" ( "+this.seat.getSeatType().toUpperCase()+" )");

        this.priceLabel = new JLabel("price : ");
        this.priceValueLabel = new JLabel(priceCalculator()+"");

        this.publicDateLabel = new JLabel("Public Date : ");
        this.publicDateValueLabel = new JLabel(this.schedule.getPublicDate()+"");

        this.startTimelabel = new JLabel("Start Time : ");
        this.startTimeValuelabel = new JLabel(this.schedule.getStartTime().toString());

        this.customerIdLabel = new JLabel("Enter Customer Id : ");
        this.customerIdTextField = new JTextField(15);

        this.saveBtn = new JButton("Save");
        this.cancelBtn = new JButton("Cancel");

        saveBtnAction();
    }

    private void saveBtnAction(){
        this.saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int customerId = Integer.parseInt(customerIdTextField.getText());
                Customer customer= customerDao.findbyId(customerId);
                if(customer != null){
                    Ticket ticket = new Ticket();
                    ticket.setCustomer(customer);
                    ticket.setSchedule(schedule);
                    ticket.setSeat(seat);
                    ticket.setPrice(priceCalculator());
                    ticketDao.create(ticket);
                    JOptionPane.showMessageDialog(frame,"Successfully Booked");
                }else{
                    JOptionPane.showMessageDialog(frame,"Customer Not Found for this Id : "+customerId);
                }
            }
        });
    }
}
