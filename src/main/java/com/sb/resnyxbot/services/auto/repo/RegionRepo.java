package com.sb.resnyxbot.services.auto.repo;

import java.util.List;

import com.sb.resnyxbot.services.auto.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepo extends JpaRepository<Region, Integer> {

    List<Region> findByNameContainingIgnoreCase(String name);

}
