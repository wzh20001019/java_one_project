package com.xiaowen.controller;

import com.xiaowen.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    @GetMapping("/download")
    public Result<String> downLoad(String name, HttpServletResponse response) {
        try {
            // 输入流 读取文件数据
            FileInputStream fileInputStream = new FileInputStream(BasePath + name);

            // 输出流返回数据
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];

            while((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);

                outputStream.flush();
            }

            fileInputStream.close();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
