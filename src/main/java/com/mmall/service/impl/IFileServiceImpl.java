package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Emily on 2018/12/15.
 */
@Service("iFileService")
public class IFileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(IFileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path){
        String filename = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = filename.substring(filename.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"." + fileExtensionName;
        logger.info("开始上传文件，上传文件的文件名:{},上传的路径:{},新文件名:{}", filename, path, uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);

        try {
            file.transferTo(targetFile);
            // 文件上传成功
            //todo 将targetFile上传到我们的FTP服务器上
            FTPUtil.uploadFile(Lists.<File>newArrayList(targetFile));
            //todo 上传完之后，删除upload下面的文件
            targetFile.delete();
        } catch (IOException e){
            return null;
        }
        return targetFile.getName();
    }
}
