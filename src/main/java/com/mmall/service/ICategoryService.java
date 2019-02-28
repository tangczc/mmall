package com.mmall.service;

import com.mmall.common.ServerRespons;
import com.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {
    ServerRespons addCategory(String categoryName, Integer parantId);
    ServerRespons updateCategory(Integer categoryId,String categoryName);
    ServerRespons<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerRespons<List<Integer>> selectCategoryAndDeepChildrenCategory(Integer categoryId);

}
