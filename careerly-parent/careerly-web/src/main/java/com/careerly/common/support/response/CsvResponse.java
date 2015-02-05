/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.careerly.common.support.response;

import java.util.List;

/**
 * 实现描述：csv导出文件DTO
 */
public class CsvResponse {

    private List<List<String>> body; // row x col
    private String fileName;
    private List<String> head;

    public List<List<String>> getBody() {
        return body;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getHead() {
        return head;
    }

    public void setBody(List<List<String>> body) {
        this.body = body;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setHead(List<String> head) {
        this.head = head;
    }

}
