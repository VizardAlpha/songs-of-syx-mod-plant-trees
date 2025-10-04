package com.github.argon.sos.planttree.util;

import snake2d.util.datatypes.AREA;
import snake2d.util.datatypes.COORDINATE;

import static settlement.main.SETT.GROUND;

public class FertilityUtil {
    public static double calcAvg(AREA area) {
        double fertilitySum = 0;

        for (COORDINATE coordinate : area.body()) {
            if (area.is(coordinate)) {
                fertilitySum += GROUND().MAP.get(coordinate.x(), coordinate.y()).farm;
            }
        }

        if (area.area() == 1) {
            return fertilitySum;
        }

        return fertilitySum / area.area();
    }
}
