package org.imintel.mbtiles4j;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import org.imintel.mbtiles4j.model.MetadataEntry;

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
}
