package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProPilgrimageType;

@Repository
public interface ProPilgrimageTypeRepository extends JpaRepository<ProPilgrimageType, Integer> {
	
	@Query(value = "select p from ProPilgrimageType p where p.pilgrimageId=:pilgrimageId order by p.code")
	public List<ProPilgrimageType> findByPilgrimageId(@Param("pilgrimageId") Integer pilgrimageId);
	
	@Query(value = "select p from ProPilgrimageType p where p.pilgrimageId=:pilgrimageId and p.code=:code")
	public ProPilgrimageType findByCode(@Param("pilgrimageId") Integer pilgrimageId, @Param("code") String code);
	
	@Query(value = "select p from ProPilgrimageType p where p.pilgrimageId=:pilgrimageId and (p.code=:code or p.name=:name) ")
	public List<ProPilgrimageType> findByCodeName(@Param("pilgrimageId") Integer pilgrimageId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(pilgrimageId) from ProPilgrimageType ")
	public Integer findByMaxPilgrimageId();
}
