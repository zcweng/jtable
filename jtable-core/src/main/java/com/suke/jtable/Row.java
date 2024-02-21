package com.suke.jtable;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(of = "row")
public class Row implements Comparable<Row>, CellStyleDelegate {
    static final ConstraintHeight DEFAULT_CONSTRAINT = new ConstraintHeight(0, Constraint.INFINITY);
    final Table table;
    @Setter
    ConstraintHeight constraint = DEFAULT_CONSTRAINT;
    @Getter(AccessLevel.PROTECTED)
    int height = -1;

    @Getter(AccessLevel.PROTECTED)
    int offset;

    CellStyle style;

    @Getter(AccessLevel.PROTECTED)
    private final int row;

    private Set<Cell> cells = new HashSet<>();

    protected Row(Table table, int row) {
        this.table = table;
        this.row = row;
    }

    protected void addCell(Cell cell) {
        cells.add(cell);
    }

    protected void removeCell(Cell cell) {
        cells.remove(cell);
    }

    protected Collection<Cell> getCells() {
        return Collections.unmodifiableCollection(cells);
    }

    public int dryLayout(Constraint constraint) {
        int height = 0;
        final Constraint enforce = constraint.enforce(this.constraint);
        for (Cell cell : getCells()) {
            if (cell.getRowspan() == 1) {
                final int h = cell.dryLayoutRow(enforce);
                height = Math.max(height, h);
            }
        }
        this.height = enforce.tighten(null, height).smallest().height;
        for (Cell cell : getCells()) {
            if (cell.getRowspan() == 1) {
                cell.adjustHeight();
            }
        }
        return this.height;
    }

    public void offset(int offset) {
        this.offset = offset;
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
    public CellStyleDelegate getParent() {
        return table;
    }

    @Override
    public int compareTo(Row o) {
        return Comparator.comparing(Row::getRow).compare(this, o);
    }
}
