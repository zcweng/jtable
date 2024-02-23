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


@Setter
@EqualsAndHashCode(of = "column")
public class Column implements Comparable<Column>, CellStyleDelegate {
    static final ConstraintWidth DEFAULT_CONSTRAINT = new ConstraintWidth(0, Constraint.INFINITY);
    public static final int WIDTH_ADJUST_CONTENT = -1;

    final Table table;

    @Getter(AccessLevel.PROTECTED)
    int width = 0;
//    Border border;
    ConstraintWidth constraint = DEFAULT_CONSTRAINT;
    Set<Cell> cells = new HashSet<>();

    @Getter(AccessLevel.PROTECTED)
    int offset;

    @Getter(AccessLevel.PROTECTED)
    private final int column;

    CellStyle style;

    protected Column(Table table, int column) {
        this.table = table;
        this.column = column;
    }

    protected void addCell(Cell cell) {
        cells.add(cell);
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
    }

    protected Collection<Cell> getCells() {
        return Collections.unmodifiableCollection(cells);
    }

    protected int dryLayout(Constraint constraint) {
        Integer width = null;
        final Constraint enforce = constraint.enforce(this.constraint);
        for (Cell cell : getCells()) {
            width = cell.dryLayoutColumn(enforce.min(width, null), this);
        }

        this.width = enforce.tighten(width, null).smallest().width;
        for (Cell cell : getCells()) {
            if (cell.getColspan() == 1) {
                cell.adjustWidth();
            }
        }
        return this.width;
    }

    public void offset(int offset) {
        this.offset = offset;
    }

    @Override
    public int compareTo(Column o) {
        return Comparator.comparing(Column::getColumn).compare(this, o);
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
}
