package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProGodStarType;

@Repository
public interface ProGodStarTypeRepository extends JpaRepository<ProGodStarType, Integer> {
	
	@Query(value = "select p from ProGodStarType p where p.godStarId=:godStarId order by p.code")
	public List<ProGodStarType> findByGodStarId(@Param("godStarId") Integer godStarId);
	
	@Query(value = "select p from ProGodStarType p where p.godStarId=:godStarId and p.code=:code")
	public ProGodStarType findByCode(@Param("godStarId") Integer godStarId, @Param("code") String code);
	
	@Query(value = "select p from ProGodStarType p where p.godStarId=:godStarId and (p.code=:code or p.name=:name) ")
	public List<ProGodStarType> findByCodeName(@Param("godStarId") Integer godStarId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(godStarId) from ProGodStarType ")
	public Integer findByMaxGodStarId();
}
