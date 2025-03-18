package org.isdb.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Product {
	private long id;
	private String name;
	private double price;
	private int quantity;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate buyDate;
	private LocalDate sellDate;
	private double amount;

	public Product() {
	}

	public Product(long id, String name, double price, int quantity, LocalDate buyDate, LocalDate sellDate,
			double amount) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.buyDate = buyDate;
		this.sellDate = sellDate;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDate getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(LocalDate buyDate) {
		this.buyDate = buyDate;
	}

	public LocalDate getSellDate() {
		return sellDate;
	}

	public void setSellDate(LocalDate sellDate) {
		this.sellDate = sellDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
