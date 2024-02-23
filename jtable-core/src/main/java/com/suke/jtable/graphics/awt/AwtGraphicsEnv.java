package com.suke.jtable.graphics.awt;

import com.suke.jtable.CellStyle;
import com.suke.jtable.Rect;
import com.suke.jtable.TextBounds;
import com.suke.jtable.graphics.Canvas;
import com.suke.jtable.graphics.Font;
import com.suke.jtable.graphics.GraphicsEnv;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public final class AwtGraphicsEnv extends GraphicsEnv {
    static Graphics2D GRAPHICS;

    @Override
    public Canvas createCanvas(int width, int height, int imageType) {
        return new AwtCanvas(new BufferedImage(width, height, imageType));
    }

    @Override
    public Font getDefaultFont() {
        return AwtFont.DEFAULT;
    }

    private void acquireGraphics() {
        if (Objects.nonNull(GRAPHICS)) {
            return;
        }
        GRAPHICS = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
    }

    @Override
    public TextBounds measureTextBounds(String text, Font font) {
        acquireGraphics();
        final FontMetrics fontMetrics = GRAPHICS.getFontMetrics(((AwtFont)font).getValue());
        final Rectangle2D stringBounds = fontMetrics.getStringBounds(text, GRAPHICS);
        final int ascent = fontMetrics.getAscent();
        final int descent = fontMetrics.getDescent();

        Rect rect = new Rect(
                (int) stringBounds.getMinX(), (int) (stringBounds.getMinY()),
                (int) stringBounds.getMaxX(), (int) (stringBounds.getMaxY()));
        rect = rect.offset(0, ascent).offset(0, rect.height()).offset(0, -descent);

        return new TextBounds(rect.getLeft(), rect.getTop(), rect.getRight(), rect.getBottom(), ascent, descent);
    }
}
