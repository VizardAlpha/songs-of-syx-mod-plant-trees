package com.github.argon.sos.planttree.util;

import snake2d.util.datatypes.AREA;
import snake2d.util.datatypes.COORDINATE;

import static settlement.main.SETT.FERTILITY;

public class FertilityUtil {
    public static double calcAvg(AREA area) {
        double fertilitySum = 0;

        for (COORDINATE coordinate : area.body()) {
            if (area.is(coordinate)) {
                fertilitySum += FERTILITY().target.get(coordinate);
            }
        }

        if (area.area() == 1) {
            return fertilitySum;
        }

        return fertilitySum / area.area();
    }
}
