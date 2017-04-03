package com.example.sonic.whatdoyoudo.utils;

import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.FileWriter;

/**
 * Created by sonic on 03/04/2017.
 */

public class Dataset {

    private CSVWriter writer;
    private String[] header;

    public String createDataset(String[] entries, boolean newDataset) {
        if (newDataset) {
            header = new String[]{
                    "MeanX", "MeanY", "MeanZ",
                    "StdDevX", "StdDevY", "StdDevZ",
                    "MaxX", "MaxY", "MaxZ",
                    "MinX", "MinY", "MinZ",
                    "Class"
            };

            if(writeToFile(header, false)){
                if (writeToFile(entries, true)) {
                    return "New dataset created";
                }else {
                    return "Fail created dataset";
                }
            } else {
                return "Fail created header";
            }

        } else {
            if (writeToFile(entries, true)){
                return "Dataset added";
            } else {
                return "File record dataset";
            }
        }
    }

    private boolean writeToFile(String[] data, boolean append) {
        try {
            writer = new CSVWriter(new FileWriter("/sdcard/dataset.csv", append), ',');
            writer.writeNext(data);
            writer.close();
            return true;
        } catch (Exception e) {
            String c = e.toString();
//            Log.i("err", e.toString());
            return false;
        }
    }
}
