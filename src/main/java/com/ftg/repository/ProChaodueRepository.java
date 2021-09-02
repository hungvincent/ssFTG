package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProChaodue;

@Repository
public interface ProChaodueRepository extends JpaRepository<ProChaodue, Integer> {
	
	@Query(value = "select p from ProChaodue p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProChaodue> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProChaodue p where p.sessions=:sessions ")
	public ProChaodue findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProChaodue ")
	public Integer findByMaxId();
}
