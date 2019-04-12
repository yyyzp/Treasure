package com.sohu.auto.treasure.entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aiyou on 2019/4/10.
 */

public class TreasureHistory implements Serializable {

    // 宝藏id
    public String id;

    // 宝藏创建时间
    @SerializedName("created_at")
    public String createdAt;

    // 宝藏更新时间
    @SerializedName("updated_at")
    public String updatedAt;

    // 宝藏开启状态 0 未开启 1 开启
    public int status;

    // 宝藏地点
    @SerializedName("address")
    public String locationStr;

    // 宝藏经纬度
    @SerializedName("location")
    public double[] locations = new double[2];

    // 找到者id
    @SerializedName("hunter_id")
    public String hunterId;

    // 找到者名字
    @SerializedName("hunter")
    public String hunterName;

    // 宝藏文字描述
    public String content;

    // 宝藏图片的链接地址
    @SerializedName("image")
    public String imagePath;

    // 宝藏谜题题干
    public String question;

    // 宝藏谜题正确答案
    public String answer;

    // 活动id
    @SerializedName("parent_id")
    public String parentId;

    // 创建者id
    @SerializedName("creator_id")
    public String creatorId;

    public Treasure convertToTreasure() {
        // 只包含了关键信息
        Treasure treasure = new Treasure();
        treasure.id = this.id;
        treasure.content = this.content;
        treasure.imagePath = this.imagePath;
        return treasure;
    }
}
