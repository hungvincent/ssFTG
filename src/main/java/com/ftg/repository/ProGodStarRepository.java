package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProGodStar;

@Repository
public interface ProGodStarRepository extends JpaRepository<ProGodStar, Integer> {
	
	@Query(value = "select p from ProGodStar p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProGodStar> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProGodStar p where p.sessions=:sessions ")
	public ProGodStar findBySessions(@Param("sessions") String sessions);
	
	@Query(value = "select max(id) from ProGodStar ")
	public Integer findByMaxId();
}
