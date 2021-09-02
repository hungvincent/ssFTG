package com.ftg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProDonateItems;

@Repository
public interface ProDonateItemsRepository extends JpaRepository<ProDonateItems, Integer> {
	
	@Query(value = "select p from ProDonateItems p where p.summary=:summary ")
	public ProDonateItems findBySummary(@Param("summary") String summary);
}
