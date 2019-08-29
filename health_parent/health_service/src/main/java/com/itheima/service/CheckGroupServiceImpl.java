package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //1.调用分页插件的方法
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //2.调用Dao获得Page
        Page<CheckGroup> page = checkGroupDao.findByConditions(queryPageBean.getQueryString());
        //3.封装成PageResult返回
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findfindCheckGroupById(Integer id) {
        return checkGroupDao.findfindCheckGroupById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsById(Integer id) {
        List<Integer> list = checkGroupDao.findCheckItemIdsById(id);
        return list;
    }

    @Override
    public void editCheckGroup(CheckGroup checkGroup, Integer[] checkitemIds) {
        //编辑检查组分为三部分操作
        //1.根据id改t_checkgroup表
        checkGroupDao.editCheckGroup(checkGroup);
        //2.根据id删除t_checkgroup_checkitem表中的checkitem_id
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.deleteItemIdsById(checkGroupId);
        //3.根据id增加t_checkgroup_checkitem表中的checkitem_id

        Map<String,Integer> map = new HashMap<>();
        map.put("checkGroupId",checkGroupId);
        for (Integer checkItemId : checkitemIds) {
            map.put("checkItemId",checkItemId);
            checkGroupDao.setCheckGroupAndCheckItem(map);
        }
    }

    @Override
    public void deleteGroupById(Integer id) {
        //删除中间表中的数据
        checkGroupDao.deleteItemIdsById(id);
        //删除检查组表中的数据
        checkGroupDao.deleteGroupById(id);

    }

    @Override
    public List<CheckGroup> findAllGroup() {
        return checkGroupDao.findAllGroup();
    }


    private void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if (checkitemIds!=null){
            Map map = new HashMap();
            map.put("checkGroupId",checkGroupId);
            for (Integer checkItemId : checkitemIds) {
                map.put("checkItemId",checkItemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

}















