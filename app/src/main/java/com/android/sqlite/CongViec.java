package com.android.sqlite;

public class CongViec {
    private int Id;
    private String TenCV;

    public CongViec(int id, String tenCV) {
        Id = id;
        TenCV = tenCV;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String tenCV) {
        TenCV = tenCV;
    }
}
