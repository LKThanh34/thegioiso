package com.lekimthanh.thegioiso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lekimthanh.thegioiso.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
