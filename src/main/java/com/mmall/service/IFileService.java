package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件上传
 * @author: LiChen
 * @create: 2019-02-04 10:59
 */
public interface IFileService {
    String upload(MultipartFile multipartFile, String path);
}
