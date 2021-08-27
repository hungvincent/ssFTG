package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProPrinceStar;

@Repository
public interface ProPrinceStarRepository extends JpaRepository<ProPrinceStar, Integer> {
	
	@Query(value = "select p from ProPrinceStar p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProPrinceStar> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProPrinceStar p where p.sessions=:sessions ")
	public ProPrinceStar findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProPrinceStar ")
	public Integer findByMaxId();
}
