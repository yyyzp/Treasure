package com.sohu.auto.treasure.entry;

import java.io.Serializable;

/**
 * Created by aiyou on 2019/4/10.
 */

public class TreasureHistory implements Serializable {

    // 宝藏id
    public long id;

    // 宝藏名字
    public String name;

    // 宝藏位置
    public String location;

    // 开启时间
    public long openTime;

    // 开启人姓名
    public String openMan;
}
