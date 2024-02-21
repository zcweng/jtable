package com.suke.jtable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * @author changwen.zhou
 * @date 2024/2/19
 */
public class CellStyle extends Font {
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Color color = Color.BLACK;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private TextAlign textAlign = TextAlign.CENTER_LEFT;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    Border border = Border.NONE;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Color backgroundColor = null;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Rect padding;

    public CellStyle(String name, int style, int size) {
        super(name, style, size);
    }

    public CellStyle(Map<? extends AttributedCharacterIterator.Attribute, ?> attributes) {
        super(attributes);
    }

    public CellStyle(Font font) {
        super(font);
    }

    public CellStyle(CellStyle font) {
        super(font);
        copy(font);
    }

    public CellStyle(CellStyle font, String name) {
        super(new Font(name, font.style, font.size));
        copy(font);
    }

    public CellStyle(CellStyle font, int style) {
        super(font.deriveFont(style));
        copy(font);
    }

    public CellStyle(CellStyle font, float size) {
        super(font.deriveFont(size));
        copy(font);
    }

    public CellStyle(CellStyle font, Color color) {
        super(new Font(font.getName(), font.getStyle(), font.getSize()));
        copy(font);
        setColor(color);
    }

    public CellStyle(CellStyle font, Rect padding) {
        super(new Font(font.getName(), font.getStyle(), font.getSize()));
        copy(font);
        setPadding(padding);
    }

    public CellStyle(CellStyle font, Border border) {
        super(new Font(font.getName(), font.getStyle(), font.getSize()));
        copy(font);
        setBorder(border);
    }

    private void copy(CellStyle font) {
        setColor(font.getColor());
        setTextAlign(font.getTextAlign());
        setBorder(font.getBorder());
        setBackgroundColor(font.getBackgroundColor());
        setPadding(font.getPadding());
    }

    public Font getFont() {
        return this;
    }
}
