package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProAnniversaryType;

@Repository
public interface ProAnniversaryTypeRepository extends JpaRepository<ProAnniversaryType, Integer> {
	
	@Query(value = "select p from ProAnniversaryType p where p.anniversaryId=:anniversaryId order by p.code")
	public List<ProAnniversaryType> findByAnniversaryId(@Param("anniversaryId") int anniversaryId);
	
	@Query(value = "select p from ProAnniversaryType p where p.anniversaryId=:anniversaryId and p.code=:code")
	public ProAnniversaryType findByCode(@Param("anniversaryId") int anniversaryId, @Param("code") String code);
	
	@Query(value = "select p from ProAnniversaryType p where p.anniversaryId=:anniversaryId and (p.code=:code or p.name=:name) ")
	public List<ProAnniversaryType> findByCodeName(@Param("anniversaryId") int anniversaryId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(anniversaryId) from ProAnniversaryType ")
	public Integer findByMaxAnniversaryId();
}
