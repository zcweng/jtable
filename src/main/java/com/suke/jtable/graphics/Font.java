package com.suke.jtable.graphics;

/**
 * @author changwen.zhou
 * @date 2024/2/21
 */
public interface Font {

    String getName();
    int getStyle();
    int getSize();

    Font deriveFontName(String name);
    Font deriveFontStyle(int style);
    Font deriveFontSize(float size);

}
