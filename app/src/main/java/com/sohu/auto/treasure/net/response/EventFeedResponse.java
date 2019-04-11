package com.sohu.auto.treasure.net.response;

import com.google.gson.annotations.SerializedName;
import com.sohu.auto.treasure.entry.EventFeed;

import java.util.List;

/**
 * Created by aiyou on 2019/4/11.
 */

public class EventFeedResponse {

    public List<EventFeed> data;

    public int code;

    @SerializedName("err_msg")
    public String errMsg;
}
