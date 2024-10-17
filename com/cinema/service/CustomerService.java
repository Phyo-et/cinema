package com.cinema.service;

import java.io.IOException;
import java.sql.SQLException;

import com.cinema.dao.AbstractDao;
import com.cinema.model.Customer;
import com.cinema.dao.CustomerDao;

public class CustomerService extends BaseService<Customer>{
	
	private static AbstractDao<Customer> customerDao = new CustomerDao();

	public CustomerService() {
		super(customerDao);
	}


	@Override
	public String getEntity() {
		return "Customer";
	}

	@Override
	public void register() throws SQLException, IOException {
		System.out.print("Enter customer name: ");
		String name = br.readLine();
		System.out.println("Enter the Email Address : ");
		String email =br.readLine();
		System.out.println("Enter the Address : ");
		String address = br.readLine();

		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setAddress(address);
		customerDao.create(customer);
	}


}
