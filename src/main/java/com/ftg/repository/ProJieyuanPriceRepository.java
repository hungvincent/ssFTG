package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProJieyuanPrice;

@Repository
public interface ProJieyuanPriceRepository extends JpaRepository<ProJieyuanPrice, Integer> {
	
	@Query(value = "select p from ProJieyuanPrice p where p.jieyuanId=:jieyuanId order by p.code")
	public List<ProJieyuanPrice> findByJieyuanId(@Param("jieyuanId") Integer jieyuanId);
	
	@Query(value = "select p from ProJieyuanPrice p where p.jieyuanId=:jieyuanId and p.code=:code ")
	public ProJieyuanPrice findByCode(@Param("jieyuanId") Integer jieyuanId, @Param("code") String code);
	
	@Query(value = "select p from ProJieyuanPrice p where p.jieyuanId=:jieyuanId and p.name=:name ")
	public ProJieyuanPrice findByName(@Param("jieyuanId") Integer jieyuanId, @Param("name") String name);
	
	@Query(value = "select p from ProJieyuanPrice p where p.jieyuanId=:jieyuanId and (p.code=:code or p.name=:name) ")
	public List<ProJieyuanPrice> findByCodeName(@Param("jieyuanId") Integer jieyuanId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(jieyuanId) from ProJieyuanPrice ")
	public Integer findByMaxJieyuanId();
}
