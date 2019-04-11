package com.sohu.auto.treasure.entry;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aiyou on 2019/4/11.
 */

public class EventFeed {

    // 活动id
    public String id;

    // 创建时间
    @SerializedName("created_at")
    public String createdAt;

    // 创建者Id
    @SerializedName("creator_id")
    public String creatorId;

    // 活动名称
    public String title;

    // 活动密码
    public String secrete;

    // 宝藏ids
    public List<String> boxIds;
}
