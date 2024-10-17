package com.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int id;
	private String name;
	private String email;
	private String address;
	private List<Ticket> tickets = new ArrayList<Ticket>();
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<Ticket> getTickets() {
		return tickets;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String[] toArray() {
		String[] customerData = new String[8];
		customerData[0] = this.id+"";
		customerData[1] = this.getName();
		customerData[2] = this.getEmail();
		customerData[3] = this.getAddress();
        return customerData;
    }

	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name +"]";
	}
}
