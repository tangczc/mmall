package com.mmall.service.impl;

import com.mmall.common.ServerRespons;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ICategoryService")
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    public ServerRespons addCategory(String categoryName,Integer parantId){
        if(parantId == null || StringUtils.isBlank(categoryName)){
            return ServerRespons.createByErrorMessage("添加品类错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parantId);
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerRespons.createBySuccessMessage("添加品类成功");
        }
        return ServerRespons.createByErrorMessage("添加品类错误");
    }

    public ServerRespons updateCategory(Integer categoryId,String categoryName){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return  ServerRespons.createByErrorMessage("更新参数错误");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerRespons.createBySuccessMessage("更新品类名字成功");
        }
        return ServerRespons.createByErrorMessage("更新品类名字失败");

    }

}
