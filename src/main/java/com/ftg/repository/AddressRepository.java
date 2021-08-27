package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	
	@Query(value = "select a from Address a where a.address=:address ")
	public Address findByAddress(@Param("address") String address);
	
	
	@Query(value = "select a from Address a where a.id in (:ids) ")
	public List<Address> findIdIn(@Param("ids") List<Integer> ids);
}
