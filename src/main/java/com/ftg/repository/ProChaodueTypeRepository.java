package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProChaodueType;

@Repository
public interface ProChaodueTypeRepository extends JpaRepository<ProChaodueType, Integer> {
	
	@Query(value = "select p from ProChaodueType p where p.chaodueId=:chaodueId order by p.code")
	public List<ProChaodueType> findByChaodueId(@Param("chaodueId") int chaodueId);
	
	@Query(value = "select p from ProChaodueType p where p.chaodueId=:chaodueId and p.code=:code ")
	public ProChaodueType findByCode(@Param("chaodueId") int chaodueId, @Param("code") String code);
	
	@Query(value = "select p from ProChaodueType p where p.chaodueId=:chaodueId and (p.code=:code or p.name=:name) ")
	public List<ProChaodueType> findByCodeName(@Param("chaodueId") int chaodueId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(chaodueId) from ProChaodueType ")
	public Integer findByMaxChaodueId();
}
