package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = CheckItemService.class)//interfaceClass = CheckItemService.class开启事务,指定接口代理对象为注入dubbo注册中心中的该实现类
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {

        checkItemDao.add(checkItem);

    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //1.调用MyBatis分页插件方法(自动帮我们做分页)
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        //2.调用Dao
        Page<CheckItem> page = checkItemDao.selectConditions(queryPageBean.getQueryString());

        PageResult pageResult = new PageResult(page.getTotal(),page.getResult());

        return pageResult;
    }

    @Override
    public int deleteItemById(Integer checkitem_id) {
        int i = checkItemDao.findCheckGroupById(checkitem_id);

        //int a=10/0;

        if (i>0) {
            return 0;
        }else {
            checkItemDao.deleteItemById(checkitem_id);
            return 1;
        }

    }

    @Override
    public void editItem(CheckItem checkItem) {
        System.out.println("checkItem = " + checkItem);
        checkItemDao.editItem(checkItem);
    }

    @Override
    public List<CheckItem> findAllItems() {

        return checkItemDao.findAllItems();
    }
}
