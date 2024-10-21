package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CinemaDao;
import com.cinema.dao.MovieDao;
import com.cinema.model.Cinema;
import com.cinema.model.Movie;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class CreateMovieSchedulePage extends JFrame implements ActionListener {

    private AbstractDao<Movie> movieDao;
    private AbstractDao<Cinema> cinemaDao;
    private JLabel movieLabel;
    private JLabel cinemaLabel;
    private JLabel theatreLabel;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JLabel publicDateLabel;

    private JLabel movieLink;
    private JLabel cinemaLink;
    private JLabel theatreLink;

    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextField publicDateField;

    private JButton resetBtn;
    private JButton createBtn;

    private Movie movie;
    private Cinema cinema;

    public CreateMovieSchedulePage(){
        this.movieDao =new MovieDao();
        this.cinemaDao = new CinemaDao();
        initializeComponent();

    }

    public String getSelectedMovieLabel(){
        if (this.movie == null){
            return "<html><a href = ''> SELECT Movie </html>";
        }else {
            return "<html><a href = ''> "+this.movie+"</html>";
        }

    }
    public void prepareMovieLabel(){
        this.movieLabel = new JLabel("Movie");
        this.movieLink =new JLabel(getSelectedMovieLabel());
        this.movieLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.movieLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               super.mouseClicked(e);
                System.out.println("Click select movie");
                openMovieListingPage();
            }
        });
    }

    private void openMovieListingPage() {
         new MovieListingPage(this);
    }

    public void prepareCinemaLabel(){
        this.cinemaLabel = new JLabel("Cinema");
        this.cinemaLink =new JLabel(this.getSelectedCinemaLabel());
        this.cinemaLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.cinemaLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openCinemaListingPage();
            }
        });
    }
    private void openCinemaListingPage() {
        new CinemaListingPage(this);
    }
    public void prepareTheatreLabel(){
        this.theatreLabel = new JLabel("Theatre");
        this.theatreLink =new JLabel("<html><a href = ''> SELECT Theatre </html>");
        this.theatreLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.theatreLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Click select theatre");
            }
        });
    }

    private void initializeComponent() {
        this.setTitle("Movie Schdule Register");

        this.setSize(500,700);
        this.setLayout(new GridLayout(7,2,10,10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        prepareMovieLabel();
        prepareCinemaLabel();
        prepareTheatreLabel();

        this.startTimeLabel = new JLabel("Start Time ");
        this.startTimeField =new JTextField();

        this.endTimeLabel = new JLabel("End Time ");
        this.endTimeField = new JTextField();

        this.publicDateLabel = new JLabel("Public Date ");
        this.publicDateField = new JTextField();

        this.createBtn = new JButton("Create");
        this.resetBtn = new JButton("Reset");


        addUIComponent();

        this.setVisible(true);
    }

    private void addUIComponent() {
        this.add(movieLabel);
        this.add(movieLink);

        this.add(cinemaLabel);
        this.add(cinemaLink);

        this.add(theatreLabel);
        this.add(theatreLink);

        this.add(startTimeLabel);
        this.add(startTimeField);

        this.add(endTimeLabel);
        this.add(endTimeField);

        this.add(publicDateLabel);
        this.add(publicDateField);

        this.add(createBtn);
        this.add(resetBtn);

    }

    public void refreshSelectMovie(int movieId){
        this.movie = this.movieDao.findbyId(movieId);
        this.movieLink.setText(this.getSelectedMovieLabel());
    }
    public void refreshSelectedCinema(int cinemaId){
        this.cinema = this.cinemaDao.findbyId(cinemaId);
        this.cinemaLink.setText(this.getSelectedCinemaLabel());
    }

    private String getSelectedCinemaLabel() {
        if (this.cinema == null){
            return "<html><a href = ''> SELECT Cinema </html>";
        }else {
            return "<html><a href = ''> "+this.cinema+"</html>";
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
