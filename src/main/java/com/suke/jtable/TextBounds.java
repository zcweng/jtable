package com.suke.jtable;

import lombok.Getter;

/**
 * @author changwen.zhou
 * @date 2024/2/19
 */
@Getter
public class TextBounds extends Rect {
    protected final int ascent;
    protected final int descent;

    public TextBounds() {
        this(0, 0, 0, 0, 0, 0);
    }
    public TextBounds(int left, int top, int right, int bottom, int ascent, int descent) {
        super(left, top, right, bottom);
        this.ascent = ascent;
        this.descent = descent;
    }

//    public static TextBounds offset(Position offset) {
//        return new Rect(left + offset.x, top + offset.y, right + offset.x, bottom + offset.y);
//    }

}
