package com.suke.jtable.graphics.skija;

import com.suke.jtable.Table;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.Font;
import com.suke.jtable.graphics.awt.AwtFont;
import io.github.humbleui.skija.FontMgr;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Typeface;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import static io.github.humbleui.skija.FontStyle.*;

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
    public int getStyle() {
        return mappingFontStyle(font.getTypeface().getFontStyle());
    }

    @Override
    public int getSize() {
        return (int) font.getSize();
    }

    @Override
    public com.suke.jtable.graphics.Font deriveFontName(String name) {
        Typeface face = FontMgr.getDefault().matchFamilyStyle(name, mappingFontStyle(getStyle()));
        Font font = new Font(face, getSize());
        return new SkijaFont(font);
    }

    @Override
    public com.suke.jtable.graphics.Font deriveFontStyle(int style) {
        Typeface face = FontMgr.getDefault().matchFamilyStyle(getName(), mappingFontStyle(style));
        Font font = new Font(face, getSize());
        return new SkijaFont(font);
    }

    @Override
    public com.suke.jtable.graphics.Font deriveFontSize(float size) {
        Typeface face = FontMgr.getDefault().matchFamilyStyle(getName(), mappingFontStyle(getStyle()));
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
//        Typeface face = Typeface.makeFromFile(resource.getFile());
        Font font = new Font(face, 14);
        return new SkijaFont(font);
//        return null;
    }

    private static FontStyle mappingFontStyle(int style) {
        switch (style) {
            case AwtFont.PLAIN:
                return FontStyle.NORMAL;
            case AwtFont.BOLD:
                return BOLD;
            case AwtFont.ITALIC:
                return ITALIC;
            case AwtFont.BOLD | AwtFont.ITALIC:
                return BOLD_ITALIC;
            default:
                throw new IllegalArgumentException("Invalid style");
        }
    }

    private static int mappingFontStyle(FontStyle style) {
        if (style == NORMAL) {
            return AwtFont.PLAIN;
        }
        if (style == BOLD) {
            return AwtFont.BOLD;
        }
        if (style == ITALIC) {
            return AwtFont.ITALIC;
        }
        if (style == BOLD_ITALIC) {
            return AwtFont.BOLD | AwtFont.ITALIC;
        }
        throw new IllegalArgumentException("Invalid style");
    }

    public Font getValue() {
        return font;
    }
}
