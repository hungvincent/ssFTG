package com.ftg.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ProBucaikuPrice generated by hbm2java
 */
@Entity
@Table(name = "pro_bucaiku_price")
public class ProBucaikuPrice implements java.io.Serializable {

	private int id;
	private Integer bucaikuId;
	private String code;
	private String name;
	private int price;

	public ProBucaikuPrice() {
	}

	public ProBucaikuPrice(int id, String code, String name, int price) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.price = price;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name = "bucaiku_id", nullable = false)
	public Integer getBucaikuId() {
		return bucaikuId;
	}

	public void setBucaikuId(Integer bucaikuId) {
		this.bucaikuId = bucaikuId;
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


	@Column(name = "price", nullable = false)
	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
