package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProBucaiku;

@Repository
public interface ProBucaikuRepository extends JpaRepository<ProBucaiku, Integer> {
	
	@Query(value = "select p from ProBucaiku p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProBucaiku> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProBucaiku p where p.sessions=:sessions ")
	public ProBucaiku findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProBucaiku ")
	public Integer findByMaxId();
}
