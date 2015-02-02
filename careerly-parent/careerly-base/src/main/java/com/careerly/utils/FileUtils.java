/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.careerly.utils;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * 实现描述：文件工具类
 * 
 * @author simon
 * @version v1.0.0
 * @see
 * @since 2013-12-7 下午5:26:58
 */
public class FileUtils {

    /**
     * 创建临时文件，JVM关闭时删除文件
     */
    public static File createTempFile(String suffix) throws IOException {
        final File tempFile = File.createTempFile("temp", suffix, null);
        tempFile.deleteOnExit();
        return tempFile;
    }

    public static File touch(String name) throws IOException {
        String tmpdir = System.getProperty("java.io.tmpdir");
        File tmpfile = new File(tmpdir, name);
        Files.touch(tmpfile);
        return tmpfile;
    }

}
