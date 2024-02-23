package com.suke.jtable;

import com.suke.jtable.graphics.Canvas;
import com.suke.jtable.graphics.Font;
import com.suke.jtable.graphics.GraphicsEnv;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zcweng
 * @date 2024/2/23
 */
public class TextBox {
    public static final TextBox EMPTY = new TextBox("");
    Rect bounds;
    List<String> lines;
    final String text;
    boolean measured = false;

    public TextBox(String text) {
        this.text = text;
        assert text != null : "text can't be null";
    }

    public Rect measure(Constraint constraint, Font font, TextWrap textWrap) {
        final String[] lines = text.split("\r?\n");
        List<TextLine> textLines = new ArrayList<>(lines.length);
        for (String line : lines) {
            if (textWrap == TextWrap.WRAP) {
                final Size biggest = constraint.biggest();
                wrapText(line, font, biggest.width, textLines);
            } else {
                final GraphicsEnv graphicsEnv = GraphicsEnv.getGraphicsEnv();
                final TextLine textLine = new TextLine(line, graphicsEnv.measureTextBounds(line, font));
                textLines.add(textLine);
            }
        }
        this.lines = textLines.stream().map(TextLine::getText).collect(Collectors.toList());
        if (textLines.isEmpty()) {
            this.bounds = Rect.EMPTY;
        } else {
            final TextLine first = textLines.get(0);
            Rect bounds = first.getBounds();
            for (int i = 1; i < textLines.size(); i++) {
                final TextBounds itemBounds = textLines.get(i).getBounds();
                bounds = new Rect(bounds.left, bounds.top,
                        bounds.right + Math.max(0, itemBounds.width() - bounds.width()),
                        bounds.bottom + itemBounds.height() + font.getSpacing());
            }
            this.bounds = bounds;
        }
        measured = true;
        return bounds;
    }

    private void wrapText(String line, Font font, int maxWidth, List<TextLine> textLines) {
        final GraphicsEnv graphicsEnv = GraphicsEnv.getGraphicsEnv();
        final TextBounds bounds = graphicsEnv.measureTextBounds(line, font);
        if (bounds.width() <= maxWidth) {
            textLines.add(new TextLine(line, bounds));
            return;
        }
        final int length = line.length();
        int start = 0;
        int end = 0;
        while (end < length) {
            final String sub = line.substring(start, end);
            final TextBounds subBounds = graphicsEnv.measureTextBounds(sub, font);
            if (subBounds.width() > maxWidth) {
                if (start == end) {
                    end++;
                }
                textLines.add(new TextLine(line.substring(start, end), graphicsEnv.measureTextBounds(line.substring(start, end), font)));
                start = end;
            }
            end++;
        }
        if (start < length) {
            textLines.add(new TextLine(line.substring(start), graphicsEnv.measureTextBounds(line.substring(start), font)));
        }
    }

    public void paint(Canvas graphics, Position textPosition, CellStyle style) {
        final Font font = style.getFont();
        final Color color = style.getColor();
        graphics.setColor(color);
        graphics.setFont(font);
        final int lineHeight = font.getSize();
        final int y = textPosition.y;// + lineHeight;
        for (int i = 0; i < lines.size(); i++) {
            final String line = lines.get(i);
            final int x = textPosition.x;
            final int dy = y + i * (lineHeight + font.getSpacing());
            graphics.drawString(line, x, dy);
        }
    }

    public boolean isMeasured() {
        return measured;
    }
}
