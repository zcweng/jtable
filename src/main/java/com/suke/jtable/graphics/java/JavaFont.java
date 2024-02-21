package com.suke.jtable.graphics.java;

import com.suke.jtable.Table;
import com.suke.jtable.graphics.Font;
import lombok.SneakyThrows;

import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * @author changwen.zhou
 * @date 2024/2/21
 */
public class JavaFont extends java.awt.Font implements Font {
    public static JavaFont DEFAULT = loadFont();

    public JavaFont(String name, int style, float size) {
        super(name, style, (int)(size + 0.5));
    }

    public JavaFont(String name, int style, int size) {
        super(name, style, size);
    }

    public JavaFont(Map<? extends AttributedCharacterIterator.Attribute, ?> attributes) {
        super(attributes);
    }

    protected JavaFont(java.awt.Font font) {
        super(font);
    }

    @Override
    public Font deriveFontName(String name) {
        return new JavaFont(name, getStyle(), getSize());
    }

    @Override
    public Font deriveFontStyle(int style) {
        return new JavaFont(getName(), style, getSize());
    }

    @Override
    public Font deriveFontSize(float size) {
        return new JavaFont(getName(), getStyle(), size);
    }

    @SneakyThrows
    private static JavaFont loadFont() {
        final URL resource = Table.class.getClassLoader().getResource("Microsoft Yahei.ttf");
        final java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, resource.openStream());
        return (JavaFont)new JavaFont(font)
                .deriveFontStyle(java.awt.Font.PLAIN)
                .deriveFontSize(14);
    }

}
