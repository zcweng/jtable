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
        GraphicsEnv.env = new SkijaGraphicsEnv();
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
    public TextBounds getTextBounds(String text, CellStyle style) {
        final SkijaFont font = (SkijaFont) style.getFont();
        final io.github.humbleui.skija.Font value = font.getValue();
        final Rect rect = value.measureText(text);
        final FontMetrics metrics = value.getMetrics();

        return new TextBounds((int)rect.getLeft(), (int)rect.getTop(), (int)rect.getRight(), (int)rect.getBottom(),
                (int)metrics.getAscent(), (int)metrics.getDescent());
    }
}
