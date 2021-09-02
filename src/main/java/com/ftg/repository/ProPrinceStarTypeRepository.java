package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProPrinceStarType;

@Repository
public interface ProPrinceStarTypeRepository extends JpaRepository<ProPrinceStarType, Integer> {
	
	@Query(value = "select p from ProPrinceStarType p where p.princeStarId=:princeStarId order by p.code")
	public List<ProPrinceStarType> findByPrinceStarId(@Param("princeStarId") int princeStarId);
	
	@Query(value = "select p from ProPrinceStarType p where p.princeStarId=:princeStarId and p.code=:code")
	public ProPrinceStarType findByCode(@Param("princeStarId") Integer princeStarId, @Param("code") String code);
	
	@Query(value = "select p from ProPrinceStarType p where p.princeStarId=:princeStarId and (p.code=:code or p.name=:name) ")
	public List<ProPrinceStarType> findByCodeName(@Param("princeStarId") int princeStarId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(princeStarId) from ProPrinceStarType ")
	public Integer findByMaxPrinceStarId();
}
