package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProDonation;

@Repository
public interface ProDonationRepository extends JpaRepository<ProDonation, Integer> {
	
	@Query(value = "select p from ProDonation p where REPLACE(p.endRegDate,'.','')>=:today or p.endRegDate is null order by id desc ")
	public List<ProDonation> findByEndRegDate(@Param("today") String today);
	
	@Query(value = "select p from ProDonation p where p.summary=:summary ")
	public ProDonation findBySummary(@Param("summary") String summary);
}
