package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProStopResolve;

@Repository
public interface ProStopResolveRepository extends JpaRepository<ProStopResolve, Integer> {
	
	@Query(value = "select p from ProStopResolve p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProStopResolve> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProStopResolve p where p.sessions=:sessions ")
	public ProStopResolve findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProStopResolve ")
	public Integer findByMaxId();
}
