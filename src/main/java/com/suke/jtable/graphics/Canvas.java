package com.suke.jtable.graphics;

import java.awt.*;
import java.io.OutputStream;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public interface Canvas {
    void setColor(Color color);

    void fillRect(int x, int y, int width, int height);

    void drawRect(int x, int y, int width, int height);

    void drawString(String text, int x, int y);

    void setStrokeWidth(int width);

    void setFont(Font font);

    default void dispose() {}

    void write(String png, OutputStream os);

    default void flush() {}

}
