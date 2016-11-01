package com.github.silverAndroid.gallery.schematic;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by silver_android on 26/10/16.
 */

@Database(version = GalleryDB.VERSION)
public final class GalleryDB {

    static final int VERSION = 1;

    public static class Tables {
        @Table(AlbumColumns.class)
        public static final String ALBUM = "album";
        @Table(PhotoColumns.class)
        public static final String PHOTO = "photo";
    }
}
