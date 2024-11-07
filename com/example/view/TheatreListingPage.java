package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CinemaDaoImpl;
import com.cinema.dao.TheatreDaoImpl;
import com.cinema.model.Cinema;
import com.cinema.model.Theatre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TheatreListingPage extends JFrame implements ActionListener {

    private TheatreDaoImpl theatreDao;
    private JTable theatreTable;
    private DefaultTableModel tableModel;
    private String[][] theatreDataTable;
    private JButton selectBtn;
    private String[] columns ;
    private JFrame parentPage;
    private Cinema cinema;
    private String flag;

    public TheatreListingPage(JFrame parentPage , String flag){
        this.theatreDao= new TheatreDaoImpl();
        this.parentPage = parentPage;
        this.flag = flag;
        prepareSelectedCinema();
        this.setLayout(new BorderLayout());
        initializeBtnComponent();
        initializeTableComponent();
        this.setLocation(200,150);
        this.setTitle("Theatre Listing for "+ this.cinema.getName());
        this.setSize(500,400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private void initializeBtnComponent() {
        this.selectBtn = new JButton("Select Theatre");
        this.selectBtn.addActionListener(this);
        this.add(selectBtn,BorderLayout.SOUTH);
    }
    private void initializeTableComponent() {
        //column create
        this.columns = new String[] {"id"," Name"};
        //table create
        this.tableModel= new DefaultTableModel(null,this.columns);
        this.theatreTable = new JTable(this.tableModel);
        this.add(this.theatreTable,BorderLayout.CENTER);
        //Data adding
        this.loadTheatreDataTable();
        this.prepareTheatreData();
    }

    private void prepareTheatreData() {
        for (String[] theatreDataRow : this.theatreDataTable) {
            this.tableModel.addRow(theatreDataRow);

        }
    }

    private void loadTheatreDataTable() {
        List<Theatre> theatres = null;
        try {
            theatres = this.theatreDao.getTheatreByCinema(this.cinema.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.theatreDataTable = new String[theatres.size()][this.columns.length];
        int rowCount =0;
        for (Theatre theatre : theatres) {
            this.theatreDataTable[rowCount] = theatre.toArray();
            rowCount++ ;
        }
    }

    public int getSelectedTheatreId(){
        return Integer.parseInt(this.theatreDataTable[this.getSelectedRow()][0]);
    }

    public int getSelectedRow(){
        int selectedRow = this.theatreTable.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this,"Please select theatre");
            return -1;
        }
        return selectedRow;
    }
    private  void prepareSelectedCinema(){
        CreateMovieSchedulePage createMovieSchedulePage = (CreateMovieSchedulePage) this.parentPage;
        this.cinema= createMovieSchedulePage.getCinema();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.selectBtn){
            int theatreId = this.getSelectedTheatreId();
            if(flag.equals("create")) {
                CreateMovieSchedulePage page = (CreateMovieSchedulePage) this.parentPage;
                page.refreshSelectedTheatre(theatreId);
            } else if (flag.equals("edit")) {
                UpdateMovieScheduleForm page = (UpdateMovieScheduleForm) this.parentPage;
                page.refreshSelectedTheatre(theatreId);
            }
            this.dispose();
        }
    }
}
