package com.ftg.dao.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SysParameter generated by hbm2java
 */
@Entity
@Table(name = "sys_parameter")
public class SysParameter implements java.io.Serializable {

	private Integer id;
	private String type;
	private String pkey;
	private String value;
	private Date updateDate;

	public SysParameter() {
	}

	public SysParameter(String type, Date updateDate) {
		this.type = type;
		this.updateDate = updateDate;
	}

	public SysParameter(String type, String pkey, String value, Date updateDate) {
		this.type = type;
		this.pkey = pkey;
		this.value = value;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "pkey", length = 20)
	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	@Column(name = "value", length = 20)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
