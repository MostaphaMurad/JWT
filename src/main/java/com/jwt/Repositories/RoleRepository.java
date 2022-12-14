package com.jwt.Repositories;

import com.jwt.Models.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByName(String name);
}
