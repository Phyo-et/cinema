package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.StringConcatFactory;
import java.util.List;

public class CustomerListingView {

    private AbstractDao<Customer> customerDao;
    private JFrame customerListingframe;
    private JTable customerTable;
    private JScrollPane scrollPane;

    private JButton createBtn;
    private JButton updateBtn;
    private JButton deleteBtn;

    private JPanel btnPanel;
    private String[] columns ={"Id","Name","Email","Address"};
    private DefaultTableModel tableModel;

    public CustomerListingView(){
        this.customerDao = new CustomerDao();
        initializeComponents();
        loadCustomersData();

    }

    private void initializeComponents() {
        this.customerListingframe = new JFrame("Customer Listing");
        this.customerListingframe.setSize(700,500);
        this.customerListingframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.customerListingframe.setLayout(new BorderLayout());

        this.tableModel =new DefaultTableModel(null, this.columns);
        this.customerTable = new JTable(this.tableModel);

        TableColumn column =this.customerTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(100);

        this.scrollPane = new JScrollPane(this.customerTable);
        this.customerListingframe.add(this.scrollPane, BorderLayout.CENTER);

        this.createBtn = new JButton("New Customer");
        this.updateBtn = new JButton("Edit");
        this.deleteBtn = new JButton("Delete");

        this.btnPanel = new JPanel();
        this.btnPanel.setLayout(new GridLayout(1,3));
        this.btnPanel.add(createBtn);
        this.btnPanel.add(updateBtn);
        this.btnPanel.add(deleteBtn);

        addCreateBtnAction();
        addEditBtnAction();

        customerListingframe.add(btnPanel,BorderLayout.SOUTH);
        customerListingframe.setVisible(true);
    }

    private void addCreateBtnAction(){
        this.createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBtnAction();
            }
        });
    }
    private void addEditBtnAction(){
        this.updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editBtnAction();
            }
        });
    }
    private void createBtnAction(){
        CustomerCreatePage customerCreatePage = new CustomerCreatePage(this);
    }

    private void editBtnAction() {
        int selectedRowIndex = this.customerTable.getSelectedRow();
        if (selectedRowIndex >= 0) {
            int customerId = Integer.parseInt(this.tableModel.getValueAt(selectedRowIndex,0).toString());
            UpdateCustomerView updateCustomerView = new UpdateCustomerView(this, customerId);
        }else{
            JOptionPane.showMessageDialog(null, "Please select a customer to update");
        }
    }

//    private String [][] getCustomerData(){
//
//        List<Customer> customers = this.customerDao.getAll();
//        String[][] customerDataTable = new String[customers.size()][this.columns.length];
//        int rowCount =0 ;
//        for(Customer customer :customers){
//            customerDataTable[rowCount]= customer.toArray();
//            rowCount++;
//        }
//        return customerDataTable;
//    }
    public void refreshCustomerTable(){
        this.tableModel.setRowCount(0);
        loadCustomersData();
    }

    private void loadCustomersData() {
        List<Customer> customers = this.customerDao.getAll();
        for(Customer customer: customers){
            this.tableModel.addRow(customer.toArray());
        }
    }

}
