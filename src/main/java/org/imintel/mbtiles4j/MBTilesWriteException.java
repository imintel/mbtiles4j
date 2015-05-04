package org.imintel.mbtiles4j;

/**
 * An exception for write errors
 */
public class MBTilesWriteException extends MBTilesException {

    public MBTilesWriteException(Throwable e) {
        super(e);
    }

    public MBTilesWriteException(String msg, Throwable e) {
        super(msg, e);
    }
}
