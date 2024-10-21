package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CinemaDao;
import com.cinema.model.Cinema;
import com.cinema.model.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CinemaListingPage extends JFrame implements ActionListener {
    private AbstractDao<Cinema> cinemaDao;
    private JScrollPane scrollPane;
    private JTable cinemaTable;
    private DefaultTableModel tableModel;
    private String[][] cinemaDataTable;
    private JButton selectBtn;
    private String[] columns = {"id"," Name","Address"};
    private JFrame parentPage;

   public CinemaListingPage(JFrame parentPage){
       this.parentPage = parentPage;
        this.cinemaDao= new CinemaDao();
       this.setLayout(new BorderLayout());
       initializeBtnComponent();
       initializeTableComponent();
       this.setLocation(200,150);
       this.setTitle("Cinema Listing Page");
       this.setSize(500,400);
       this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       this.setVisible(true);
   }

    private void initializeTableComponent() {
       this.tableModel= new DefaultTableModel(null,this.columns);
      //  this.scrollPane = new JScrollPane();
       this.cinemaTable = new JTable(this.tableModel);
     //  this.scrollPane.add(this.cinemaTable);
       this.add(this.cinemaTable,BorderLayout.CENTER);
       this.loadCinemaDataTable();;
       this.prepareCinemaData();
    }

    public void initializeBtnComponent() {
        this.selectBtn = new JButton("Select Cinema");
        this.selectBtn.addActionListener(this);
        this.add(selectBtn,BorderLayout.SOUTH);
    }
    public void loadCinemaDataTable(){
        List<Cinema> cinemas = this.cinemaDao.getAll();
        this.cinemaDataTable = new String[cinemas.size()][this.columns.length];
        int rowCount = 0;
        for (Cinema cinema : cinemas) {
            this.cinemaDataTable[rowCount]= cinema.toArray();
            rowCount++;
        }
    }
    private void prepareCinemaData(){
        for (String[] cinemaDataRow : this.cinemaDataTable) {
            this.tableModel.addRow(cinemaDataRow);
            loadCinemaDataTable();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.selectBtn){
            int cinemaId = this.getSelectedCinemaId();
            CreateMovieSchedulePage page=(CreateMovieSchedulePage) this.parentPage;
            page.refreshSelectedCinema(cinemaId);
            this.dispose();
        }
    }
    public int getSelectedCinemaId(){
       return Integer.parseInt(this.cinemaDataTable[this.getSelectedRow()][0]);
    }
    public int getSelectedRow(){
       int selectedRow = this.cinemaTable.getSelectedRow();
       if(selectedRow == -1){
           JOptionPane.showMessageDialog(this,"Please select cinema");
           return -1;
       }
       return selectedRow;
    }
}
