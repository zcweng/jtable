package com.suke.jtable.graphics.skija;

import com.suke.jtable.Table;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.FontSlant;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Typeface;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.URL;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public class SkijaFont implements com.suke.jtable.graphics.Font {
    public static SkijaFont DEFAULT = loadFont();
    final Font font;

    private SkijaFont(Font font) {
        this.font = font;
        assert font != null;
    }

    @Override
    public String getName() {
        return font.getTypeface().getFamilyName();
    }

    @Override
    public com.suke.jtable.graphics.FontStyle getStyle() {
        return mapping(font.getTypeface().getFontStyle());
    }

    @Override
    public int getSize() {
        return (int) font.getSize();
    }

    @Override
    public com.suke.jtable.graphics.Font deriveFontName(String name) {
        Typeface face = FontMgr.getDefault().matchFamilyStyle(name, mapping(getStyle()));
        Font font = new Font(face, getSize());
        return new SkijaFont(font);
    }

    @Override
    public com.suke.jtable.graphics.Font deriveFontStyle(com.suke.jtable.graphics.FontStyle style) {
        Typeface face = FontMgr.getDefault().matchFamilyStyle(getName(), mapping(style));
        Font font = new Font(face, getSize());
        return new SkijaFont(font);
    }

    @Override
    public com.suke.jtable.graphics.Font deriveFontSize(float size) {
        Typeface face = FontMgr.getDefault().matchFamilyStyle(getName(), mapping(getStyle()));
        Font font = new Font(face, size);
        return new SkijaFont(font);
    }

    @SneakyThrows
    private static SkijaFont loadFont() {
        final URL resource = Table.class.getClassLoader().getResource("Microsoft Yahei.ttf");
        System.out.println(resource.getFile());
        final InputStream inputStream = resource.openStream();
        // inputStream 转为 ByteBuffer
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        final Data data = Data.makeFromBytes(buffer);
        Typeface face = Typeface.makeFromData(data);
        Font font = new Font(face, 14);
        return new SkijaFont(font);
    }

    public Font getValue() {
        return font;
    }

    private com.suke.jtable.graphics.FontStyle mapping(FontStyle style) {
        final FontSlant slant = style.getSlant();
        final int weight = style.getWeight();
        if (slant == FontSlant.ITALIC && weight >= FontStyle.BOLD.getWeight()) {
            return com.suke.jtable.graphics.FontStyle.BOLD_ITALIC;
        }
        if (slant == FontSlant.ITALIC) {
            return com.suke.jtable.graphics.FontStyle.ITALIC;
        }
        if (weight >= FontStyle.BOLD.getWeight()) {
            return com.suke.jtable.graphics.FontStyle.BOLD;
        }
        return com.suke.jtable.graphics.FontStyle.PLAIN;
    }

    private FontStyle mapping(com.suke.jtable.graphics.FontStyle style) {
        switch (style) {
            case PLAIN:
                return FontStyle.NORMAL;
            case BOLD:
                return FontStyle.BOLD;
            case ITALIC:
                return FontStyle.ITALIC;
            case BOLD_ITALIC:
                return FontStyle.BOLD_ITALIC;
            default:
                throw new IllegalStateException("Unexpected value: " + style);
        }
    }
}
