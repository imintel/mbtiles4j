package org.imintel.mbtiles4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.imintel.mbtiles4j.model.MetadataBounds;
import org.imintel.mbtiles4j.model.MetadataEntry;
import org.junit.Test;

public class MBTileReaderTest {

    private File f = new File("src/test/resources/globe.mbtiles");

    @Test
    public void test() throws Exception {
        MBTilesReader reader = new MBTilesReader(f);
        MetadataEntry metadata = reader.getMetadata();
        assertEquals("Offline", metadata.getTilesetName());
        assertEquals(MetadataEntry.TileSetType.BASE_LAYER, metadata.getTilesetType());
        assertEquals("0.0.1", metadata.getTilesetVersion());
        assertEquals("Global offline baselayer", metadata.getTilesetDescription());
        assertEquals(MetadataEntry.TileMimeType.PNG, metadata.getTileMimeType());
        assertEquals(new MetadataBounds(-180, -85, 180, 85), metadata.getTilesetBounds());
        assertEquals("© OpenStreetMap contributors", metadata.getAttribution());
        TileIterator tiles = reader.getTiles();
        while (tiles.hasNext()) {
            Tile next = tiles.next();
            assertNotNull(next.getData());
        }
        tiles.close();
        reader.close();
    }
    
    @Test
    public void getTile() throws Exception{
    	MBTilesReader reader = new MBTilesReader(f);
    	Tile tile = reader.getTile(0, 0, 0);
    	InputStream is = tile.getData();
    	
    	File referenceImage = new File("src/test/resources/test.png");
    	String checksumReferenceFile = getChecksum(Files.newInputStream(referenceImage.toPath()));
    	String tileChecksum = getChecksum(is);
    	
    	assertEquals(checksumReferenceFile, tileChecksum);
    }
    
    @Test (expected=MBTilesReadException.class)
    public void getNonExistingTile() throws Exception{
    	MBTilesReader reader = new MBTilesReader(f);
    	reader.getTile(20, 0, 0);
    }
    
    @Test
    public void getMaxZoom() throws Exception{
    	MBTilesReader reader = new MBTilesReader(f);
    	int maxZoom = reader.getMaxZoom();
    	assertEquals(2, maxZoom);
    }
    
    @Test
    public void getMinZoom() throws Exception{
    	MBTilesReader reader = new MBTilesReader(f);
    	int minZoom = reader.getMinZoom();
    	assertEquals(0, minZoom);
    }
    
    /**
     * Calculate a SHA-1 hash for the inputStream
     * @param inputStream
     * @return hexadecimal string representing the SHA-1 hash of the input
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private static String getChecksum(InputStream inputStream) throws IOException, NoSuchAlgorithmException
    {
    	
    	//Use SHA-1 algorithm
    	MessageDigest shaDigest = MessageDigest.getInstance("SHA-1");
    	
        //Get file input stream for reading the file content
//        FileInputStream fis = new FileInputStream(inputstream);
         
        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;
          
        //Read file data and update in message digest
        while ((bytesCount = inputStream.read(byteArray)) != -1) {
        	shaDigest.update(byteArray, 0, bytesCount);
        };
         
        //close the stream; We don't need it now.
        inputStream.close();
         
        //Get the hash's bytes
        byte[] bytes = shaDigest.digest();
         
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        //return complete hash
       return sb.toString();
    }
}
