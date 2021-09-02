package com.ftg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.OrdersDonateGold;

@Repository
public interface OrdersDonateGoldRepository extends JpaRepository<OrdersDonateGold, Integer> {

	@Query(value = "select max(serialNo) from OrdersDonateGold where serialNo like :serialNo% ")
	public String findByMaxNo(@Param("serialNo") String serialNo);
}
