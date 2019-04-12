package com.sohu.auto.treasure.entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aiyou on 2019/4/10.
 */

public class Treasure implements Serializable {

    public String id;
    // 宝藏地点
    @SerializedName("address")
    public String locationStr;

    // 宝藏经纬度
    @SerializedName("location")
    public double[] locations = new double[2];

    // 宝藏文字描述
    public String content;

    // 宝藏图片的链接地址
    @SerializedName("image")
    public String imagePath;

    // 宝藏谜题题干
    public String question;

    // 宝藏谜题正确答案
    public String answer;
}
