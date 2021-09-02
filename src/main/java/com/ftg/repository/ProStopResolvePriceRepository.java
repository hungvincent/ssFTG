package com.ftg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftg.dao.entity.ProStopResolvePrice;

@Repository
public interface ProStopResolvePriceRepository extends JpaRepository<ProStopResolvePrice, Integer> {
	
	@Query(value = "select p from ProStopResolvePrice p where p.stopResolveId=:stopResolveId order by p.code")
	public List<ProStopResolvePrice> findByStopResolveId(@Param("stopResolveId") Integer stopResolveId);
	
	@Query(value = "select p from ProStopResolvePrice p where p.stopResolveId=:stopResolveId and (p.code=:code or p.name=:name) ")
	public List<ProStopResolvePrice> findByCodeName(@Param("stopResolveId") Integer stopResolveId, @Param("code") String code, @Param("name") String name);
	
	@Query(value = "select max(stopResolveId) from ProStopResolvePrice ")
	public Integer findByMaxStopResolveId();
}
