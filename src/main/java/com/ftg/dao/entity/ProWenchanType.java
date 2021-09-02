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
 * ProWenchanType generated by hbm2java
 */
@Entity
@Table(name = "pro_wenchan_type")
public class ProWenchanType implements java.io.Serializable {

	private Integer id;
	private Integer wenchanId;
	private String code;
	private String name;
	private Integer inventory;
	private Integer quantity;
	private Integer price;
	private Date createDate;
	private Integer createUserId;
	private Date updateDate;
	private Integer updateUserId;

	public ProWenchanType() {
	}

	public ProWenchanType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public ProWenchanType(Integer wenchanId, String code, String name, Integer inventory, Integer quantity, Integer price, Date createDate,
			Integer createUserId, Date updateDate, Integer updateUserId) {
		this.wenchanId = wenchanId;
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

	@Column(name = "wenchan_id")
	public Integer getWenchanId() {
		return this.wenchanId;
	}

	public void setWenchanId(Integer wenchanId) {
		this.wenchanId = wenchanId;
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