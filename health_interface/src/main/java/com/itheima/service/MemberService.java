package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member findMemberByTelephone(String telephone);

    void registerMember(Member member);

    List<Integer> findMemberCounts(List<String> monthList);

    List<Map<String, Object>> findSexCount();

    List<Map<String, Object>> findAgeCount();
}
