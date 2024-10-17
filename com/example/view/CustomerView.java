package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;
import com.cinema.model.Schedule;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class CustomerView {
    private JFrame frame;
    private JScrollPane scrollPane;
    private JTable customersTable;
    private JLabel nameLabel;
    private JLabel nameValueLabel;
    private JLabel emailLabel;
    private JLabel emailValueLabel;
    private JLabel addressLabel;
    private JLabel addressValueLable;
    private JButton createBtn;
    private JButton cancelBtn;
    private JPanel detailsPanel;
    private AbstractDao<Customer> customerDao;
    private String[] columns = {"id","Customer Name", "Customer Email","Customer Address"};
    private Customer customer;

    public CustomerView() {
        this.customerDao = new CustomerDao();
        this.customer = new Customer();

        this.intializeComponent();
    }

    private void addToPanel(){
        this.detailsPanel.add(this.nameLabel);
        this.detailsPanel.add(this.nameValueLabel);
        this.detailsPanel.add(this.emailLabel);
        this.detailsPanel.add(this.emailValueLabel);
        this.detailsPanel.add(this.addressLabel);
        this.detailsPanel.add(this.addressValueLable);


        this.detailsPanel.add(this.createBtn);
        this.detailsPanel.add(this.cancelBtn);

        this.frame.add(this.detailsPanel,BorderLayout.NORTH);


    }

    public void intializeComponent(){

        this.frame = new JFrame("Customers Data");
        this.frame.setSize(800,500);
        this.frame.setLayout(new BorderLayout());
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        System.out.println(this.getCustomersData().toString());

        this.customersTable= new JTable(this.getCustomersData(),this.columns);
        this.scrollPane = new JScrollPane(this.customersTable);

        this.detailsPanel = new JPanel();
        this.detailsPanel.setLayout(new GridLayout(4,2));

        this.nameLabel=new JLabel("Customer Name : ");
        this.nameValueLabel = new JLabel(this.customer.getName());

        this.emailLabel=new JLabel("Email Address : ");
        this.emailValueLabel = new JLabel(this.customer.getEmail());

        this.addressLabel=new JLabel("Address : ");
        this.addressValueLable = new JLabel(this.customer.getAddress());

        this.createBtn =new JButton("Customer Registeration Form");

        this.frame.add(createBtn,BorderLayout.SOUTH);
        //Method
        this.createBtn = new JButton("Create new Customer");
        this.cancelBtn = new JButton("Cancel");
        System.out.println("Customer is working");

    }

    private String[][] getCustomersData(){

        List<Customer> customers = this.customerDao.getAll();
        String[][] customersData = new String[customers.size()][columns.length];
        int rowCount = 0;
        for(Customer customer : customers) {
            for(int i =0 ; i < columns.length; i++) {
                customersData[rowCount][i] = customer.toArray()[i];
            }
            rowCount++;
        }
        return customersData;
    }
}
