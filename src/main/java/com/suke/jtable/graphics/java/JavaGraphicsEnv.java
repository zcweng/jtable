package com.suke.jtable.graphics.java;

import com.suke.jtable.CellStyle;
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
public final class JavaGraphicsEnv extends GraphicsEnv {
    static Graphics2D GRAPHICS;

    @Override
    public Canvas createCanvas(int width, int height, int imageType) {
        return new JavaCanvas(new BufferedImage(width, height, imageType));
    }

    @Override
    public Font getDefaultFont() {
        return JavaFont.DEFAULT;
    }

    private void acquireGraphics() {
        if (Objects.nonNull(GRAPHICS)) {
            return;
        }
        GRAPHICS = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
    }

    @Override
    public TextBounds getTextBounds(String text, CellStyle style) {
        acquireGraphics();
        final FontMetrics fontMetrics = GRAPHICS.getFontMetrics((java.awt.Font) style.getFont());
        final Rectangle2D stringBounds = fontMetrics.getStringBounds(text, GRAPHICS);
        final int ascent = fontMetrics.getAscent();
        final int descent = fontMetrics.getDescent();

        return new TextBounds((int)stringBounds.getMinX(), (int)(stringBounds.getMinY()),
                (int)stringBounds.getMaxX(), (int)(stringBounds.getMaxY()), ascent, descent);
    }
}
