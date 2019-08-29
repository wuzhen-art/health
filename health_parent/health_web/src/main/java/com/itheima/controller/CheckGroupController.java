package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

import java.util.List;


/**
 * @ LYK
 * @ 检查组业务
 *
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * @ LYK
     * @ 增加检查组
     * @ param CheckGroup,integer[]
     * @ return result
     */
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 查询所有检查组
     * @ param string
     * @ return PageResult
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.findPage(queryPageBean);
    }

    /**
     * @ LYK
     * @ 根据id查找检查组
     * @ param integer
     * @ return result
     */
    @RequestMapping("/findById")
    public Result findCheckGroupById(Integer id){
        try {
            CheckGroup checkGroup = checkGroupService.findfindCheckGroupById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 根据检查组id查询检查项
     * @ param integer
     * @ return result
     */
    @RequestMapping("/findCheckItemIdsById")
    public Result findCheckItemIdsById(Integer id){
        try {
            List<Integer> list = checkGroupService.findCheckItemIdsById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 编辑检查组
     * @ param CheckGroup,integer[]
     * @ return result
     */
    @RequestMapping("/edit")
    public Result editCheckGroup(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        System.out.println("checkitemIds = " + checkitemIds);

        try {
            checkGroupService.editCheckGroup(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 根据id删除检查组
     * @ param integer
     * @ return result
     */
    @RequestMapping("/delete")
    public Result deleteGroupById(Integer id){
        try {
            checkGroupService.deleteGroupById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    /**
     * @ LYK
     * @ 查询所有检查组
     * @ param
     * @ return result
     */
    @RequestMapping("/findAll")
    public Result findAllGroup(){
        try {
            List<CheckGroup> list = checkGroupService.findAllGroup();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }


}
