package org.imintel.mbtiles4j;

import org.imintel.mbtiles4j.model.MetadataEntry;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MBTilesReader {

    private File f;
    private Connection connection;

    public MBTilesReader(File f) throws MBTilesReadException {
        try {
            connection = SQLHelper.establishConnection(f);
        } catch (MBTilesException e) {
            throw new MBTilesReadException("Establish Connection to " + f.getAbsolutePath() + " failed", e);
        }
        this.f = f;
    }

    public File close() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
        return f;
    }

    public MetadataEntry getMetadata() throws MBTilesReadException {
        String sql = "SELECT * from metadata;";
        try {
            ResultSet resultSet = SQLHelper.executeQuery(connection, sql);
            MetadataEntry ent = new MetadataEntry();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String value = resultSet.getString("value");
                ent.addKeyValue(name, value);
            }
            return ent;
        } catch (MBTilesException | SQLException e) {
            throw new MBTilesReadException("Get Metadata failed", e);
        }
    }

    public TileIterator getTiles() throws MBTilesReadException {
        String sql = "SELECT * from tiles;";
        try {
            ResultSet resultSet = SQLHelper.executeQuery(connection, sql);
            return new TileIterator(resultSet);
        } catch (MBTilesException e) {
            throw new MBTilesReadException("Access Tiles failed", e);
        }
    }

    public static void main(String[] args) throws Exception {
        MBTilesReader r = new MBTilesReader(new File("/Users/ckcook/Downloads/control-room-0.2.0.mbtiles"));
        MetadataEntry metadata = r.getMetadata();
        String tileSetName = metadata.getTilesetName();
        MetadataEntry.TileSetType type = metadata.getTilesetType();
        String tilesetVersion = metadata.getTilesetVersion();
        String description = metadata.getTilesetDescription();
        MetadataEntry.TileMimeType tileMimeType = metadata.getTileMimeType();
        MetadataEntry.MetadataBounds bounds = metadata.getTilesetBounds();
        String attribution = metadata.getAttribution();
        TileIterator tiles = r.getTiles();
        while (tiles.hasNext()) {
            TileIterator.Tile next = tiles.next();
            int zoom = next.getZoom();
            int column = next.getColumn();
            int row = next.getRow();
            InputStream tileData = next.getData();
        }
        tiles.close();
    }

}
