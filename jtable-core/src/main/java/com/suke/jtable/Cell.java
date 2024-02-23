package com.suke.jtable;

import com.suke.jtable.graphics.Canvas;
import com.suke.jtable.graphics.GraphicsEnv;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Cell implements CellStyleDelegate {

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    Rect bounds;
    @Getter(AccessLevel.PROTECTED)
    Set<Row> rows = new HashSet<>();
    @Getter(AccessLevel.PROTECTED)
    Set<Column> columns = new HashSet<>();
//    String text;
    CellStyle style;
    Size size = null;
//    TextBounds textBounds;
    TextBox textBox = TextBox.EMPTY;
    Position position;

    public Cell() {
        this("");
    }
    public Cell(String text) {
        setText(text);
    }

    public void paint(Canvas graphics) {
        final CellStyle style = findStyle();
        final Color backgroundColor = style.getBackgroundColor();
        final Border border = style.getBorder();
        final int borderWidth = border == null ? 0 : border.getWidth();
        if (Objects.nonNull(backgroundColor)) {
            graphics.setColor(backgroundColor);
            graphics.fillRect(position.x + borderWidth, position.y + borderWidth,
                    size.width() - borderWidth, size.height() - borderWidth);
        }
        if (borderWidth > 0) {
            graphics.setColor(border.getColor());
            graphics.setStrokeWidth(border.getWidth());
            graphics.drawRect(position.x, position.y, size.width(), size.height());
        }

        final Position textPosition = calcTextPosition(style);
        textBox.paint(graphics, textPosition, style);

//        if (Objects.nonNull(text)) {
//            graphics.setColor(style.getColor());
//            graphics.setFont(style.getFont());
//            recalculateTextSizeIfNeed();
//            final Position textPosition = calcTextPosition(style);
//            graphics.drawString(text, textPosition.x, textPosition.y);
//        }
    }

    private Position calcTextPosition(CellStyle style) {
        final TextAlign textAlign = style.getTextAlign();
        final Rect textBounds = textBox.bounds;
        final Rect offset = textBounds.offset(position);
        int x = offset.left, y = offset.top;

        Rect padding = style.getPadding();
        if (Objects.isNull(padding)) {
            padding = Rect.EMPTY;
        }
        if (textAlign.match(TextAlign.FLAG.LEFT)) {
            x += padding.left;
        }
        if (textAlign.match(TextAlign.FLAG.MIDDLE)) {
            x += (size.width() - textBounds.width())/2;
        }
        if (textAlign.match(TextAlign.FLAG.RIGHT)) {
            x += size.width() - textBounds.width() - padding.right;
        }

        if (textAlign.match(TextAlign.FLAG.TOP)) {
            y += padding.top;
        }
        if (textAlign.match(TextAlign.FLAG.CENTER)) {
            y += (size.height() - textBounds.height())/2;
        }
        if (textAlign.match(TextAlign.FLAG.BOTTOM)) {
            y += size.height() - textBounds.height() - padding.bottom;
        }
        return new Position(y, x);
    }

    protected int dryLayoutColumn(Constraint constraint, Column column) {
        if (getColspan() == 1) {
            final int textWidth = layoutTextBox(constraint).width();
            final CellStyle style = findStyle();
            final Rect padding = style.getPadding();
            return constraint.constrainWidth(textWidth+padding.left+padding.right);
        }

        final ArrayList<Column> list = new ArrayList<>(columns);
        Collections.sort(list);
        if (column != list.get(list.size()-1)) {
            return constraint.smallest().width;
        }
        int w = 0;
        for (Column col : list) {
            if (col == column) {
                final CellStyle style = findStyle();
                final Rect padding = style.getPadding();
                final int minWidth = layoutTextBox(constraint).width()+padding.left+padding.right;
                return constraint.constrainWidth(minWidth-w);
//                return Math.max(constraint.smallest().width, Math.max(0, minWidth - w));
            }
            w += col.width;
        }
        throw new IllegalArgumentException();
    }

    private Rect layoutTextBox(Constraint constraint) {
        final CellStyle style = findStyle();
        final Rect padding = style.getPadding();
        final Constraint textConstraint = new Constraint(
                Math.max(0, constraint.minWidth-padding.left-padding.right),
                Math.max(0, constraint.maxWidth-padding.left-padding.right),
                Math.max(0, constraint.minHeight-padding.top-padding.bottom),
                Math.max(0, constraint.maxHeight-padding.top-padding.bottom));
        return textBox.measure(textConstraint, style.getFont(), style.getTextWrap());
    }

    protected int dryLayoutRow(Constraint constraint) {
        final CellStyle style = findStyle();
        final Rect padding = style.getPadding();
        final Rect textBounds = textBox.bounds;
        final int minHeight = textBounds.height() + (padding == null ? 0 : (padding.top+padding.bottom));
        size = constraint.constrain(Size.of(size.width, minHeight));
        return size.height;
    }

    public void adjustWidth() {
        final int width = columns.stream().mapToInt(Column::getWidth).sum();
        this.size = Size.of(width, size == null ? 0 : size.height);
    }

    public void adjustHeight() {
        final int height = rows.stream().mapToInt(Row::getHeight).sum();
        this.size = Size.of(size == null ? 0 : size.width, height);
    }

    public void addCoordinate(Row row, Column column) {
        rows.add(row);
        row.addCell(this);
        columns.add(column);
        column.addCell(this);
    }

    public void resetCoordinate() {
        for (Row row : rows) {
            row.removeCell(this);
        }
        for (Column column : columns) {
            column.removeCell(this);
        }
        rows.clear();
        columns.clear();
    }

    protected int getColspan() {
        return bounds.width();
    }

    protected int getRowspan() {
        return bounds.height();
    }

    public void offset(Position offset) {
        this.position = offset;
//        calcTextPosition(findStyle());
    }

    @Override
    public void applyStyle(CellStyle font) {
        this.style = font;
    }

    @Override
    public CellStyle getMyStyle() {
        return style;
    }

    @Override
    public CellStyle getParentStyle() {
        for (Row row : rows) {
            final CellStyle font = row.getMyStyle();
            if (Objects.nonNull(font)) {
                return font;
            }
        }
        for (Column column : columns) {
            final CellStyle font = column.getMyStyle();
            if (Objects.nonNull(font)) {
                return font;
            }
        }
        for (Row row : rows) {
            final CellStyle font = row.getParentStyle();
            if (Objects.nonNull(font)) {
                return font;
            }
        }
        for (Column column : columns) {
            final CellStyle font = column.getParentStyle();
            if (Objects.nonNull(font)) {
                return font;
            }
        }
        return null;
    }

    public Cell setText(String text) {
//        this.text = text;
        assert text != null;
//        textBounds = null;
        textBox = new TextBox(text);
        return this;
    }
}
