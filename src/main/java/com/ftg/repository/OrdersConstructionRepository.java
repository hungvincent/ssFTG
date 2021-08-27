package com.ftg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.OrdersConstruction;

@Repository
public interface OrdersConstructionRepository extends JpaRepository<OrdersConstruction, Integer> {

	@Query(value = "select max(serialNo) from OrdersConstruction where serialNo like :serialNo% ")
	public String findByMaxNo(@Param("serialNo") String serialNo);
}
