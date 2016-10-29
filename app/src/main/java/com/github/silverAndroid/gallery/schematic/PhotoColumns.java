package com.github.silverAndroid.gallery.schematic;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by silver_android on 26/10/16.
 */

public interface PhotoColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    String id = "id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String title = "title";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String url = "url";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String thumbnailURL = "thumbnailUrl";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    @References(table = GalleryDB.Tables.ALBUM, column = AlbumColumns.id)
    String albumID = "albumId";
}
