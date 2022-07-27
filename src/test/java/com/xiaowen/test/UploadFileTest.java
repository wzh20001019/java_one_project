package com.xiaowen.test;

import org.junit.jupiter.api.Test;

public class UploadFileTest {
    @Test
    public void upload() {
        String fileName = "asdxde.png";

        String suffix = fileName.substring(fileName.lastIndexOf("."));

        System.out.println(suffix);

    }
}
