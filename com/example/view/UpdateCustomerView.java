package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateCustomerView{
    private AbstractDao<Customer> customerDao;
    private JFrame customerCreateFrame;
    private JPanel registerPanel;

    private JLabel customerNameLabel;
    private JTextField customerTextField;

    private JLabel emailLabel;
    private JTextField emailTextField;

    private JLabel addressLabel;
    private JTextField addressTextField;

    private JButton updateBtn;
    private JButton cancelBtn;

    private Customer customer;
    private CustomerListingView parentFrame;



    public UpdateCustomerView(CustomerListingView parentFrame, int customerId){
        this.customerDao =  new CustomerDao();
        this.customer = this.customerDao.findbyId(customerId);
        initializeComponent();
        this.parentFrame = parentFrame;
        this.customerCreateFrame.setSize(700,500);
        this.customerCreateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.customerCreateFrame.setLocationRelativeTo(null);
        this.customerCreateFrame.setVisible(true);

    }

    private void initializeComponent() {
        this.customerCreateFrame = new JFrame("Customer Registeration Form");
        this.customerCreateFrame.setLayout(new BorderLayout());

        this.updateBtn = new JButton("Update");
        this.cancelBtn = new JButton("Cancel");
        this.customerNameLabel = new JLabel("Customer Name : ");
        this.customerTextField = new JTextField(this.customer.getName());
        this.emailLabel = new JLabel("Customer Email : ");
        this.emailTextField = new JTextField(this.customer.getEmail());
        this.addressLabel = new JLabel("Customer Address : ");
        this.addressTextField = new JTextField(this.customer.getAddress());

        this.registerPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(4,2);
        this.registerPanel.setLayout(gridLayout);

        this.registerPanel.add(this.customerNameLabel);
        this.registerPanel.add(this.customerTextField);
        this.registerPanel.add(this.emailLabel);
        this.registerPanel.add(this.emailTextField);
        this.registerPanel.add(this.addressLabel);
        this.registerPanel.add(this.addressTextField);
        this.registerPanel.add(this.updateBtn);
        this.registerPanel.add(this.cancelBtn);

        this.customerCreateFrame.add(this.registerPanel,BorderLayout.NORTH);

        addActionUpdateBtn();
    }
    private void addActionUpdateBtn(){
        this.updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerUpdateAction();
            }
        });
    }


    private void customerUpdateAction() {
        String name = this.customerTextField.getText();
        String email =this.emailTextField.getText();
        String address=this.addressTextField.getText();

        Customer customer = new Customer(this.customer.getId(),name,email,address);
        this.customerDao.update(customer);

        JOptionPane.showMessageDialog(this.customerCreateFrame,"Sucess");

        this.parentFrame.refreshCustomerTable();
        this.customerCreateFrame.dispose();
    }
}
