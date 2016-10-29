package com.github.silverAndroid.gallery.schematic;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by silver_android on 26/10/16.
 */

public interface AlbumColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    String id = "id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String userID = "userId";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String title = "title";
}
