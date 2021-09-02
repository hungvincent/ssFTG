package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProPurdue;

@Repository
public interface ProPurdueRepository extends JpaRepository<ProPurdue, Integer> {
	
	@Query(value = "select p from ProPurdue p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProPurdue> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProPurdue p where p.sessions=:sessions ")
	public ProPurdue findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProPurdue ")
	public Integer findByMaxId();
}
