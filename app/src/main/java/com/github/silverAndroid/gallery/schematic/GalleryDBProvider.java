package com.github.silverAndroid.gallery.schematic;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by silver_android on 26/10/16.
 */

@ContentProvider(authority = GalleryDBProvider.AUTHORITY, database = GalleryDB.class)
public class GalleryDBProvider {

    static final String AUTHORITY = "com.github.silverAndroid.gallery.schematic.GalleryDBProvider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String ALBUM = "album";
        String PHOTO = "photo";
    }

    @TableEndpoint(table = GalleryDB.Tables.ALBUM)
    public static class Album {

        @ContentUri(
                path = Path.ALBUM,
                type = "vnd.android.cursor.dir/album",
                join = "JOIN " + GalleryDB.Tables.PHOTO + " ON " + GalleryDB.Tables.PHOTO + "." + PhotoColumns.albumID
                        + " = " + GalleryDB.Tables.ALBUM + "." + AlbumColumns.id
        )
        public static final Uri CONTENT_URI = buildUri(Path.ALBUM);

        @InexactContentUri(
                name = "album_id",
                path = Path.ALBUM + "/#",
                type = "vnd.android.cursor.item/album",
                whereColumn = AlbumColumns.id,
                pathSegment = 1
        )
        public static Uri withId(int id) {
            return buildUri(Path.ALBUM, String.valueOf(id));
        }
    }

    @TableEndpoint(table = GalleryDB.Tables.PHOTO)
    public static class Photo {
        @ContentUri(
                path = Path.PHOTO,
                type = "vnd.android.cursor.dir/photo"
        )
        public static final Uri CONTENT_URI = buildUri(Path.PHOTO);

        @InexactContentUri(
                name = "photo_id",
                path = Path.PHOTO + "/#",
                type = "vnd.android.cursor.item/photo",
                whereColumn = PhotoColumns.id,
                pathSegment = 1
        )
        public static Uri withId(int id) {
            return buildUri(Path.PHOTO, String.valueOf(id));
        }

        @InexactContentUri(
                name = "photo_album_id",
                path = Path.PHOTO + "/" + Path.ALBUM + "/#",
                type = "vnd.android.cursor.dir/photo",
                whereColumn = PhotoColumns.albumID,
                pathSegment = 2
        )
        public static Uri withAlbumId(int albumId) {
            return buildUri(Path.PHOTO, Path.ALBUM, String.valueOf(albumId));
        }
    }
}
