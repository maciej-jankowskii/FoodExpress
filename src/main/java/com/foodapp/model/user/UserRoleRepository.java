package com.foodapp.model.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    Optional<UserRole> findByName(String name);
}
