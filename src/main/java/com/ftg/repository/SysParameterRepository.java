package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.SysParameter;

@Repository
public interface SysParameterRepository extends JpaRepository<SysParameter, Integer> {
	
	@Query(value = "select s from SysParameter s where s.type=:type ")
	public List<SysParameter> findByType(@Param("type") String type);
}
