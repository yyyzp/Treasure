package com.sohu.auto.treasure.entry;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by aiyou on 2019/4/10.
 */

public class Treasure implements Serializable {

    // 宝藏地点
    public String location;

    // 宝藏维度
    public String latitude;

    // 宝藏经度
    public String longitude;

    // 宝藏文字描述
    public String text;

    // 宝藏图片内容
    public Uri image;

    // 宝藏图片的链接地址
    public String imagePath;

    // 宝藏谜题题干
    public String stem;

    // 宝藏谜题正确答案
    public String answer;
}
