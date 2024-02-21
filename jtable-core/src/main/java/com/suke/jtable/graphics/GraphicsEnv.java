package com.suke.jtable.graphics;

import com.suke.jtable.CellStyle;
import com.suke.jtable.TextBounds;
import com.suke.jtable.graphics.awt.AwtGraphicsEnv;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public abstract class GraphicsEnv {
    protected static GraphicsEnv env;

    protected GraphicsEnv() {}

    public static GraphicsEnv getGraphicsEnv() {
        if (env == null) {
            env = new AwtGraphicsEnv();
        }
        return env;
    }

    public abstract Canvas createCanvas(int width, int height, int imageType);
    public abstract Font getDefaultFont();
    public abstract TextBounds getTextBounds(String text, CellStyle style);
}
