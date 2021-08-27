package com.ftg.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OrdersDonation generated by hbm2java
 */
@Entity
@Table(name = "orders_donation")
public class OrdersDonation implements java.io.Serializable {

	private Integer id;
	private int ordersId;
	private int customersId;
	private String serialNo;
	private Integer amount;
	private Integer age;
	private String summary;

	public OrdersDonation() {
	}

	public OrdersDonation(int ordersId, int customersId, String serialNo) {
		this.ordersId = ordersId;
		this.customersId = customersId;
		this.serialNo = serialNo;
	}

	public OrdersDonation(int ordersId, int customersId, String serialNo, Integer amount, Integer age, 
			String summary) {
		this.ordersId = ordersId;
		this.customersId = customersId;
		this.serialNo = serialNo;
		this.amount = amount;
		this.age = age;
		this.summary = summary;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "orders_id", nullable = false)
	public int getOrdersId() {
		return this.ordersId;
	}

	public void setOrdersId(int ordersId) {
		this.ordersId = ordersId;
	}

	@Column(name = "customers_id", nullable = false)
	public int getCustomersId() {
		return this.customersId;
	}

	public void setCustomersId(int customersId) {
		this.customersId = customersId;
	}

	@Column(name = "serial_no", nullable = false, length = 20)
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	@Column(name = "amount")
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "summary", length = 200)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
