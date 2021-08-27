package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProLianghuang;

@Repository
public interface ProLianghuangRepository extends JpaRepository<ProLianghuang, Integer> {
	
	@Query(value = "select p from ProLianghuang p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProLianghuang> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProLianghuang p where p.sessions=:sessions ")
	public ProLianghuang findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProLianghuang ")
	public Integer findByMaxId();
}
