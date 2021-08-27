package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProWenchanType;

@Repository
public interface ProWenchanTypeRepository extends JpaRepository<ProWenchanType, Integer> {
	
	@Query(value = "select p from ProWenchanType p where p.wenchanId=:wenchanId order by p.code")
	public List<ProWenchanType> findByWenchanId(@Param("wenchanId") Integer wenchanId);
	
	@Query(value = "select p from ProWenchanType p where p.wenchanId=:wenchanId and p.code=:code")
	public ProWenchanType findByCode(@Param("wenchanId") Integer wenchanId, @Param("code") String code);
	
	@Query(value = "select p from ProWenchanType p where p.wenchanId=:wenchanId and (p.code=:code or p.name=:name) ")
	public List<ProWenchanType> findByCodeName(@Param("wenchanId") Integer wenchanId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(wenchanId) from ProWenchanType ")
	public Integer findByMaxWenchanId();
}
