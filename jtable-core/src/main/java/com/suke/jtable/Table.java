package com.suke.jtable;


import com.suke.jtable.graphics.Canvas;
import com.suke.jtable.graphics.GraphicsEnv;
import lombok.Setter;
import lombok.SneakyThrows;
import sun.awt.SunHints;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.suke.jtable.Rect.merge;
import static com.suke.jtable.Size.ZERO;


public class Table implements CellStyleDelegate {
    @Setter
    CellStyle style;// = CellStyle.DEFAULT_STYLE;
    @Setter
    Color backgroundColor = Color.WHITE;
    Map<Position, Cell> cellMap = new HashMap<>();
    Map<Integer, Row> rowMap = new HashMap<>();
    Map<Integer, Column> columnMap = new HashMap<>();
    @Setter
    Rect margin = new Rect(10);

    public Cell getCell(int row, int column) {
        return getCell(row, column, 1, 1);
    }

    public Cell getCell(int row, int column, int rowSpan, int colSpan) {
        final Cell cell = new Cell();
        setCell(cell, row, column, rowSpan, colSpan);
        return cell;
    }

    private void setCell(Cell cell, int row, int column) {
        setCell(cell, row, column, 1, 1);
    }

    private void setCell(Cell cell, int row, int column, int rowSpan, int colSpan) {
        assert cell != null;
        assert column >= 0;
        assert row >= 0;
        assert colSpan > 0;
        assert rowSpan > 0;

        final Rect newRect = new Rect(column, row, column + colSpan, row + rowSpan);
        cell.setBounds(newRect);
        // 添加新的单元格
        final Set<Cell> replacedCells = addCell(cell);

        for (Cell replacedCell : replacedCells) {
            replacedCell.resetCoordinate();

            final Rect rect = replacedCell.getBounds();
            // 分裂单元格
            final Collection<Rect> intersections = rect.intersection(newRect);
            if(intersections.isEmpty()) {
                // 完全占位
                continue;
            }
            final Iterator<Rect> iterator = intersections.iterator();
            final Rect first = iterator.next();
            iterator.remove();
            replacedCell.setBounds(first);
            addCell(replacedCell);
            while (iterator.hasNext()) {
                final Cell blankCell = new Cell();
                final Rect blankCellRect = iterator.next();
                blankCell.setBounds(blankCellRect);
                addCell(blankCell);
            }
        }
    }

    private Set<Cell> addCell(Cell cell) {
        final Rect rect = cell.getBounds();
        assert rect != null;
        final Set<Cell> existCells = new HashSet<>();
        for (Position key : rect.getPositions()) {
            final Cell exist = putCell(key, cell);
            if (exist != null) {
                existCells.add(exist);
            }
        }
        return existCells;
    }

    private Cell putCell(Position position, Cell cell) {
        final Cell exist = cellMap.put(position, cell);
        final Row row = getRow(position.y);
        final Column column = getColumn(position.x);
        cell.addCoordinate(row, column);
        return exist;
    }

    public Size getBounds() {
        final Rect[] arrays = getCells()
                .stream()
                .map(Cell::getBounds)
                .toArray(Rect[]::new);
        final Rect merged = merge(arrays);
        if (merged.valid()) {
            return merged.size();
        }
        return ZERO;
    }

    public Row getRow(int row) {
        return rowMap.computeIfAbsent(row, r -> new Row(this, row));
    }

    public Column getColumn(int column) {
        return columnMap.computeIfAbsent(column, c -> new Column(this, column));
    }

    private Size layout(Constraint constraint) {
        final Collection<Integer> colIndexes = sort(columnMap.keySet());
        final Collection<Integer> rowIndexes = sort(rowMap.keySet());

        int width = 0;
        for (Integer idx : colIndexes) {
            final Column column = columnMap.get(idx);
            width += column.dryLayout(constraint);
        }

        for (Cell cell : getCells()) {
            if (cell.getColspan() > 1) {
                cell.adjustWidth();
            }
        }

        int height = 0;
        for (Integer idx : rowIndexes) {
            final Row row = rowMap.get(idx);
            height += row.dryLayout(constraint);
        }

        for (Cell cell : getCells()) {
            if (cell.getRowspan() > 1) {
                cell.adjustHeight();
            }
        }

        int offset = margin.left;
        for (Integer idx : colIndexes) {
            final Column column = columnMap.get(idx);
            column.offset(offset);
            offset += column.getWidth();
        }
        offset = margin.top;
        for (Integer idx : rowIndexes) {
            final Row row = rowMap.get(idx);
            row.offset(offset);
            offset += row.getHeight();
        }

        for (Cell cell : getCells()) {
            final Rect bounds = cell.getBounds();
            final Row row = rowMap.get(bounds.top);
            final Column column = columnMap.get(bounds.left);
            cell.offset(new Position(row.offset, column.offset));
        }

        return constraint.constrain(Size.of(
                width + margin.left + margin.right,
                height + margin.top + margin.bottom));
    }

    static Collection<Integer> sort(Collection<Integer> collection) {
        return collection.stream().sorted().collect(Collectors.toList());
    }

    private Collection<Cell> getCells() {
        return new HashSet<>(cellMap.values());
    }

    public void paint(com.suke.jtable.graphics.Canvas graphics) {
        for (Cell cell : getCells()) {
            cell.paint(graphics);
        }
    }

    @Override
    public void applyStyle(CellStyle font) {
        this.style = font;
    }

    @Override
    public CellStyle getMyStyle() {
        return style;
    }

    @SneakyThrows
    public void savePng(OutputStream os) {
        final Size size = layout(new Constraint());
        final Canvas canvas = GraphicsEnv.getGraphicsEnv()
                .createCanvas(size.width(), size.height(), BufferedImage.TYPE_USHORT_565_RGB);

        if (Objects.nonNull(backgroundColor)) {
            canvas.setColor(backgroundColor);
            canvas.fillRect(0,0,size.width(), size.height());
        }

        paint(canvas);
        canvas.write("png", os);
        canvas.flush();
        canvas.dispose();
        os.flush();
    }

}
