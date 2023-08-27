package com.imyme010101.restapi.repository;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.imyme010101.restapi.DTO.member.MemberDTO;
import com.imyme010101.restapi.DTO.shop.CartDTO;

@Repository
@Mapper
public interface CartRepository {
  public int add(CartDTO cartDTO);
}
