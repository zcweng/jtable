package com.suke.jtable;

import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BinaryOperator;

import static java.lang.Math.max;
import static java.lang.Math.min;

@ToString
@Getter
public class Rect implements Comparable<Rect> {
    public static Rect EMPTY = new Rect(0,0,0,0);
    protected final int left;
    protected final int top;
    protected final int right;
    protected final int bottom;

    public Rect() {
        this(0);
    }
    public Rect(int all) {
        this(all,all,all,all);
    }
    public Rect(int horizontal, int vertical) {
        this(horizontal,vertical,horizontal,vertical);
    }
    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    private boolean test(Rect other) {
        if (left > other.right) {
            return false;
        }
        if (top > other.bottom) {
            return false;
        }
        if (right < other.left) {
            return false;
        }
        if (bottom < other.top) {
            return false;
        }
        return true;
    }

    public int height() {
        return bottom - top;
    }
    public int width() {
        return right - left;
    }

    public boolean valid() {
        return width() > 0 && height() > 0;
    }

    public Collection<Rect> intersection(Rect other) {
        if (! test(other)) {
            return Collections.emptySet();
        }
        Set<Rect> list = new HashSet<>();
        // lt
        final Rect lt = new Rect(left, top, max(left, other.left), max(top, other.top));
        list.add(lt);
        // t
        final Rect t = new Rect(lt.right, top, min(right, other.right), lt.top);
        list.add(t);
        // rt
        final Rect rt = new Rect(t.right, top, right, lt.top);
        list.add(rt);

        // l
        final Rect l = new Rect(left, lt.bottom, lt.right, min(bottom, other.bottom));
        list.add(l);
        // r
        final Rect r = new Rect(rt.left, rt.bottom, right, l.bottom);
        list.add(r);

        // lb
        final Rect lb = new Rect(left, l.bottom, l.right, bottom);
        list.add(lb);
        // b
        final Rect b = new Rect(lb.right, lb.top, t.right, bottom);
        list.add(b);
        // rb
        final Rect rb = new Rect(r.left, lb.top, right, bottom);
        list.add(rb);

        boolean rowMerged = false;
        if (t.valid()) {
            // 合并行
            list.add(merge(lt, t, rt));
            rowMerged = true;
            list.remove(lt);
            list.remove(t);
            list.remove(rt);
        }
        if (b.valid()) {
            // 合并行
            list.add(merge(lb, b, rb));
            rowMerged = true;
            list.remove(lb);
            list.remove(b);
            list.remove(rb);
        }
        if (! rowMerged) {
            if (l.valid()) {
                // 合并列
                list.add(merge(lt, l, lb));
                list.remove(lt);
                list.remove(l);
                list.remove(lb);
            }
            if (r.valid()) {
                // 合并列
                list.add(merge(rt, r, rb));
                list.remove(rt);
                list.remove(r);
                list.remove(rb);
            }
        }
        list.removeIf(rect -> ! rect.valid());
        return list;
    }

    public static Rect merge(Rect ... arr) {
        int left = Integer.MAX_VALUE;
        int top = Integer.MAX_VALUE;
        int right = Integer.MIN_VALUE;
        int bottom = Integer.MIN_VALUE;
        for (Rect rect : arr) {
            left = min(left, rect.left);
            top = min(top, rect.top);
            right = max(right, rect.right);
            bottom = max(bottom, rect.bottom);
        }
        return new Rect(left, top, right, bottom);
    }

//    public static BinaryOperator<Rect> merge() {
//        return Rect::merge;
//    }

    public Collection<Position> getPositions() {
        Set<Position> set = new HashSet<>();
        for (int r = top; r < bottom; r++) {
            for (int c = left; c < right; c++) {
                final Position key = new Position(r, c);
                set.add(key);
            }
        }
        return set;
    }

    @Override
    public int compareTo(Rect other) {
        if (left == other.left && top == other.top) {
            return 0;
        }

        return 0;
    }

    public Size size() {
        return Size.of(width(), height());
    }

    public Rect scale(int scale) {
        return new Rect(left*scale, top*scale, right*scale, bottom*scale);
    }

    public Rect offset(Position offset) {
        return new Rect(left + offset.x, top + offset.y, right + offset.x, bottom + offset.y);
    }

    public Rect offset(int dx, int dy) {
        return new Rect(left + dx, top + dy, right + dx, bottom + dy);
    }
}
