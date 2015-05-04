package org.imintel.mbtiles4j.model;

public class MetadataBounds {
    private double left;
    private double bottom;
    private double right;
    private double top;

    public MetadataBounds(String serialized) {
        String[] split = serialized.split(",");
        double left = Double.parseDouble(split[0]);
        double bottom = Double.parseDouble(split[1]);
        double right = Double.parseDouble(split[2]);
        double top = Double.parseDouble(split[3]);
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        if (left < -180) {
            this.left = -180;
        }
        if (bottom < -85) {
            this.bottom = -85;
        }
        if (right > 180) {
            this.right = 180;
        }
        if (top > 85) {
            this.top = 85;
        }
    }

    public MetadataBounds(double left, double bottom, double right, double top) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        if (left < -180) {
            this.left = -180;
        }
        if (bottom < -85) {
            this.bottom = -85;
        }
        if (right > 180) {
            this.right = 180;
        }
        if (top > 85) {
            this.top = 85;
        }
    }

    @Override
    public String toString() {
        return left + "," + bottom + "," + right + "," + top;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return obj.toString().equals(this.toString());
        }
        return false;
    }

    public double getLeft() {
        return left;
    }

    public double getBottom() {
        return bottom;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }
}