package com.imyme010101.restapi.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.imyme010101.restapi.DTO.member.MembersDTO;

@Repository
@Mapper
public interface MembersRepository {
  public MembersDTO findById(String $username);
}
