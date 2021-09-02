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
 * Orders generated by hbm2java
 */
@Entity
@Table(name = "orders")
public class Orders implements java.io.Serializable {

	private Integer id;
	private String receiptNo;
	private int customersId;
	private int groupId;
	private Integer totalAmount;
	private String payType;
	private String remark;
	private String isActive;
	private Date createDate;
	private int createUserId;
	private Date deleteDate;
	private Integer deleteUserId;

	public Orders() {
	}

	public Orders(String receiptNo, int customersId, int groupId, String payType, Date createDate, int createUserId) {
		this.receiptNo = receiptNo;
		this.customersId = customersId;
		this.groupId = groupId;
		this.payType = payType;
		this.createDate = createDate;
		this.createUserId = createUserId;
	}

	public Orders(String receiptNo, int customersId, int groupId, Integer totalAmount, String payType, String remark, String isActive, 
					Date createDate, int createUserId, Date deleteDate, Integer deleteUserId) {
		this.receiptNo = receiptNo;
		this.customersId = customersId;
		this.groupId = groupId;
		this.totalAmount = totalAmount;
		this.payType = payType;
		this.remark = remark;
		this.isActive = isActive;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.deleteDate = deleteDate;
		this.deleteUserId = deleteUserId;
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

	@Column(name = "receipt_no", nullable = false, length = 20)
	public String getReceiptNo() {
		return this.receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	@Column(name = "customers_id", nullable = false)
	public int getCustomersId() {
		return this.customersId;
	}

	public void setCustomersId(int customersId) {
		this.customersId = customersId;
	}
	
	@Column(name = "group_id", nullable = false)
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "total_amount")
	public Integer getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Column(name = "pay_type", nullable = false, length = 100)
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "create_user_id", nullable = false)
	public int getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "delete_date", length = 19)
	public Date getDeleteDate() {
		return this.deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	@Column(name = "delete_user_id")
	public Integer getDeleteUserId() {
		return this.deleteUserId;
	}

	public void setDeleteUserId(Integer deleteUserId) {
		this.deleteUserId = deleteUserId;
	}

}
