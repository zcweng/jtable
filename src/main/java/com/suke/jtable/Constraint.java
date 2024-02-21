package com.suke.jtable;


import lombok.Getter;

@Getter
public class Constraint {
    public static final int INFINITY = Integer.MAX_VALUE;
    int minWidth;
    int maxWidth;
    int minHeight;
    int maxHeight;

    public Constraint(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public Constraint() {
        this(0, INFINITY, 0, INFINITY);
    }

    public Constraint(ConstraintWidth width, ConstraintHeight height) {
        this(width.minWidth, width.maxWidth, height.minHeight, height.maxHeight);
    }

    public Constraint min(Integer width, Integer height) {
        return new Constraint(
                width == null ? minWidth : Math.max(minWidth, width),
                maxWidth,
                height == null ? minHeight : Math.max(minHeight, height),
                maxHeight
        );
    }

    public Constraint max(Integer width, Integer height) {
        return new Constraint(
                minWidth,
                width == null ? maxWidth : Math.min(maxWidth, width),
                minHeight,
                height == null ? maxHeight : Math.min(maxHeight, height)
        );
    }

    public static Constraint height(int height) {
        return new Constraint(0, INFINITY, height, height);
    }

    public static Constraint width(int width) {
        return new Constraint(width, width, 0, INFINITY);
    }

    public static Constraint tight(Size size) {
        return new Constraint(size.width, size.width, size.height, size.height);
    }

    public static Constraint tight(int width, int height) {
        assert width>0;
        assert height>0;
        return new Constraint(width, width, height, height);
    }

    public static Constraint expand(int width, int height) {
        assert width>0;
        assert height>0;
        return new Constraint(width, INFINITY, height, INFINITY);
    }

    public static Constraint loose(int width, int height) {
        assert width>0;
        assert height>0;
        return new Constraint(0, width, 0, height);
    }

    public Constraint loose() {
        return new Constraint(0, maxWidth, 0, maxHeight);
    }

    public Constraint enforce(Constraint constraints) {
        return new Constraint(
                clamp(minWidth, constraints.minWidth, constraints.maxWidth),
                clamp(maxWidth, constraints.minWidth, constraints.maxWidth),
                clamp(minHeight, constraints.minHeight, constraints.maxHeight),
                clamp(maxHeight, constraints.minHeight, constraints.maxHeight));
    }

    public Constraint enforce(ConstraintWidth constraints) {
        return new Constraint(
                clamp(minWidth, constraints.minWidth, constraints.maxWidth),
                clamp(maxWidth, constraints.minWidth, constraints.maxWidth),
                minHeight,
                maxHeight);
    }

    public Constraint enforce(ConstraintHeight constraints) {
        return new Constraint(
                minWidth,
                maxWidth,
                clamp(minHeight, constraints.minHeight, constraints.maxHeight),
                clamp(maxHeight, constraints.minHeight, constraints.maxHeight));
    }

    /// Returns new box constraints with a tight width and/or height as close to
    /// the given width and height as possible while still respecting the original
    /// box constraints.
    public Constraint tighten(Integer width, Integer height) {
        return new Constraint(
                width == null ? minWidth : clamp(width, minWidth, maxWidth),
                width == null ? maxWidth : clamp(width, minWidth, maxWidth),
                height == null ? minHeight : clamp(height, minHeight, maxHeight),
                height == null ? maxHeight : clamp(height, minHeight, maxHeight));
    }

    public Size constrain(Size size) {
        return Size.of(constrainWidth(size.width), constrainHeight(size.height));
    }

    /// The biggest size that satisfies the constraints.
    Size biggest() {
        return Size.of(constrainWidth(INFINITY), constrainHeight(INFINITY));
    }

    /// The smallest size that satisfies the constraints.
    Size smallest() {
        return Size.of(constrainWidth(0), constrainHeight(0));
    }

    public int constrainWidth(int width) {
        return clamp(width, minWidth, maxWidth);
    }

    public int constrainHeight(int height) {
        return clamp(height, minHeight, maxHeight);
    }

    int clamp(int x, int min, int max) {
        if (x < min) {
            return min;
        }
        return Math.min(x, max);
    }


    @Override
    public String toString() {
        return "["+num2str(minWidth)+"->"+num2str(maxWidth)+","+num2str(minHeight)+"->"+num2str(maxHeight)+"]";
    }

    static String num2str(int x) {
        if (x == INFINITY) {
            return "INFINITY";
        }
        if (x == -INFINITY) {
            return "-INFINITY";
        }
        return String.valueOf(x);
    }

}
