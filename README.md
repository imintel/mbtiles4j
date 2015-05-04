# mbtiles4j
A pure-java reader/writer for [MBTiles](https://github.com/mapbox/mbtiles-spec/blob/master/1.2/spec.md)


## Illustrative Examples

### Reading `.mbtiles`

```java
MBTilesReader r = new MBTilesReader(new File("control-room-0.2.0.mbtiles"));
//metadata
MetadataEntry metadata = r.getMetadata();
String tileSetName = metadata.getTilesetName();
MetadataEntry.TileSetType type = metadata.getTilesetType();
String tilesetVersion = metadata.getTilesetVersion();
String description = metadata.getTilesetDescription();
MetadataEntry.TileMimeType tileMimeType = metadata.getTileMimeType();
MetadataEntry.MetadataBounds bounds = metadata.getTilesetBounds();
String attribution = metadata.getAttribution();
//tiles
TileIterator tiles = r.getTiles();
while (tiles.hasNext()) {
	TileIterator.Tile next = tiles.next();
	int zoom = next.getZoom();
	int column = next.getColumn();
	int row = next.getRow();
	InputStream tileData = next.getData();        
}
tiles.close();
```