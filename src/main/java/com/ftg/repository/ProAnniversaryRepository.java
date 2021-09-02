package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProAnniversary;

@Repository
public interface ProAnniversaryRepository extends JpaRepository<ProAnniversary, Integer> {
	
	@Query(value = "select p from ProAnniversary p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProAnniversary> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProAnniversary p where p.sessions=:sessions ")
	public ProAnniversary findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProAnniversary ")
	public Integer findByMaxId();
}
