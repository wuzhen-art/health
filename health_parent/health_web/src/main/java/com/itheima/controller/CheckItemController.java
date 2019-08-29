package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @ LYK
 * @ 检查项业务
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * @ LYK
     * @ 增加检查项
     * @ param checkItem
     * @ return result
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){

        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 查询检查项
     * @ param string
     * @ return PageResult
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        PageResult pageResult = checkItemService.findPage(queryPageBean);

        return pageResult;
    }

    /**
     * @ LYK
     * @ 删除检查项
     * @ param int
     * @ return result
     */
    @RequestMapping("/delete")
    public Result deleteItemById(Integer id){
        try {
            int i = checkItemService.deleteItemById(id);
            if (i==1) {
                //删除成功
                return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
            }else {
                return new Result(false,"删除失败:该检查项已被引用,不可删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //服务器异常,返回前端删除失败
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 编辑检查项
     * @ param checkItem
     * @ return result
     */
    @RequestMapping("/editItem")
    public Result editItem(@RequestBody CheckItem checkItem){
        System.out.println("checkItem = " + checkItem);

        try {
            checkItemService.editItem(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 回显检查项
     * @ param
     * @ return result
     */
    @RequestMapping("/findAll")
    public Result findAllItems(){
        try {
            List<CheckItem> list = checkItemService.findAllItems();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
































