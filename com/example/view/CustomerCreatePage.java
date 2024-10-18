package com.example.view;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CustomerDao;
import com.cinema.model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerCreatePage {
    private AbstractDao<Customer> customerDao;
    private JFrame customerCreateFrame;
    private JPanel registerPanel;

    private JLabel customerNameLabel;
    private JTextField customerTextField;

    private JLabel emailLabel;
    private JTextField emailTextField;

    private JLabel addressLabel;
    private JTextField addressTextField;

    private JButton createBtn;
    private JButton cancelBtn;
    private CustomerListingView parentFrame;

    public CustomerCreatePage(CustomerListingView parentFrame){
        this.customerDao =  new CustomerDao();
        initializeComponent();
        this.parentFrame = parentFrame;
        this.customerCreateFrame.setSize(700,500);
        this.customerCreateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.customerCreateFrame.setLocationRelativeTo(null);
        this.customerCreateFrame.setVisible(true);

    }

    private void initializeComponent() {
        this.customerCreateFrame = new JFrame("Customer Registeration Form");
        this.customerCreateFrame.setLayout(new BorderLayout());

        this.createBtn = new JButton("Create");
        this.cancelBtn = new JButton("Cancel");

        this.customerNameLabel = new JLabel("Customer Name : ");
        this.customerTextField = new JTextField(20);

        this.emailLabel = new JLabel("Customer Email : ");
        this.emailTextField = new JTextField(50);

        this.addressLabel = new JLabel("Customer Address : ");
        this.addressTextField = new JTextField(50);

        this.registerPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(4,2);
        this.registerPanel.setLayout(gridLayout);

        this.registerPanel.add(this.customerNameLabel);
        this.registerPanel.add(this.customerTextField);

        this.registerPanel.add(this.emailLabel);
        this.registerPanel.add(this.emailTextField);

        this.registerPanel.add(this.addressLabel);
        this.registerPanel.add(this.addressTextField);

        this.registerPanel.add(this.createBtn);
        this.registerPanel.add(this.cancelBtn);

        this.customerCreateFrame.add(registerPanel,BorderLayout.NORTH);

        addActionRegisterBtn();
    }

    private void addActionRegisterBtn(){
        this.createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerRegisterAction();
            }
        });
    }

    private void customerRegisterAction() {
        String name = this.customerTextField.getText();
        String email =this.emailTextField.getText();
        String address=this.addressTextField.getText();

        Customer customer = new Customer(name,email,address);
        this.customerDao.create(customer);

        JOptionPane.showMessageDialog(this.customerCreateFrame,"Customer Sucessfully added");

        this.parentFrame.refreshCustomerTable();
        this.customerCreateFrame.dispose();
    }
}
