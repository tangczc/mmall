package com.mmall.service.impl;

import com.mmall.service.IFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @description: 文件上传实现类
 * @author: LiChen
 * @create: 2019-02-04 11:00
 */

@Service("iFileService")
public class FileServiceImpl implements IFileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 返回上传路径
     *
     * @param multipartFile
     * @param path
     * @return
     */
    public String upload(MultipartFile multipartFile, String path) {
        String fileName = multipartFile.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件，上传文件名:{},上传路径:{},新文件名字:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadFileName);

        try {
            //上传文件
            multipartFile.transferTo(targetFile);
            //todo 上传ftp 以后实现
//            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //todo 上传之后删除upload文件夹
//            targetFile.delete();
        }catch (IOException e){
            logger.error("上传文件异常");
            return null;
        }

        return targetFile.getName();
    }
}