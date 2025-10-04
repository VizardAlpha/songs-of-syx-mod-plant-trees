package com.github.argon.sos.planttree.util;

import settlement.main.SETT;
import snake2d.util.datatypes.AREA;
import snake2d.util.datatypes.COORDINATE;

public class MoistureUtil {
    public static double calculateAverage(AREA area) {
        double moistureSum = 0;

        for (COORDINATE coordinate : area.body()) {
            if (area.is(coordinate)) {
                double moisture = getCurrent(coordinate);
                moistureSum += moisture;
            }
        }

        return moistureSum / area.area();
    }

    public static double getCurrent(int x, int y) {
        return SETT.GROUND().MOISTURE_CURRENT.get(x, y);
    }

    public static double getCurrent(COORDINATE coordinate) {
        return SETT.GROUND().MOISTURE_CURRENT.get(coordinate);
    }
}
