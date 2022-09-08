package com.example.powerplantsystem.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.powerplantsystem.model.Battery;


@Repository
public interface BatteryRepository extends JpaRepository<Battery, UUID> {
	@Query(value = "select * from battery  where postcode between ?1 and ?2 order by name_battery asc",  nativeQuery = true)
	List<Battery> findByRange(Long fromPostcode,Long toPostcode);
}
