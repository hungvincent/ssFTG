package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.OfferingBoxDetail;

@Repository
public interface OfferingBoxDetailRepository extends JpaRepository<OfferingBoxDetail, Integer> {
	
	@Query(value = "select ob from OfferingBoxDetail ob where ob.offeringBoxId=:offeringBoxId")
	public List<OfferingBoxDetail> findByOfferingBoxId(@Param("offeringBoxId") Integer offeringBoxId);
}
