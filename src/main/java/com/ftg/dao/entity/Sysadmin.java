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
 * Sysadmin generated by hbm2java
 */
@Entity
@Table(name = "sysadmin")
public class Sysadmin implements java.io.Serializable {

	private Integer id;
	private String name;
	private String password;
	private String authCount;
	private String authOfferingBox;
	private String authParamSet;
	private String authReportPrint;
	private String authQuery;
	private String isActive;
	private Date createDate;
	private Date updateDate;

	public Sysadmin() {
	}

	public Sysadmin(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public Sysadmin(String name, String password, String authCount, String authOfferingBox, String authParamSet,
			String authReportPrint, String authQuery, String isActive, Date createDate, Date updateDate) {
		this.name = name;
		this.password = password;
		this.authCount = authCount;
		this.authOfferingBox = authOfferingBox;
		this.authParamSet = authParamSet;
		this.authReportPrint = authReportPrint;
		this.authQuery = authQuery;
		this.isActive = isActive;
		this.createDate = createDate;
		this.updateDate = updateDate;
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

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", nullable = false, length = 20)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "auth_count", length = 1)
	public String getAuthCount() {
		return this.authCount;
	}

	public void setAuthCount(String authCount) {
		this.authCount = authCount;
	}

	@Column(name = "auth_offering_box", length = 1)
	public String getAuthOfferingBox() {
		return this.authOfferingBox;
	}

	public void setAuthOfferingBox(String authOfferingBox) {
		this.authOfferingBox = authOfferingBox;
	}

	@Column(name = "auth_param_set", length = 1)
	public String getAuthParamSet() {
		return this.authParamSet;
	}

	public void setAuthParamSet(String authParamSet) {
		this.authParamSet = authParamSet;
	}

	@Column(name = "auth_report_print", length = 1)
	public String getAuthReportPrint() {
		return this.authReportPrint;
	}

	public void setAuthReportPrint(String authReportPrint) {
		this.authReportPrint = authReportPrint;
	}

	@Column(name = "auth_query", length = 1)
	public String getAuthQuery() {
		return this.authQuery;
	}

	public void setAuthQuery(String authQuery) {
		this.authQuery = authQuery;
	}

	@Column(name = "is_active", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
