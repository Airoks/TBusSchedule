package ru.tblsk.owlz.busschedule.utils;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class CommonUtils {
    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(jsonFileName);

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }
}
