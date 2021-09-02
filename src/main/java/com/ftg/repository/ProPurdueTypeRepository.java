package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProPurdueType;

@Repository
public interface ProPurdueTypeRepository extends JpaRepository<ProPurdueType, Integer> {
	
	@Query(value = "select p from ProPurdueType p where p.purdueId=:purdueId order by p.code")
	public List<ProPurdueType> findByPurdueId(@Param("purdueId") int purdueId);
	
	@Query(value = "select p from ProPurdueType p where p.purdueId=:purdueId and p.code=:code ")
	public ProPurdueType findByCode(@Param("purdueId") int purdueId, @Param("code") String code);
	
	@Query(value = "select p from ProPurdueType p where p.purdueId=:purdueId and (p.code=:code or p.name=:name) ")
	public List<ProPurdueType> findByCodeName(@Param("purdueId") int purdueId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(purdueId) from ProPurdueType ")
	public Integer findByMaxPurdueId();
}
