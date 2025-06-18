package com.example.demo.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.crud.model.entity.UserDetails;


public interface UserRepository extends JpaRepository<UserDetails, Long>{

}
