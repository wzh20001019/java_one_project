package com.xiaowen.controller;

import com.xiaowen.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file/handler")
public class FileHandlerController {
    @Value("${rg.path}")
    private String BasePath;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info(file.toString());

        String originalFilename = file.getOriginalFilename();

        // 获取图片后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建一个目录
        File dir = new File(BasePath);

        // 判断目录是否存在
        if(!dir.exists()) {
            // 目录不存在, 需要创建目录
            dir.mkdirs();
        }

        try {
            // 临时文件转存到指定位置
            file.transferTo(new File(BasePath + fileName));

            return Result.success("上传成功");
        } catch (IOException e) {
            e.printStackTrace();

            return Result.error("上传失败");
        }
    }
}
