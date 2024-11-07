package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.MovieDao;
import com.cinema.model.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MovieListingPage extends JFrame implements ActionListener {
    private AbstractDao<Movie> movieDao;
    private JTable movieTable;
    private JButton selectBtn;
    private DefaultTableModel tableModel;
    private String[] columns={"Id","Title","Duration"};
    private String[][] movieDataTable;
    private JFrame parentPage;
    private String flag;

    public MovieListingPage(JFrame parentPage,String flag){
        this.parentPage= parentPage;
        this.flag = flag;
        this.movieDao= new MovieDao();
        initializedTableComponent();
        initializedComponent();
        this.setVisible(true);
    }

    private void initializedComponent() {
        this.selectBtn = new JButton("Select Movie");
        this.setTitle("Movie List");
        this.setSize(500,400);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.selectBtn.addActionListener(this);
        this.add(this.movieTable,BorderLayout.CENTER);
        this.add(this.selectBtn,BorderLayout.SOUTH);

    }

    private void initializedTableComponent(){
        this.tableModel = new DefaultTableModel(null,this.columns);
        this.movieTable = new JTable(this.tableModel);
        this.prepareMovieData();
        this.loadMovieData();
    }

    private void loadMovieData(){
        for (String[] movieDataRow : this.movieDataTable) {
           this.tableModel.addRow(movieDataRow);
        }
    }

    private void prepareMovieData(){
        List<Movie> movies =this.movieDao.getAll();
        this.movieDataTable = new String[movies.size()][this.columns.length];
        int rowCount=0;
        for (Movie movie : movies) {
            this.movieDataTable[rowCount]= movie.toArray();
            rowCount++;

        }
    }
    private void addSelectBtnAction(){
        this.selectBtn.addActionListener(e -> selectBtnAction());
    }

    private void selectBtnAction(){
        int movieId =getSelectedMovieId();
        if(this.flag.equals("create")){
            CreateMovieSchedulePage page = (CreateMovieSchedulePage)  this.parentPage;
            page.refreshSelectMovie(movieId);
        } else if (this.flag.equals("edit")) {
            UpdateMovieScheduleForm page = (UpdateMovieScheduleForm) this.parentPage;
            page.refreshSelectMovie(movieId);
        }
        this.dispose();
       // System.out.println("Selected movie id :" + movieId);
    }

    private int getSelectedMovieId(){
        return Integer.parseInt(this.movieDataTable[this.getSelectedIndex()][0]);
    }

    private  int getSelectedIndex(){
        int selectedRow=this.movieTable.getSelectedRow();
        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this,"Please Select a Movie");
        }else{
            return selectedRow;
        }
        return -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.selectBtn){
            this.selectBtnAction();
        }

    }
}
