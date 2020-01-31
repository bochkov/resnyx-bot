package com.sb.resnyxbot.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepo extends JpaRepository<Region, Integer> {

    List<Region> findByNameContainingIgnoreCase(String name);

}
