package com.qzk.user.service;

import com.qzk.common.result.RestResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description 上传服务
 * @Date 2023-02-03-11-37
 * @Author qianzhikang
 */
public interface UploadService {
    /**
     * 上传头像
     * @param avatar 头像文件
     * @return
     */
    RestResult<Object> uploadAvatar(MultipartFile avatar);
}
