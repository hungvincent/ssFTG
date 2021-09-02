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
 * ProPilgrimageType generated by hbm2java
 */
@Entity
@Table(name = "pro_pilgrimage_type")
public class ProPilgrimageType implements java.io.Serializable {

	private Integer id;
	private Integer pilgrimageId;
	private String code;
	private String name;
	private Integer inventory;
	private Integer quantity;
	private Integer price;
	private Date createDate;
	private Integer createUserId;
	private Date updateDate;
	private Integer updateUserId;

	public ProPilgrimageType() {
	}

	public ProPilgrimageType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public ProPilgrimageType(Integer pilgrimageId, String code, String name, Integer inventory, Integer quantity, Integer price,
			Date createDate, Integer createUserId, Date updateDate, Integer updateUserId) {
		this.pilgrimageId = pilgrimageId;
		this.code = code;
		this.name = name;
		this.inventory = inventory;
		this.quantity = quantity;
		this.price = price;
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

	@Column(name = "pilgrimage_id")
	public Integer getPilgrimageId() {
		return this.pilgrimageId;
	}

	public void setPilgrimageId(Integer pilgrimageId) {
		this.pilgrimageId = pilgrimageId;
	}

	@Column(name = "code", nullable = false, length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "inventory")
	public Integer getInventory() {
		return inventory;
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

	@Column(name = "price")
	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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