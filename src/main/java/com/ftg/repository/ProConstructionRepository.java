package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProConstruction;

@Repository
public interface ProConstructionRepository extends JpaRepository<ProConstruction, Integer> {
	
	@Query(value = "select p from ProConstruction p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProConstruction> findByEndRegDate(@Param("today") String today);
	

	@Query(value = "select p from ProConstruction p where p.summary=:summary ")
	public ProConstruction findBySummary(@Param("summary") String summary);
}