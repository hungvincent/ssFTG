package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.Customers;

@Repository
public interface CustomersRepository extends JpaRepository<Customers, Integer> {
	
	@Query(value = "select c from Customers c where c.name=:name and groupId=:groupId ")
	public Customers findNameByGroupId(@Param("name") String name, @Param("groupId")int groupId);
	
	@Query(value = "select c from Customers c where c.name=:name ")
	public List<Customers> findByName(@Param("name") String name);
	
	@Query(value = "select c from Customers c where c.groupId=:groupId order by groupSort ")
	public List<Customers> findByGroupId(@Param("groupId") int groupId);
	
	@Query(value = "select c from Customers c where c.phone1=:phone1 ")
	public List<Customers> findByPhone1(@Param("phone1") String phone1);
}
