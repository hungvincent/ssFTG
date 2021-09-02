package com.ftg.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * ProPrinceStar generated by hbm2java
 */
@Entity
@Table(name = "pro_prince_star")
public class ProPrinceStar implements java.io.Serializable {

	private Integer id;
	private String sessions;
	private String startRegDate;
	private String endRegDate;
	private Date createDate;
	private Integer createUserId;
	private Date updateDate;
	private Integer updateUserId;
	private String eventStart;
	private String eventEnd;
	
	private List<ProPrinceStarType> princeStarType; 

	public ProPrinceStar() {
	}

	public ProPrinceStar(String sessions) {
		this.sessions = sessions;
	}

	public ProPrinceStar(String sessions, String startRegDate, String endRegDate, Date createDate, Integer createUserId,
			Date updateDate, Integer updateUserId, String eventStart, String eventEnd) {
		this.sessions = sessions;
		this.startRegDate = startRegDate;
		this.endRegDate = endRegDate;
		this.createDate = createDate;
		this.createUserId = createUserId;
		this.updateDate = updateDate;
		this.updateUserId = updateUserId;
		this.eventStart = eventStart;
		this.eventEnd = eventEnd;
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

	@Column(name = "sessions", nullable = false, length = 20)
	public String getSessions() {
		return this.sessions;
	}

	public void setSessions(String sessions) {
		this.sessions = sessions;
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
	
	@Column(name = "event_start", length = 10)
	public String getEventStart() {
		return eventStart;
	}
	
	public void setEventStart(String eventStart) {
		this.eventStart = eventStart;
	}

	@Column(name = "event_end", length = 10)
	public String getEventEnd() {
		return eventEnd;
	}

	public void setEventEnd(String eventEnd) {
		this.eventEnd = eventEnd;
	}

	@Transient
	public List<ProPrinceStarType> getPrinceStarType() {
		return princeStarType;
	}

	public void setPrinceStarType(List<ProPrinceStarType> princeStarType) {
		this.princeStarType = princeStarType;
	}
}
