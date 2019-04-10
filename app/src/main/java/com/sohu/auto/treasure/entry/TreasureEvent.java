package com.sohu.auto.treasure.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aiyou on 2019/4/10.
 */

public class TreasureEvent implements Serializable{

    public String title;

    public String password;

    public List<Treasure> treasures;
}
