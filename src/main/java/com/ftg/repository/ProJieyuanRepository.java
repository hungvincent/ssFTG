package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProJieyuan;

@Repository
public interface ProJieyuanRepository extends JpaRepository<ProJieyuan, Integer> {
	
	@Query(value = "select p from ProJieyuan p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProJieyuan> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProJieyuan p where p.sessions=:sessions ")
	public ProJieyuan findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProJieyuan ")
	public Integer findByMaxId();
}
