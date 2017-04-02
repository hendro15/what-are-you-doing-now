package com.example.sonic.whatdoyoudo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sonic on 02/04/2017.
 */

public class Dataset {

    public String calculate(int window, List<Float> xList, List<Float> yList, List<Float> zList, String cat) {
        float sumX = 0, sumY = 0, sumZ = 0;
        float stdX = 0, stdY = 0, stdZ = 0;
        float meanX, meanY, meanZ;
        float stdDevX, stdDevY, stdDevZ;
        float minX, minY, minZ, maxX, maxY, maxZ;

        for (int i = 0; i < xList.size(); i++) {
            sumX += xList.get(i);
            sumY += yList.get(i);
            sumZ += zList.get(i);
        }

        meanX = sumX / window;
        meanY = sumY / window;
        meanZ = sumZ / window;

        for (int i = 0; i < xList.size(); i++) {
            stdX += (xList.get(i) - meanX) * (xList.get(i) - meanX);
            stdY += (yList.get(i) - meanY) * (yList.get(i) - meanY);
            stdZ += (zList.get(i) - meanZ) * (zList.get(i) - meanZ);
        }

        stdDevX = (float) Math.sqrt(stdX / (window - 1));
        stdDevY = (float) Math.sqrt(stdY / (window - 1));
        stdDevZ = (float) Math.sqrt(stdZ / (window - 1));

        String data = String.valueOf(meanX) + ";" + String.valueOf(meanY) + ";" + String.valueOf(meanZ) + ';'
                + String.valueOf(stdDevX) + ';' + String.valueOf(stdDevY) + ';' + String.valueOf(stdDevZ)
                + ';' + String.valueOf(Collections.max(xList)) + ';' + String.valueOf(Collections.max(yList)) + ';' + String.valueOf(Collections.max(zList)) + ';'
                + String.valueOf(Collections.min(xList)) + ';' + String.valueOf(Collections.min(yList)) + ';' + String.valueOf(Collections.min(zList)) + ';'
                + cat;

        return data;
    }
}
