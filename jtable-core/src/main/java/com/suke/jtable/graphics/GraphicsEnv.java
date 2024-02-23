package com.suke.jtable.graphics;

import com.suke.jtable.CellStyle;
import com.suke.jtable.TextBox;
import com.suke.jtable.TextBounds;
import com.suke.jtable.TextLine;
import com.suke.jtable.TextWrap;
import com.suke.jtable.graphics.awt.AwtGraphicsEnv;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public abstract class GraphicsEnv {
    private static GraphicsEnv env;

    protected GraphicsEnv() {}

    public static GraphicsEnv getGraphicsEnv() {
        if (env == null) {
            env = new AwtGraphicsEnv();
        }
        return env;
    }

    protected static void setEnv(GraphicsEnv env) {
        assert env != null;
        assert GraphicsEnv.env == null;
        GraphicsEnv.env = env;
    }

    public abstract Canvas createCanvas(int width, int height, int imageType);
    public abstract Font getDefaultFont();

//    public TextBox getTextBounds(String text, CellStyle style, int maxWidth) {
//        final String[] lines = text.split("\r?\n");
//        final TextWrap textWrap = style.getTextWrap();
//        List<TextLine> textLines = new ArrayList<>(lines.length);
//        for (String line : lines) {
//            if (textWrap == TextWrap.WRAP) {
//                wrapText(line, style, maxWidth, textLines);
//            } else {
//                final TextLine textLine = new TextLine(line, measureTextBounds(line, style));
//                textLines.add(textLine);
//            }
//        }
//        return new TextBox(textLines);
//    }

    public abstract TextBounds measureTextBounds(String text, Font font);
}
