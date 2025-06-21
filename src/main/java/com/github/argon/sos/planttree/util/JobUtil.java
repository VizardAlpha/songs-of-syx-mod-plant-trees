package com.github.argon.sos.planttree.util;

import com.github.argon.sos.planttree.log.Logger;
import com.github.argon.sos.planttree.log.Loggers;
import settlement.job.Job;
import settlement.main.SETT;

public class JobUtil {
    private final static Logger log = Loggers.getLogger(JobUtil.class);

    public static void addClearsJob(Job job) {
        int pos = SETT.JOBS().clearss.placers.length - 1;
        SETT.JOBS().clearss.placers[pos] = job.placer();

        log.debug("Added job class '%s' to clears jobs", job.getClass().getSimpleName());
    }
}
