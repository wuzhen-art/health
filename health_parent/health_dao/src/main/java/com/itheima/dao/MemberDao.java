package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface MemberDao {
    /*
       setName((String)map.get("name"));
       setPhoneNumber((String)map.get("telephone"))
       setIdCard((String)map.get("idCard"));
       setSex((String)map.get("sex"));
       setRegTime(new Date());
     */

    void addMember(Member member);

    @Select("select * from t_member where phoneNumber=#{telephone}")
    Member findMemberByTelephone(String telephone);

    @Select("select * from t_member where id = (select member_id from t_order where id=#{id})")
    Member findMemberById(Integer id);

    @Select("select count(id) from t_member where regTime <= #{value}")
    int findMemberCount(String date);
}
