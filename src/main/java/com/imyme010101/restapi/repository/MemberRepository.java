package com.imyme010101.restapi.repository;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.imyme010101.restapi.DTO.member.MemberDTO;

@Repository
@Mapper
public interface MemberRepository {
  public int add(MemberDTO memberDTO);
  public MemberDTO findAll(String key, String val);
}
