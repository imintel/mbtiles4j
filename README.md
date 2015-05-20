# mbtiles4j

[![](https://travis-ci.org/imintel/mbtiles4j.svg?branch=master)](https://travis-ci.org/imintel/mbtiles4j)

A pure-java reader/writer for [MBTiles](https://github.com/mapbox/mbtiles-spec/blob/master/1.2/spec.md)

### About MBTiles

MBTiles is a MapBox specification for storing large collections of tiles in a single tileset. Each mbtiles file  is a sqlite instance with metadata and tiles.

### Maven

```xml
<dependency>
   <groupId>org.imintel</groupId>
   <artifactId>mbtiles4j</artifactId>
   <version>1.0.5</version>
</dependency>
```

### Illustrative Examples

#### Reading `.mbtiles`

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
r.close();
```

#### Writing `.mbtiles`

```java
MBTilesWriter w = new MBTilesWriter(new File("example.mbtiles"));
MetadataEntry ent = new MetadataEntry();
//Add metadata parts
ent.setTilesetName("An example Tileset")
	.setTilesetType(MetadataEntry.TileSetType.BASE_LAYER)
	.setTilesetVersion("0.2.0")
	.setTilesetDescription("An example tileset description")
	.setTileMimeType(MetadataEntry.TileMimeType.PNG)
	.setAttribution("Tiles are Open Source!")
	.setTilesetBounds(-180, -85, 180, 85);
w.addMetadataEntry(ent);
//add someTile at Zoom (0), Column(0), Row (0)
w.addTile(someTileBytes, 0, 0, 0);
File result = w.close();
```
