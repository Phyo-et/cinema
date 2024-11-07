
package com.example.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.cinema.dao.AbstractDao;
import com.cinema.model.Schedule;
import com.cinema.dao.ScheduleDao;

public class BookingPage {

    private AbstractDao<Schedule> scheduleDao;
    private JFrame bookingframe;
    private JTable moviesTable;
    private JScrollPane scrollPane;
    private JButton bookingBtn;
    private JButton createSchduleBtn;
    private JButton editSchduleBtn;
    private JButton deleteSchduleBtn;

    private DefaultTableModel tableModel;

    private String[] columns = {"id","Movie Title", "Cinema Name", "Theatre Name", "Start Time", "End Time", "Public Date", "Duration"};

    public BookingPage() {
        System.out.println("calling constructor !!!!");
        this.scheduleDao = new ScheduleDao();
        this.initializeComponents();
    }

    private void initializeComponents() {
        this.bookingframe = new JFrame("Movie Booking");
        this.bookingframe.setSize(800, 500);
        this.bookingframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.bookingframe.setLayout(new BorderLayout());

        this.tableModel = new DefaultTableModel(null,this.columns);

        this.moviesTable = new JTable(this.tableModel);
        TableColumn column = this.moviesTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(200);

        this.scrollPane = new JScrollPane(this.moviesTable);

        this.bookingframe.add(this.scrollPane, BorderLayout.CENTER);

        this.loadDataToMovieSchduleListing();

        this.bookingBtn = new JButton("Select Movie & Book Seat");
        this.createSchduleBtn = new JButton("Create");
        this.editSchduleBtn = new JButton("Edit");
        this.deleteSchduleBtn = new JButton("Delete");
        JPanel btnPanel = new JPanel( );
        btnPanel.setLayout( new GridLayout(1,4));
        btnPanel.add(bookingBtn);
        btnPanel.add(createSchduleBtn);
        btnPanel.add(editSchduleBtn);
        btnPanel.add(deleteSchduleBtn);
        this.bookingframe.add(btnPanel, BorderLayout.SOUTH);

        selectMovieForBookingAction();

        addActionCreateBtn();
        addActionEditBtn();

        this.bookingframe.setLocationRelativeTo(null);
        this.bookingframe.setVisible(true);

    }

    public void loadDataToMovieSchduleListing(){
        for(String[] movieArr:getMoviesData()){
            this.tableModel.addRow(movieArr);
        }
    }

    private String[][] getMoviesData(){

        List<Schedule> schedules = this.scheduleDao.getAll();
        String[][] moviesData = new String[schedules.size()][columns.length];
        int rowCount = 0;
        for(Schedule schedule : schedules) {
            for(int i =0 ; i < columns.length; i++) {
                moviesData[rowCount][i] = schedule.toArray()[i];
            }
            rowCount++;
        }
        return moviesData;
    }

    private void addActionCreateBtn(){
        this.createSchduleBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCreateMovieSchdulePage();
                refreshMovieSchduleListingTable();
            }
        });
    }
    private void openCreateMovieSchdulePage(){
        new CreateMovieSchedulePage(this);
    }

    public void refreshMovieSchduleListingTable() {
        this.tableModel.setRowCount(0);
        this.loadDataToMovieSchduleListing();
    }
    private void addActionEditBtn(){
        this.editSchduleBtn.addActionListener(e -> editBtnAction());
    }

    private void editBtnAction() {
        int selectedRow = moviesTable.getSelectedRow();

        if (selectedRow != -1) {
            int scheduleId = Integer.parseInt(getMoviesData()[selectedRow][0]);
            new UpdateMovieScheduleForm(this,scheduleId);
        } else {
            JOptionPane.showMessageDialog(bookingframe, "Please Select a movie");
        }
    }

    private void selectMovieForBookingAction(){
        this.bookingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = moviesTable.getSelectedRow();
                System.out.println("Selected row :"+selectedRow);
                if (selectedRow != -1) {
                    int scheduleId = Integer.parseInt(getMoviesData()[selectedRow][0]);
                    try {
                        SeatView seatView = new SeatView(scheduleId);
                    } catch (SQLException e1) {
                        System.out.println(e1.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(bookingframe, "Please Select a movie");
                }
            }
        });
    }

}
