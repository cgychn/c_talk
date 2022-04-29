package com.cloud.c_talk.im.dto;

import java.util.List;

public class Page<T> {

    private List<T> list;

    private long size;

    public Page(List<T> list, long size) {
        this.list = list;
        this.size = size;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
