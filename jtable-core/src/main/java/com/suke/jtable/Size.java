package com.suke.jtable;

public class Size implements Comparable<Size> {
    public static final Size ZERO = new Size(0,0);
    int width;
    int height;

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Size of(int width, int height) {
        assert width >= 0;
        assert height >= 0;
        return new Size(width, height);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    @Override
    public int compareTo(Size o) {
        return 0;
    }

    public Size scale(int scale) {
        return of(width * scale, height * scale);
    }

    @Override
    public String toString() {
        return "[" +
                "width:" + width +
                ",height:" + height +
                ']';
    }

    public Size divide(double divisor) {
        return of((int) (width / divisor), (int) (height / divisor));
    }

    public Size multiply(double multiplier) {
        return of((int) (width * multiplier), (int) (height * multiplier));
    }
}
