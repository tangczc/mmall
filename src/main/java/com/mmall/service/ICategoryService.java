package com.mmall.service;

import com.mmall.common.ServerRespons;

public interface ICategoryService {
    ServerRespons addCategory(String categoryName, Integer parantId);
    ServerRespons updateCategory(Integer categoryId,String categoryName);

}
