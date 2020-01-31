package com.sb.resnyxbot.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepo extends JpaRepository<Region, Integer> {

    Region findByNameStartsWithIgnoreCase(String name);

}
