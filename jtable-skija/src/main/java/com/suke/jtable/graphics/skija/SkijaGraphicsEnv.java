package com.suke.jtable.graphics.skija;

import com.suke.jtable.CellStyle;
import com.suke.jtable.TextBounds;
import com.suke.jtable.graphics.Canvas;
import com.suke.jtable.graphics.Font;
import com.suke.jtable.graphics.GraphicsEnv;
import io.github.humbleui.skija.FontMetrics;
import io.github.humbleui.types.Rect;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public class SkijaGraphicsEnv extends GraphicsEnv {

    private SkijaGraphicsEnv(){}

    public static void load() {
        GraphicsEnv.setEnv(new SkijaGraphicsEnv());
    }


    @Override
    public Canvas createCanvas(int width, int height, int imageType) {
        return new SkijaCanvas(width, height);
    }

    @Override
    public Font getDefaultFont() {
        return SkijaFont.DEFAULT;
    }

    @Override
    public TextBounds measureTextBounds(String text, Font _font) {
        final SkijaFont font = (SkijaFont) _font;
        final io.github.humbleui.skija.Font value = font.getValue();
        final Rect rect = value.measureText(text);
        final FontMetrics metrics = value.getMetrics();

        com.suke.jtable.Rect rect1 = new com.suke.jtable.Rect(
                (int) rect.getLeft(), (int) rect.getTop(),
                (int) rect.getRight(), (int) rect.getBottom());
        rect1 = rect1.offset(0, rect1.height())
                .offset(0, -(int) metrics.getAscent())
                .offset(0, -(int) metrics.getDescent());

        return new TextBounds(rect1.getLeft(), rect1.getTop(), rect1.getRight(), rect1.getBottom(),
                (int)metrics.getAscent(), (int)metrics.getDescent());
    }
}
