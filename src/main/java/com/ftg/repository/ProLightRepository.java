package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProLight;

@Repository
public interface ProLightRepository extends JpaRepository<ProLight, Integer> {
	
	@Query(value = "select p from ProLight p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProLight> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProLight p where p.sessions=:sessions ")
	public ProLight findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProLight ")
	public Integer findByMaxId();
}
