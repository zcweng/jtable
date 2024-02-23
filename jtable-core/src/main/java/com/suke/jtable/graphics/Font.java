package com.suke.jtable.graphics;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public interface Font {

    String getName();
    FontStyle getStyle();
    int getSize();
    int getSpacing();

    Font deriveFontName(String name);
    Font deriveFontStyle(FontStyle style);
    Font deriveFontSize(float size);

}
