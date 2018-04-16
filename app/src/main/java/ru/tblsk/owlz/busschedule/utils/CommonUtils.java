package ru.tblsk.owlz.busschedule.utils;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

public final class CommonUtils {

    private static final AtomicLong NEXT_ID = new AtomicLong();

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
