package com.joy.app.bean.hotel;

import java.io.Serializable;

/**
 * @author litong  <br>
 * @Description XXXXXX    <br>
 */
public class FilterItems implements Serializable {

    String name;
    int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "name:"+ name+" value:"+value;
    }
}
