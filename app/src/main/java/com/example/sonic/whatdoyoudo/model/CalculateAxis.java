package com.example.sonic.whatdoyoudo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sonic on 02/04/2017.
 */

public class CalculateAxis {

    List<Float> data;

    public List<Float> calculate(int window, List<Float> xList, List<Float> yList, List<Float> zList) {
        data = new ArrayList<Float>();
        float sumX = 0, sumY = 0, sumZ = 0;
        float stdX = 0, stdY = 0, stdZ = 0;
        float meanX, meanY, meanZ;
        float stdDevX, stdDevY, stdDevZ;

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

        data.add(meanX);
        data.add(meanY);
        data.add(meanZ);
        data.add(stdDevX);
        data.add(stdDevY);
        data.add(stdDevZ);
        data.add(Collections.max(xList));
        data.add(Collections.max(yList));
        data.add(Collections.max(zList));
        data.add(Collections.min(xList));
        data.add(Collections.min(yList));
        data.add(Collections.min(zList));

        return data;
    }
}
