package com.suke.jtable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zcweng
 * @date 2024/2/23
 */
@AllArgsConstructor
@Getter
public class TextLine {
    final String text;
    final TextBounds bounds;
}
