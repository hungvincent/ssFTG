package com.ftg.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Orders generated by hbm2java
 */
@Entity
@Table(name = "offering_box_detail")
public class OfferingBoxDetail implements java.io.Serializable {

	private Integer id;
	private Integer offeringBoxId;
	private String summary;
	private Integer amount;

	public OfferingBoxDetail() {
	}

	public OfferingBoxDetail(String summary, Integer amount) {
		this.summary = summary;
		this.amount = amount;
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
	
	@Column(name = "offering_box_id", nullable = false)
	public Integer getOfferingBoxId() {
		return offeringBoxId;
	}

	public void setOfferingBoxId(Integer offeringBoxId) {
		this.offeringBoxId = offeringBoxId;
	}

	@Column(name = "summary", nullable = false, length = 100)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Column(name = "amount", nullable = false)
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
