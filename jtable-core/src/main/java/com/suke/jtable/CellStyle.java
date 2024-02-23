package com.suke.jtable;

import com.suke.jtable.graphics.Font;
import com.suke.jtable.graphics.FontStyle;
import com.suke.jtable.graphics.awt.AwtGraphicsEnv;
import lombok.Getter;

import java.awt.*;

/**
 * @author zcweng
 * @date 2024/2/19
 */
@Getter
public class CellStyle {
    public static final CellStyle DEFAULT_STYLE;
    static {
        DEFAULT_STYLE = new CellStyle();
        DEFAULT_STYLE.font = AwtGraphicsEnv.getGraphicsEnv().getDefaultFont();
        DEFAULT_STYLE.color = Color.BLACK;
        DEFAULT_STYLE.textAlign = TextAlign.CENTER_LEFT;
        DEFAULT_STYLE.border = Border.NONE;
        DEFAULT_STYLE.backgroundColor = null;
        DEFAULT_STYLE.padding = new Rect(0, 0, 0, 0);
        DEFAULT_STYLE.textWrap = TextWrap.NO_WRAP;
    }

    private Font font;
    private Color color;
    private TextAlign textAlign;
    private Border border;
    private Color backgroundColor;
    private Rect padding;
    private TextWrap textWrap;

    private CellStyle(){}

    public CellStyle(CellStyle font) {
        copy(font);
    }

    private void copy(CellStyle style) {
        assert style != null;
        font = (style.getFont());
        color = (style.getColor());
        textAlign = (style.getTextAlign());
        border = (style.getBorder());
        backgroundColor = (style.getBackgroundColor());
        padding = (style.getPadding());
        textWrap = (style.getTextWrap());
    }

    public CellStyle setFontName(String name) {
        final CellStyle cellStyle = new CellStyle(this);
        cellStyle.font = (font.deriveFontName(name));
        return cellStyle;
    }

    public CellStyle setFontStyle(FontStyle style) {
        final CellStyle cellStyle = new CellStyle(this);
        cellStyle.font = (font.deriveFontStyle(style));
        return cellStyle;
    }

    public CellStyle setFontSize(float size) {
        final CellStyle cellStyle = new CellStyle(this);
        cellStyle.font = (font.deriveFontSize(size));
        return cellStyle;
    }

    public CellStyle setColor(Color color) {
        final CellStyle cellStyle = new CellStyle(this);
        cellStyle.color = (color);
        return cellStyle;
    }

    public CellStyle setTextAlign(TextAlign textAlign) {
        final CellStyle cellStyle = new CellStyle(this);
        cellStyle.textAlign = (textAlign);
        return cellStyle;
    }

    public CellStyle setBorder(Border border) {
        final CellStyle cellStyle = new CellStyle(this);
        cellStyle.border = (border);
        return cellStyle;
    }

    public CellStyle setBackgroundColor(Color color) {
        final CellStyle cellStyle = new CellStyle(this);
        cellStyle.backgroundColor = (color);
        return cellStyle;
    }

    public CellStyle setPadding(Rect padding) {
        final CellStyle cellStyle = new CellStyle(this);
        cellStyle.padding = (padding);
        return cellStyle;
    }


}
