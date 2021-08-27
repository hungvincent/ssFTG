package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProPilgrimage;

@Repository
public interface ProPilgrimageRepository extends JpaRepository<ProPilgrimage, Integer> {
	
	@Query(value = "select p from ProPilgrimage p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProPilgrimage> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProPilgrimage p where p.sessions=:sessions ")
	public ProPilgrimage findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProPilgrimage ")
	public Integer findByMaxId();
}
	