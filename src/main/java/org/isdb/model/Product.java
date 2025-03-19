package org.isdb.model;

public class Product {
	private int id;
	private String name;
	private double price;
	private int quantity;
	private String model;

	public Product() {
	}

	public Product(int id, String name, double price, int quantity, String model) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.model = model;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
