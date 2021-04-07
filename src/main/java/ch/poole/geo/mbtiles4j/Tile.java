package ch.poole.geo.mbtiles4j;

import java.io.InputStream;

public class Tile {
    private int zoom;
    private int column;
    private int row;
    private InputStream data;

    public Tile(int zoom, int column, int row, InputStream tile_data) {
        this.zoom = zoom;
        this.column = column;
        this.row = row;
        this.data = tile_data;
    }

    public InputStream getData() {
        return data;
    }

    public int getZoom() {
        return zoom;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}