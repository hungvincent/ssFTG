package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProLianghuangType;

@Repository
public interface ProLianghuangTypeRepository extends JpaRepository<ProLianghuangType, Integer> {
	
	@Query(value = "select p from ProLianghuangType p where p.lianghuangId=:lianghuangId order by p.code")
	public List<ProLianghuangType> findByLianghuangId(@Param("lianghuangId") int lianghuangId);
	
	@Query(value = "select p from ProLianghuangType p where p.lianghuangId=:lianghuangId and p.code=:code")
	public ProLianghuangType findByCode(@Param("lianghuangId") int lianghuangId, @Param("code") String code);
	
	@Query(value = "select p from ProLianghuangType p where p.lianghuangId=:lianghuangId and (p.code=:code or p.name=:name) ")
	public List<ProLianghuangType> findByCodeName(@Param("lianghuangId") int lianghuangId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(lianghuangId) from ProLianghuangType ")
	public Integer findByMaxLianghuangId();
}
