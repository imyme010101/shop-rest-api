package com.imyme010101.restapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.imyme010101.restapi.DTO.member.MemberDTO;

@Repository
@Mapper
public interface MemberRepository {
  public MemberDTO findById(String username);
}
