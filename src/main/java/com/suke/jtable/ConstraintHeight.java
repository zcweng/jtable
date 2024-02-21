package com.suke.jtable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConstraintHeight {
    int minHeight;
    int maxHeight;

    public static ConstraintHeight tight(int height) {
        assert height>0;
        return new ConstraintHeight(height, height);
    }

    public ConstraintHeight plus(ConstraintHeight constraint) {
        return new ConstraintHeight(minHeight + constraint.minHeight, maxHeight + constraint.maxHeight);
    }
}
