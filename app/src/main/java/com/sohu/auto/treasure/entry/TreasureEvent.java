package com.sohu.auto.treasure.entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aiyou on 2019/4/10.
 */

public class TreasureEvent implements Serializable{

    public String title;

    public String secret;

    @SerializedName("treasure_list")
    public List<Treasure> treasures;
}
