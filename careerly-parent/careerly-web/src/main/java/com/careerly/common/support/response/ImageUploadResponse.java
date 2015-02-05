/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.careerly.common.support.response;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 实现描述：图片上传返回信息
 */
public class ImageUploadResponse {
    private static final String imgHost = "http://img1.qunarzz.com";

    private Integer error;
    private String message;
    private String url; //全路径
    private String originalFileName; //图片的原始名称
    private String imgUrl; //相对路径

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static String getImgHost() {
        return imgHost;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
    public static ImageUploadResponse convertStandardJsonObject(StandardJsonObject standardJsonObject) {
        ImageUploadResponse uploadResponse = new ImageUploadResponse();
        List<Object> images = standardJsonObject.getData();
        Object image = images.get(0);
        JSONObject jsonObject = (JSONObject) image;
        if (standardJsonObject.getRet()) {
            uploadResponse.setUrl(imgHost+jsonObject.getString("img"));
            uploadResponse.setOriginalFileName(jsonObject.getString("src_file"));
            uploadResponse.setImgUrl(jsonObject.getString("img"));
            uploadResponse.setError(0);
            return uploadResponse;
        } else {
            uploadResponse.setMessage("errmsg");
            uploadResponse.setError(1);
            return uploadResponse;
        }
    }
}
