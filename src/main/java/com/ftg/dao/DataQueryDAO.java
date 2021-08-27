package com.ftg.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ftg.dao.entity.Address;


@Component
public class DataQueryDAO {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	
	@Transactional
	public List<Map<String, Object>> queryCustByAddr(String name, String phone1, String address) {
		return null;
	}
}
