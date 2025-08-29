package com.bbs.gobus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbs.gobus.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByMobile(Long mobile);

    User findByMobile(Long mobile);

    User findByEmail(String user);
    

}
