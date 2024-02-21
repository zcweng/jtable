package com.suke.jtable.graphics.java;

import com.suke.jtable.graphics.Canvas;
import com.suke.jtable.graphics.Font;
import lombok.SneakyThrows;
import sun.awt.SunHints;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author changwen.zhou
 * @date 2024/2/21
 */
public class JavaCanvas implements Canvas {
    final BufferedImage image;
    final Graphics2D graphics;

    public JavaCanvas(BufferedImage image) {
        this.image = image;
        this.graphics = image.createGraphics();
        graphics.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_QUALITY);
    }

    @Override
    public void setColor(Color color) {
        graphics.setColor(color);
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        graphics.fillRect(x, y, width, height);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    @Override
    public void drawString(String text, int x, int y) {
        graphics.drawString(text, x, y);
    }

    @Override
    public void setStrokeWidth(int width) {
        graphics.setStroke(new BasicStroke(width));
    }

    @Override
    public void setFont(Font font) {
        java.awt.Font javaFont = ((JavaFont) font);
        if (Objects.isNull(javaFont)) {
            javaFont = JavaFont.DEFAULT;
        }
        graphics.setFont(javaFont);
    }

    @SneakyThrows
    @Override
    public void write(String format, OutputStream os) {
        ImageIO.write(image, format, os);
    }

    @Override
    public void flush() {
        image.flush();
    }
}