package com.suke.jtable.graphics.skija;

import com.suke.jtable.graphics.Canvas;
import com.suke.jtable.graphics.Font;
import io.github.humbleui.skija.Data;
import io.github.humbleui.skija.EncodedImageFormat;
import io.github.humbleui.skija.EncoderPNG;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.types.Rect;
import lombok.SneakyThrows;

//import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public class SkijaCanvas implements Canvas {
    final io.github.humbleui.skija.Canvas canvas;
    final Surface surface;
    final Paint paint;
    private SkijaFont font;

    protected SkijaCanvas(int width, int height) {
        this.surface = Surface.makeRasterN32Premul(width, height);
        this.canvas = surface.getCanvas();
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    public void setColor(java.awt.Color color) {
        paint.setColor(color.getRGB());
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        canvas.save();
        canvas.clipRect(new Rect(x, y, x + width, y + height), true);
        canvas.drawColor(paint.getColor());
        canvas.restore();
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        canvas.drawRect(new Rect(x, y, x + width, y + height), paint);
    }

    @Override
    public void drawString(String text, int x, int y) {
        assert font != null;
        canvas.drawString(text, x, y, font.getValue(), paint);
    }

    @Override
    public void setStrokeWidth(int width) {
        paint.setStrokeWidth(width);
    }

    @Override
    public void setFont(Font font) {
        assert font != null;
        assert font instanceof SkijaFont;
        this.font = (SkijaFont) font;
    }

    @Override
    public void dispose() {
        Canvas.super.dispose();
    }

    @SneakyThrows
    @Override
    public void write(String png, OutputStream os) {
        Image image = surface.makeImageSnapshot();
        final Data pngData = EncoderPNG.encode(image);
        ByteBuffer pngBytes = pngData.toByteBuffer();
        WritableByteChannel channel = Channels.newChannel(os);
        channel.write(pngBytes);
        channel.close();
    }

    @Override
    public void flush() {
        Canvas.super.flush();
    }
}
