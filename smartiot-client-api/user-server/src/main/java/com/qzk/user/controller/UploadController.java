package com.qzk.user.controller;

import com.qzk.common.result.RestResult;
import com.qzk.user.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Description 文件上传控制器
 * @Date 2023-02-03-11-33
 * @Author qianzhikang
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/avatar")
    public RestResult<Object> uploadAvatar(@RequestParam(name = "avatar") MultipartFile avatar){
        return uploadService.uploadAvatar(avatar);
    }
}
