package org.imintel.mbtiles4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.imintel.mbtiles4j.model.MetadataBounds;
import org.imintel.mbtiles4j.model.MetadataEntry;
import org.junit.Test;

public class MBTileReaderTest {

    private File f = new File("src/test/resources/example.mbtiles");

    @Test
    public void test() throws Exception {
        MBTilesReader reader = new MBTilesReader(f);
        MetadataEntry metadata = reader.getMetadata();
        assertEquals(metadata.getTilesetName(), "control_room");
        assertEquals(metadata.getTilesetType(), MetadataEntry.TileSetType.BASE_LAYER);
        assertEquals(metadata.getTilesetVersion(), "0.2.0");
        assertEquals(metadata.getTilesetDescription(), "");
        assertEquals(metadata.getTileMimeType(), MetadataEntry.TileMimeType.JPG);
        assertEquals(metadata.getTilesetBounds(), new MetadataBounds(-180, -85, 180, 85));
        assertEquals(metadata.getAttribution(), "");
        TileIterator tiles = reader.getTiles();
        while (tiles.hasNext()) {
            TileIterator.Tile next = tiles.next();
            assertNotNull(next.getData());
        }
        tiles.close();
        reader.close();
    }
}
