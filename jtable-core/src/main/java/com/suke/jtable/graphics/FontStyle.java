package com.suke.jtable.graphics;

/**
 * @author zcweng
 * @date 2024/2/21
 */
public enum FontStyle {
    PLAIN(0),
    BOLD(1),
    ITALIC(1<<1),
    BOLD_ITALIC(BOLD.value | ITALIC.value);

    final int value;

    FontStyle(int value) {
        this.value = value;
    }
}
