package com.suke.jtable;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public class Border {
    public static final Border NONE = new Border(0, Color.BLACK, 0);

    private final int width;
    private final Color color;
    private final int radius;

}
