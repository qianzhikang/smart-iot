package com.qzk.user.service.impl;

import com.qzk.common.exception.ApiException;
import com.qzk.common.result.RestResult;
import com.qzk.user.minio.MinioProperties;
import com.qzk.user.minio.MinioUtil;
import com.qzk.user.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 上传服务接口实现类
 * @Date 2023-02-03-11-38
 * @Author qianzhikang
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Resource
    private MinioProperties minioProperties;

    private final String AVATAR_PATH_PREFIX = "avatar/";
    /**
     * 上传头像
     *
     * @param avatar 头像文件
     * @return
     */
    @Override
    public RestResult<Object> uploadAvatar(MultipartFile avatar) {
        // 文件目录 + 文件名（随机uuid + 时间戳）
        String fileName = AVATAR_PATH_PREFIX + UUID.randomUUID() + System.currentTimeMillis() + avatar.getOriginalFilename();
        try {
            MinioUtil.createBucket(minioProperties.getBucket());
            MinioUtil.uploadFile(minioProperties.getBucket(), avatar, fileName);
            String url = MinioUtil.getPreSignedObjectUrl(minioProperties.getBucket(), fileName);
            Map<String,String> result = new HashMap<>();
            result.put("img",url);
            return new RestResult<>().success("上传成功",result);
        } catch (Exception e) {
            throw new ApiException("图片上传失败");
        }
    }
}
