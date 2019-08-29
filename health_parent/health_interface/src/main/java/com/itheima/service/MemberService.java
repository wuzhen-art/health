package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

public interface MemberService {
    Member findMemberByTelephone(String telephone);

    void registerMember(Member member);

    List<Integer> findMemberCounts(List<String> monthList);
}
