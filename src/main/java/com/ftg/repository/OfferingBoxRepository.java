package com.ftg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.OfferingBox;

@Repository
public interface OfferingBoxRepository extends JpaRepository<OfferingBox, Integer> {

	@Query(value = "select max(receiptNo) from OfferingBox where receiptNo like :receiptNo% ")
	public String findByMaxNo(@Param("receiptNo") String receiptNo);
}
