package com.imyme010101.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imyme010101.restapi.DTO.member.MemberDTO;
import com.imyme010101.restapi.repository.MemberRepository;

@Service
public class MemberService {
  @Autowired
  private MemberRepository memberRepository;
  
  @Transactional
  public boolean add(MemberDTO memberDTO) throws Exception {
    if (memberRepository.add(memberDTO) == 0) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * 디비에 해당 컬럼 값이 존재 하는지 확인
   *
   * @param  key
   * @param  val
   * @return      아이디
   */
  public boolean check(String key, String val) {
    return memberRepository.findOne(key, val) != null;
  }

  public MemberDTO get(String memberId) {
    return memberRepository.findAll("id", memberId);
  }
}
