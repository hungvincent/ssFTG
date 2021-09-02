package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProLightType;

@Repository
public interface ProLightTypeRepository extends JpaRepository<ProLightType, Integer> {
	
	@Query(value = "select p from ProLightType p where p.lightId=:lightId order by p.code")
	public List<ProLightType> findByLightId(@Param("lightId") Integer lightId);
	
	@Query(value = "select p from ProLightType p where p.lightId=:lightId and p.code=:code")
	public ProLightType findByCode(@Param("lightId") Integer lightId, @Param("code") String code);
	
	@Query(value = "select p from ProLightType p where p.lightId=:lightId and (p.code=:code or p.name=:name) ")
	public List<ProLightType> findByCodeName(@Param("lightId") Integer lightId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(lightId) from ProLightType ")
	public Integer findByMaxLightId();
}
