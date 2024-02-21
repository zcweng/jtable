package com.suke.jtable;

import java.awt.*;
import java.util.Objects;

/**
 * @author changwen.zhou
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
        final CellStyle font = parent.getMyStyle();
        if (Objects.nonNull(font)) {
            return font;
        }
        return parent.getParentStyle();
    }

    default CellStyle findStyle() {
        CellStyle font = getMyStyle();
        if (Objects.nonNull(font)) {
            return font;
        }
        font = getParentStyle();
        if (Objects.nonNull(font)) {
            return font;
        }
        return Table.DEFAULT_STYLE;
    }

    default CellStyleDelegate setFontStyle(int style) {
        CellStyle font = findStyle();
        applyStyle(new CellStyle(font, style));
        return this;
    }

    default CellStyleDelegate setFontSize(float size) {
        CellStyle font = findStyle();
        applyStyle(new CellStyle(font, size));
        return this;
    }

    default CellStyleDelegate setFontName(String name) {
        CellStyle font = findStyle();
        applyStyle(new CellStyle(font, name));
        return this;
    }

    default CellStyleDelegate setFontColor(Color color) {
        CellStyle font = findStyle();
        applyStyle(new CellStyle(font, color));
        return this;
    }

    default CellStyleDelegate setCellPadding(Rect padding) {
        CellStyle font = findStyle();
        applyStyle(new CellStyle(font, padding));
        return this;
    }

    default CellStyleDelegate setBorder(Border border) {
        CellStyle font = findStyle();
        applyStyle(new CellStyle(font, border));
        return this;
    }

    default CellStyleDelegate setCellBackground(Color color) {
        CellStyle font = findStyle();
        final CellStyle style = new CellStyle(font);
        style.setBackgroundColor(color);
        applyStyle(style);
        return this;
    }

    default CellStyleDelegate setTextAlign(TextAlign textAlign) {
        CellStyle font = findStyle();
        final CellStyle style = new CellStyle(font);
        style.setTextAlign(textAlign);
        applyStyle(style);
        return this;
    }
}
