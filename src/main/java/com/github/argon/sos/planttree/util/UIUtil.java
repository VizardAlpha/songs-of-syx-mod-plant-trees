package com.github.argon.sos.planttree.util;

import com.github.argon.sos.planttree.log.Logger;
import com.github.argon.sos.planttree.log.Loggers;
import settlement.job.Job;
import settlement.main.SETT;

public class UIUtil {
    private final static Logger log = Loggers.getLogger(UIUtil.class);

    public static void addJob(Job job) {
        int pos = SETT.JOBS().clearss.placers.length - 1;
        SETT.JOBS().clearss.placers[pos] = job.placer();

        log.debug("Added job '%s' to UI", job.getClass().getSimpleName());
    }
}
