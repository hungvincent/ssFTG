package com.ftg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.OrdersGodStar;

@Repository
public interface OrdersGodStarRepository extends JpaRepository<OrdersGodStar, Integer> {

	@Query(value = "select max(serialNo) from OrdersGodStar where serialNo like :serialNo% ")
	public String findByMaxNo(@Param("serialNo") String serialNo);
}
