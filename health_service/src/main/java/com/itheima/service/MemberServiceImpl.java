package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;


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

    @Override
    public List<Map<String, Object>> findSexCount() {
        List<Map<String, Object>> list = memberDao.findSexCount();
        if(list!=null&&list.size()>0){
            for (Map<String, Object> map : list) {
                Object name = map.get("name");
                if(name==null){
                    map.put("name","未知性别");
                }else if("1".equals((String)name)){
                    map.put("name","男");
                }else if("2".equals((String)name)){
                    map.put("name","女");
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findAgeCount() {
        Calendar calendar = Calendar.getInstance();
        String date1 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        calendar.add(Calendar.YEAR,-18);
        String date2 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        calendar.add(Calendar.YEAR,-12);
        String date3 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        calendar.add(Calendar.YEAR,-15);
        String date4 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        calendar.add(Calendar.YEAR,-200);
        String date5 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

        List<Map<String, Object>> list = new ArrayList<>();

        int num1 = memberDao.findCountBetweenDate(date2,date1);
        Map<String,Object> map1 = new HashMap();
        map1.put("name","0-18岁");
        map1.put("value",num1);
        list.add(map1);

        int num2 = memberDao.findCountBetweenDate(date3,date2);
        Map<String,Object> map2 = new HashMap();
        map2.put("name","18-30岁");
        map2.put("value",num2);
        list.add(map2);

        int num3 = memberDao.findCountBetweenDate(date4,date3);
        Map<String,Object> map3 = new HashMap();
        map3.put("name","30-45岁");
        map3.put("value",num3);
        list.add(map3);

        int num4 = memberDao.findCountBetweenDate(date5,date4);
        Map<String,Object> map4 = new HashMap();
        map4.put("name","45岁以上");
        map4.put("value",num4);
        list.add(map4);

        return list;
    }
}
