package com.suke.jtable.graphics.awt;

import com.suke.jtable.Table;
import com.suke.jtable.graphics.Font;
import com.suke.jtable.graphics.FontStyle;
import lombok.SneakyThrows;

import java.net.URL;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public class AwtFont implements Font {
    public static AwtFont DEFAULT = loadFont();

    final java.awt.Font font;

    private AwtFont(java.awt.Font font) {
        this.font = font;
    }

    private AwtFont(String name, int style, float size) {
        this(name, style, (int)(size + 0.5));
    }

    private AwtFont(String name, int style, int size) {
        this(new java.awt.Font(name, style, size));
    }

    @Override
    public String getName() {
        return font.getName();
    }

    @Override
    public FontStyle getStyle() {
        return mapping(font.getStyle());
    }

    @Override
    public int getSize() {
        return font.getSize();
    }

    @Override
    public int getSpacing() {
        return (int) (font.getSize()*0.2);
    }

    @Override
    public Font deriveFontName(String name) {
        return new AwtFont(name, mapping(getStyle()), getSize());
    }

    @Override
    public Font deriveFontStyle(FontStyle style) {
        return new AwtFont(getName(), mapping(style), getSize());
    }

    @Override
    public Font deriveFontSize(float size) {
        return new AwtFont(getName(), mapping(getStyle()), size);
    }

    @SneakyThrows
    private static AwtFont loadFont() {
        final URL resource = Table.class.getClassLoader().getResource("Microsoft Yahei.ttf");
        final java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, resource.openStream());
        return (AwtFont)new AwtFont(font)
                .deriveFontStyle(FontStyle.PLAIN)
                .deriveFontSize(14);
    }

    public java.awt.Font getValue() {
        return font;
    }

    private int mapping(FontStyle style) {
        switch (style) {
            case PLAIN:
                return java.awt.Font.PLAIN;
            case BOLD:
                return java.awt.Font.BOLD;
            case ITALIC:
                return java.awt.Font.ITALIC;
//            case BOLD_ITALIC:
//                return java.awt.Font.BOLD | java.awt.Font.ITALIC;
            default:
                throw new IllegalArgumentException("Unknown style: " + style);
        }
    }

    private FontStyle mapping(int style) {
        if (style == java.awt.Font.PLAIN) {
            return FontStyle.PLAIN;
        }
        if (style == java.awt.Font.BOLD) {
            return FontStyle.BOLD;
        }
        if (style == java.awt.Font.ITALIC) {
            return FontStyle.ITALIC;
        }
//        if (style == (java.awt.Font.BOLD | java.awt.Font.ITALIC)) {
//            return FontStyle.BOLD_ITALIC;
//        }
        throw new IllegalArgumentException("Unknown style: " + style);
    }
}
