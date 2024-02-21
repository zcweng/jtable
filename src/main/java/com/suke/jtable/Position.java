package com.suke.jtable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Position {
    int y;
    int x;

    @Override
    public String toString() {
        return "[x:"+ x +",y:"+ y +"]";
    }

    public Position offset(Size size) {
        return new Position(y + size.height, x + size.width);
    }

    public Position offsetY(int dy) {
        return new Position(y + dy, x);
    }
}
