package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	
	@Query(value = "select max(receiptNo) from Orders where receiptNo like :receiptNo% ")
	public String findByMaxNo(@Param("receiptNo") String receiptNo);
	
	@Query(value = "select o from Orders o where o.customersId=:customersId ")
	public List<Orders> findByCust(@Param("customersId") int customersId);
	
	@Query(value = "select o from Orders o where o.createUserId=:createUserId ")
	public List<Orders> findByCreator(@Param("createUserId") int createUserId);
}
