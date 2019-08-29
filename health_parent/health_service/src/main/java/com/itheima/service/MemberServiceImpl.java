package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findMemberByTelephone(String telephone) {
        Member member = memberDao.findMemberByTelephone(telephone);
        return member;
    }

    @Override
    public void registerMember(Member member) {
        memberDao.addMember(member);

    }

    @Override
    public List<Integer> findMemberCounts(List<String> monthList) {
        List<Integer> list = new ArrayList<>();
        for (String month : monthList) {
            String date=month+"-31";
            Integer count = memberDao.findMemberCount(date);
            list.add(count);
        }
        return list;
    }
}
