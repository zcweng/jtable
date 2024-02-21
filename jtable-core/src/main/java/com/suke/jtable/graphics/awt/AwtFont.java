package com.suke.jtable.graphics.awt;

import com.suke.jtable.Table;
import com.suke.jtable.graphics.Font;
import lombok.SneakyThrows;

import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public class AwtFont extends java.awt.Font implements Font {
    public static AwtFont DEFAULT = loadFont();

    public AwtFont(String name, int style, float size) {
        super(name, style, (int)(size + 0.5));
    }

    public AwtFont(String name, int style, int size) {
        super(name, style, size);
    }

    public AwtFont(Map<? extends AttributedCharacterIterator.Attribute, ?> attributes) {
        super(attributes);
    }

    protected AwtFont(java.awt.Font font) {
        super(font);
    }

    @Override
    public Font deriveFontName(String name) {
        return new AwtFont(name, getStyle(), getSize());
    }

    @Override
    public Font deriveFontStyle(int style) {
        return new AwtFont(getName(), style, getSize());
    }

    @Override
    public Font deriveFontSize(float size) {
        return new AwtFont(getName(), getStyle(), size);
    }

    @SneakyThrows
    private static AwtFont loadFont() {
        final URL resource = Table.class.getClassLoader().getResource("Microsoft Yahei.ttf");
        final java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, resource.openStream());
        return (AwtFont)new AwtFont(font)
                .deriveFontStyle(java.awt.Font.PLAIN)
                .deriveFontSize(14);
    }

}
