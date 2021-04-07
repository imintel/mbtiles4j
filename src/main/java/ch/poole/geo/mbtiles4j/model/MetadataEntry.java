/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */
package ch.poole.geo.mbtiles4j.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A container for the metadata table
 */
public class MetadataEntry {
    //these are the key-value pairs for required fields
    private Map<String, String> keyValuePairs = new HashMap<>();
    //these are the key-value pairs for custom fields
    private Map<String, String> customPairs = new HashMap<>();
    //these are the keys for required fields
    private Set<String> requiredKeys = new HashSet<>();

    /**
     * Provides a basic, if not uninformative, metadata entry.
     * If using this constructor, please modify with setX() methods
     */
    public MetadataEntry() {
        initRequiredKeys();
        setTilesetName("tilesetDefinedAt" + System.currentTimeMillis());
        setTilesetType(TileSetType.BASE_LAYER);
        setTilesetVersion("0");
        setTilesetDescription("n/a");
        setTileMimeType(TileMimeType.JPG);
        setTilesetBounds(-180, -85, 180, 85);
        setAttribution("");
    }

    /**
     * Initialize a metadata entry, accepting default bounds and attribution fields
     *
     * @param name        The plain-english name of the tileset.
     * @param type        overlay or baselayer
     * @param version     The version of the tileset, as a plain number.
     * @param description A description of the layer as plain text.
     * @param mimeType    The image file format of the tile data: png or jpg
     */
    public MetadataEntry(String name, TileSetType type, String version, String description, TileMimeType mimeType) {
        initRequiredKeys();
        setTilesetName(name);
        setTilesetType(type);
        setTilesetVersion(version);
        setTilesetDescription(description);
        setTileMimeType(mimeType);
    }

    /**
     * Initialize a metadata entry, accepting default attribution fields
     *
     * @param name        The plain-english name of the tileset.
     * @param type        overlay or baselayer
     * @param version     The version of the tileset, as a plain number.
     * @param description A description of the layer as plain text.
     * @param mimeType    The image file format of the tile data: png or jpg
     * @param bounds      The maximum extent of the rendered map area.
     *                    Bounds must define an area covered by all zoom levels.
     *                    The bounds are represented in WGS:84 - latitude and longitude values,
     *                    in the OpenLayers Bounds format - left, bottom, right, top.
     *                    Example of the full earth: -180.0,-85,180,85.
     */
    public MetadataEntry(String name, TileSetType type, String version, String description, TileMimeType mimeType, MetadataBounds bounds) {
        initRequiredKeys();
        setTilesetName(name);
        setTilesetType(type);
        setTilesetVersion(version);
        setTilesetDescription(description);
        setTileMimeType(mimeType);
        setTilesetBounds(bounds);
    }

    /**
     * Initialize a metadata entry, accepting default bounds fields
     *
     * @param name        The plain-english name of the tileset.
     * @param type        overlay or baselayer
     * @param version     The version of the tileset, as a plain number.
     * @param description A description of the layer as plain text.
     * @param mimeType    The image file format of the tile data: png or jpg
     * @param attribution An attribution string, which explains in English (and HTML)
     *                    the sources of data and/or style for the map.
     */
    public MetadataEntry(String name, TileSetType type, String version, String description, TileMimeType mimeType, String attribution) {
        initRequiredKeys();
        setTilesetName(name);
        setTilesetType(type);
        setTilesetVersion(version);
        setTilesetDescription(description);
        setTileMimeType(mimeType);
        setAttribution(attribution);
    }

    /**
     * Initialize a metadata entry
     *
     * @param name        The plain-english name of the tileset.
     * @param type        overlay or baselayer
     * @param version     The version of the tileset, as a plain number.
     * @param description A description of the layer as plain text.
     * @param mimeType    The image file format of the tile data: png or jpg
     * @param bounds      The maximum extent of the rendered map area.
     *                    Bounds must define an area covered by all zoom levels.
     *                    The bounds are represented in WGS:84 - latitude and longitude values,
     *                    in the OpenLayers Bounds format - left, bottom, right, top.
     *                    Example of the full earth: -180.0,-85,180,85.
     * @param attribution An attribution string, which explains in English (and HTML)
     *                    the sources of data and/or style for the map.
     */
    public MetadataEntry(String name, TileSetType type, String version, String description, TileMimeType mimeType, MetadataBounds bounds, String attribution) {
        initRequiredKeys();
        setTilesetName(name);
        setTilesetType(type);
        setTilesetVersion(version);
        setTilesetDescription(description);
        setTileMimeType(mimeType);
        setTilesetBounds(bounds);
        setAttribution(attribution);
    }

    /**
     * Set the name of this tile set
     *
     * @param name The plain-english name of the tileset.
     * @return this entry
     */
    public MetadataEntry setTilesetName(String name) {
        keyValuePairs.put("name", name);
        return this;
    }

    /**
     * @return The plain-english name of the tileset.
     */
    public String getTilesetName() {
        return keyValuePairs.get("name");
    }

    /**
     * @param type overlay or baselayer
     * @return this entry
     */
    public MetadataEntry setTilesetType(TileSetType type) {
        keyValuePairs.put("type", type.toString());
        return this;
    }

    /**
     * @return overlay or baselayer
     */
    public TileSetType getTilesetType() {
        String strValue = keyValuePairs.get("type");
        return TileSetType.getTypeFromString(strValue);
    }

    /**
     * @param version The version of the tileset, e.g (0.2.0)
     * @return this entry
     */
    public MetadataEntry setTilesetVersion(String version) {
        keyValuePairs.put("version", version);
        return this;
    }

    /**
     * @return The version of the tileset, e.g (0.2.0)
     */
    public String getTilesetVersion() {
        return keyValuePairs.get("version");
    }

    /**
     * @param description A description of the layer as plain text.
     * @return this entry
     */
    public MetadataEntry setTilesetDescription(String description) {
        keyValuePairs.put("description", description);
        return this;
    }

    /**
     * @return A description of the layer as plain text.
     */
    public String getTilesetDescription() {
        return keyValuePairs.get("description");
    }

    /**
     * @param fmt The image file format of the tile data: png or jpg
     * @return this entry
     */
    public MetadataEntry setTileMimeType(TileMimeType fmt) {
        keyValuePairs.put("format", fmt.toString());
        return this;
    }

    /**
     * @return The image file format of the tile data: png or jpg
     */
    public TileMimeType getTileMimeType() {
        return TileMimeType.getTypeFromString(keyValuePairs.get("format"));
    }

    /**
     * The maximum extent of the rendered map area.
     * Bounds must define an area covered by all zoom levels.
     * The bounds are represented in WGS:84 - latitude and longitude values,
     * in the OpenLayers Bounds format - left, bottom, right, top.
     * Example of the full earth: -180.0,-85,180,85.
     *
     * @param left   left, long
     * @param bottom bottom, lat
     * @param right  right , long
     * @param top    top,lat
     * @return this entry
     */
    public MetadataEntry setTilesetBounds(double left, double bottom, double right, double top) {
        return setTilesetBounds(new MetadataBounds(left, bottom, right, top));
    }

    /**
     * @param bounds The maximum extent of the rendered map area.
     *               Bounds must define an area covered by all zoom levels.
     *               The bounds are represented in WGS:84 - latitude and longitude values,
     *               in the OpenLayers Bounds format - left, bottom, right, top.
     *               Example of the full earth: -180.0,-85,180,85.
     * @return this entry
     */
    public MetadataEntry setTilesetBounds(MetadataBounds bounds) {
        keyValuePairs.put("bounds", bounds.toString());
        return this;
    }

    /**
     * @return The maximum extent of the rendered map area.
     * Bounds must define an area covered by all zoom levels.
     * The bounds are represented in WGS:84 - latitude and longitude values,
     * in the OpenLayers Bounds format - left, bottom, right, top.
     * Example of the full earth: -180.0,-85,180,85.
     */
    public MetadataBounds getTilesetBounds() {
        return new MetadataBounds(keyValuePairs.get("bounds"));
    }

    /**
     * @param attribution An attribution string, which explains in English (and HTML)
     *                    the sources of data and/or style for the map.
     * @return this entry
     */
    public MetadataEntry setAttribution(String attribution) {
        keyValuePairs.put("attribution", attribution);
        return this;
    }

    /**
     * @return An attribution string, which explains in English (and HTML)
     * the sources of data and/or style for the map.
     */
    public String getAttribution() {
        return keyValuePairs.get("attribution");
    }

    /**
     * Add a custom key-value pair outside of those defined by the standard
     *
     * @param key   the key of the key-value pair
     * @param value the value of the key-value pair
     * @return this entry
     */
    public MetadataEntry addCustomKeyValue(String key, String value) {
        if (requiredKeys.contains(key)) {
            keyValuePairs.put(key, value);
        } else {
            customPairs.put(key, value);
        }
        return this;
    }

    /**
     * @return a set of custom key-value pairs
     */
    public Set<Map.Entry<String, String>> getCustomKeyValuePairs() {
        return customPairs.entrySet();
    }

    /**
     * @return a set of the required key-value pairs, per the standard
     */
    public Set<Map.Entry<String, String>> getRequiredKeyValuePairs() {
        return keyValuePairs.entrySet();
    }

    /**
     * An agnostic key-value pair, it figures out whether the key is a required key, or a custom key
     *
     * @param name  the name of the agnostic key
     * @param value the value of the agnostic key
     */
    public void addKeyValue(String name, String value) {
        if (requiredKeys.contains(name)) {
            keyValuePairs.put(name, value);
        } else {
            customPairs.put(name, value);
        }
    }

    private void initRequiredKeys() {
        requiredKeys.add("name");
        requiredKeys.add("type");
        requiredKeys.add("version");
        requiredKeys.add("description");
        requiredKeys.add("format");
        requiredKeys.add("bounds");
        requiredKeys.add("attribution");
    }


    public enum TileMimeType {
        PNG,
        JPG;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

        public static TileMimeType getTypeFromString(String format) {
            for (TileMimeType t : TileMimeType.values()) {
                if (t.toString().equals(format)) {
                    return t;
                }
            }
            return null;
        }
    }

    public enum TileSetType {
        OVERLAY("overlay"),
        BASE_LAYER("baselayer");

        private String str;

        TileSetType(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }

        public static TileSetType getTypeFromString(String strValue) {
            for (TileSetType t : TileSetType.values()) {
                if (t.str.equals(strValue)) {
                    return t;
                }
            }
            return null;
        }
    }

}
