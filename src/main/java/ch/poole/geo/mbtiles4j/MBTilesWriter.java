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
package ch.poole.geo.mbtiles4j;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import ch.poole.geo.mbtiles4j.model.MetadataEntry;

public class MBTilesWriter {

    Connection connection;
    private File file;

    public MBTilesWriter(File f) throws MBTilesWriteException {
        try {
            connection = SQLHelper.establishConnection(f);
        } catch (MBTilesException e) {
            throw new MBTilesWriteException("Establish connection to new mbtiles", e);
        }
        file = f;
        init();
    }

    public MBTilesWriter() throws MBTilesWriteException {
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".mbtiles");
            connection = SQLHelper.establishConnection(file);
            init();
        } catch (MBTilesException | IOException e) {
            throw new MBTilesWriteException("Create a temp file to write mbtiles to", e);
        }
    }

    public MBTilesWriter(String name) throws MBTilesWriteException {
        try {
            file = File.createTempFile(name, ".mbtiles");
            connection = SQLHelper.establishConnection(file);
            init();
        } catch (MBTilesException | IOException e) {
            throw new MBTilesWriteException("Create a temp file to write mbtiles to", e);
        }
    }


    private void init() throws MBTilesWriteException {
        try {
            SQLHelper.createTable(connection, "metadata", "(name text,value text)", "CREATE UNIQUE INDEX name on metadata (name);");
            SQLHelper.createTable(connection, "tiles", "(zoom_level integer, tile_column integer, tile_row integer, tile_data blob)", "CREATE UNIQUE INDEX tile_index on tiles (zoom_level, tile_column, tile_row);");
        } catch (MBTilesException e) {
            throw new MBTilesWriteException("Initialize new mbtiles failed", e);
        }

    }

    public void addMetadataEntry(MetadataEntry ent) throws MBTilesWriteException {
        for (Map.Entry<String, String> metadata : ent.getRequiredKeyValuePairs()) {
            String schema = "(name,value)";
            String values = "VALUES('" + metadata.getKey() + "','" + metadata.getValue() + "')";
            try {
                SQLHelper.insert(connection, "metadata", schema, values);
            } catch (MBTilesException e) {
                throw new MBTilesWriteException("Add metadata failed.", e);
            }
        }
        for (Map.Entry<String, String> metadata : ent.getCustomKeyValuePairs()) {
            String schema = "(name,value)";
            String values = "VALUES('" + metadata.getKey() + "','" + metadata.getValue() + "')";
            try {
                SQLHelper.insert(connection, "metadata", schema, values);
            } catch (MBTilesException e) {
                throw new MBTilesWriteException("Add metadata failed.", e);
            }
        }
    }

    public void addTile(InputStream tileIs, long zoom, long column, long row) throws MBTilesWriteException {
        try {
            byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(tileIs);
            addTile(bytes, zoom, column, row);
        } catch (IOException e) {
            throw new MBTilesWriteException("Add Tile Failed.", e);
        }
    }

    public void addTile(byte[] bytes, long zoom, long column, long row) throws MBTilesWriteException {
        try {
            SQLHelper.addTile(connection, bytes, zoom, column, row);
        } catch (MBTilesException e) {
            throw new MBTilesWriteException("Add Tile to MBTiles file failed", e);
        }
    }

    public void addTile(File f, long zoom, long column, long row) throws MBTilesWriteException {
        try {
            addTile(new FileInputStream(f), zoom, column, row);
        } catch (FileNotFoundException e) {
            throw new MBTilesWriteException("Add tile failed. No file found.", e);
        }
    }

    public File close() {
        try {
            connection.close();
        } catch (SQLException e) {
        }
        return file;
    }

    /**
     * Expose the Connection object
     * This is for example useful for turning off auto commit.
     * 
     * @return the current Connection object
     */
    public Connection getConnection() {
        return connection;
    }
}
