package com.suke.jtable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConstraintWidth {
    int minWidth;
    int maxWidth;

    public static ConstraintWidth tight(int width) {
        assert width>0;
        return new ConstraintWidth(width, width);
    }

    public ConstraintWidth plus(ConstraintWidth constraint) {
        return new ConstraintWidth(minWidth + constraint.minWidth, maxWidth + constraint.maxWidth);
    }
}
