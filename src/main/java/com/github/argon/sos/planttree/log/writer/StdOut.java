package com.github.argon.sos.planttree.log.writer;

import com.github.argon.sos.planttree.util.StringUtil;

/**
 * Writes to {@link System#out} or {@link System#err}
 */
public class StdOut extends AbstractLogWriter {

    public StdOut(String prefix, String messageFormat, String name) {
        super(prefix, messageFormat, name);
    }

    @Override
    public void error(String msgPrefix, String formatMsg, Object[] args) {
        try {
            System.err.printf((messageFormat) + "%n",
                prefix,
                timestamp(),
                name,
                msgPrefix,
                String.format(formatMsg, StringUtil.stringifyValues(args)));
        } catch (Exception e) {
            problemLogging(formatMsg, args, e);
        }
    }

    @Override
    public void log(String msgPrefix, String formatMsg, Object[] args) {
        try {
            System.out.printf((messageFormat) + "%n",
                prefix,
                timestamp(),
                name,
                msgPrefix,
                String.format(formatMsg, StringUtil.stringifyValues(args)));
        } catch (Exception e) {
            problemLogging(formatMsg, args, e);
        }
    }

    @Override
    public void exception(Throwable ex) {
        System.err.println("\n" + ex.getMessage());
        ex.printStackTrace(System.err);
        System.err.println();
    }
}
