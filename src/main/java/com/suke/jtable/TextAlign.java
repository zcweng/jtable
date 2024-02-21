package com.suke.jtable;

/**
 * @author changwen.zhou
 * @date 2024/2/19
 */
public enum TextAlign {
    TOP_LEFT(FLAG.TOP | FLAG.LEFT), TOP_CENTER(FLAG.TOP | FLAG.MIDDLE), TOP_RIGHT(FLAG.TOP | FLAG.RIGHT),
    CENTER_LEFT(FLAG.CENTER | FLAG.LEFT), CENTER(FLAG.CENTER | FLAG.MIDDLE), CENTER_RIGHT(FLAG.CENTER | FLAG.RIGHT),
    BOTTOM_LEFT(FLAG.BOTTOM | FLAG.LEFT), BOTTOM_CENTER(FLAG.BOTTOM | FLAG.MIDDLE), BOTTOM_RIGHT(FLAG.BOTTOM | FLAG.RIGHT),
    ;
    final int flag;

    TextAlign(int flag) {
        this.flag = flag;
    }

    public boolean match(int flag) {
        return (this.flag & flag) != 0;
    }

    interface FLAG {
        int TOP = 1, BOTTOM = (1<<1), CENTER = (1<<2);
        int LEFT = (1<<3), RIGHT = (1<<4), MIDDLE = (1<<5);
    }
}
