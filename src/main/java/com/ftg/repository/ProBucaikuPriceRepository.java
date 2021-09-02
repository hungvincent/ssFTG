package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProBucaikuPrice;

@Repository
public interface ProBucaikuPriceRepository extends JpaRepository<ProBucaikuPrice, Integer> {
	
	@Query(value = "select p from ProBucaikuPrice p where p.bucaikuId=:bucaikuId order by p.code")
	public List<ProBucaikuPrice> findByBucaikuId(@Param("bucaikuId") Integer bucaikuId);

	@Query(value = "select p from ProBucaikuPrice p where p.bucaikuId=:bucaikuId and p.code=:code ")
	public ProBucaikuPrice findByCode(@Param("bucaikuId") Integer bucaikuId, @Param("code") String code);
	
	@Query(value = "select p from ProBucaikuPrice p where p.bucaikuId=:bucaikuId and p.name=:name ")
	public ProBucaikuPrice findByName(@Param("bucaikuId") Integer bucaikuId, @Param("name") String name);
	
	@Query(value = "select p from ProBucaikuPrice p where p.bucaikuId=:bucaikuId and (p.code=:code or p.name=:name) ")
	public List<ProBucaikuPrice> findByCodeName(@Param("bucaikuId") Integer bucaikuId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(bucaikuId) from ProBucaikuPrice ")
	public Integer findByMaxBucaikuId();
}
