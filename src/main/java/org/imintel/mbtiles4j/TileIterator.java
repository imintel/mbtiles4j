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
package org.imintel.mbtiles4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A streaming iterator to extract tiles
 */
public class TileIterator {
    private ResultSet rs;

    public TileIterator(ResultSet s) {
        rs = s;
    }

    public boolean hasNext() {
        try {
            boolean hasNext = rs.next();
            if (!hasNext) {
                close();
            }
            return hasNext;
        } catch (SQLException e) {
            return false;
        }
    }

    public Tile next() throws MBTilesReadException {
        try {
            int zoom = rs.getInt("zoom_level");
            int column = rs.getInt("tile_column");
            int row = rs.getInt("tile_row");
            InputStream tile_data;
            if (rs.getBytes(4) != null) {
                tile_data = new ByteArrayInputStream(rs.getBytes(4));
            } else {
                tile_data = new ByteArrayInputStream(new byte[]{});
            }
            return new Tile(zoom, column, row, tile_data);
        } catch (SQLException e) {
            throw new MBTilesReadException("Read next tile", e);
        }
    }

    public void close() {
        try {
            rs.close();
        } catch (SQLException e) {
        }
    }
}
