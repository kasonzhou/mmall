package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Emily on 2018/12/15.
 */
public interface IFileService {
    public String upload(MultipartFile file, String path);
}
