package com.imyme010101.restapi.repository;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.imyme010101.restapi.DTO.member.MemberDTO;

@Repository
@Mapper
public interface CartRepository {
  public int add(MemberDTO memberDTO);
  public MemberDTO findAll(@Param("key") String key, @Param("val") String val);
  public String findOne(@Param("key") String key, @Param("val") String val);
}
