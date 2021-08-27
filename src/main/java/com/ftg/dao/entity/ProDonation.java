package com.ftg.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProDonation generated by hbm2java
 */
@Entity
@Table(name = "pro_donation")
public class ProDonation implements java.io.Serializable {

	private Integer id;
	private String summary;
	private Integer price;
	private Integer inventory;
	private Integer quantity;
	private String startRegDate;
	private String endRegDate;
	private Date createDate;
	private Integer createUserId;
	private Date updateDate;
	private Integer updateUserId;

	public ProDonation() {
	}

	public ProDonation(String summary, Integer price, Integer inventory, Integer quantity, String startRegDate, String endRegDate, 
			Date createDate, Integer createUserId, Date updateDate, Integer updateUserId) {
		this.summary = summary;
		this.price = price;
		this.inventory = inventory;
		this.quantity = quantity;
		this.startRegDate = startRegDate;
		this.endRegDate = endRegDate;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.updateDate = updateDate;
		this.updateUserId = updateUserId;
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

	@Column(name = "summary", length = 100)
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Column(name = "price")
	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Column(name = "inventory")
	public Integer getInventory() {
		return this.inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
	
	@Column(name = "quantity")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Column(name = "start_reg_date", length = 10)
	public String getStartRegDate() {
		return this.startRegDate;
	}

	public void setStartRegDate(String startRegDate) {
		this.startRegDate = startRegDate;
	}

	@Column(name = "end_reg_date", length = 10)
	public String getEndRegDate() {
		return this.endRegDate;
	}

	public void setEndRegDate(String endRegDate) {
		this.endRegDate = endRegDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_user_id")
	public Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "update_user_id")
	public Integer getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

}
