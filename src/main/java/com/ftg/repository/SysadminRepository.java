package com.ftg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.Sysadmin;

@Repository
public interface SysadminRepository extends JpaRepository<Sysadmin, Integer> {
	
	@Query(value = "select s from Sysadmin s where s.name=:name ")
	public Sysadmin findByName(@Param("name") String name);
}
