package com.suke.jtable;

import com.suke.jtable.graphics.FontStyle;

import java.awt.*;
import java.util.Objects;

/**
 * @author zcweng
 * @date 2024/2/19
 */
public interface CellStyleDelegate {

    void applyStyle(CellStyle font);
    CellStyle getMyStyle();

    default CellStyleDelegate getParent() {
        return null;
    }

    default CellStyle getParentStyle() {
        CellStyleDelegate parent = getParent();
        if (Objects.isNull(parent)) {
            return null;
        }
        final CellStyle style = parent.getMyStyle();
        if (Objects.nonNull(style)) {
            return style;
        }
        return parent.getParentStyle();
    }

    default CellStyle findStyle() {
        CellStyle style = getMyStyle();
        if (Objects.nonNull(style)) {
            return style;
        }
        style = getParentStyle();
        if (Objects.nonNull(style)) {
            return style;
        }
        return CellStyle.DEFAULT_STYLE;
    }

    default CellStyleDelegate setFontStyle(FontStyle style) {
        CellStyle font = findStyle();
        applyStyle(font.setFontStyle(style));
        return this;
    }

    default CellStyleDelegate setFontSize(float size) {
        CellStyle style = findStyle();
        applyStyle(style.setFontSize(size));
        return this;
    }

    default CellStyleDelegate setFontName(String name) {
        CellStyle style = findStyle();
        applyStyle(style.setFontName(name));
        return this;
    }

    default CellStyleDelegate setFontColor(Color color) {
        CellStyle style = findStyle();
        applyStyle(style.setColor(color));
        return this;
    }

    default CellStyleDelegate setCellPadding(Rect padding) {
        CellStyle style = findStyle();
        applyStyle(style.setPadding(padding));
        return this;
    }

    default CellStyleDelegate setBorder(Border border) {
        CellStyle style = findStyle();
        applyStyle(style.setBorder(border));
        return this;
    }

    default CellStyleDelegate setCellBackground(Color color) {
        CellStyle style = findStyle();
        applyStyle(style.setBackgroundColor(color));
        return this;
    }

    default CellStyleDelegate setTextAlign(TextAlign textAlign) {
        CellStyle style = findStyle();
        applyStyle(style.setTextAlign(textAlign));
        return this;
    }
}
