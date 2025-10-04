package com.github.argon.sos.planttree.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionUtil {

    /**
     * @return when the last argument is a Throwable, it will be returned
     */
    public static Throwable extractThrowableLast(Object[] args) {
        Object lastArg = null;
        int lastPos = args.length - 1;

        if (lastPos >= 0) {
            lastArg = args[lastPos];
        }

        if (lastArg instanceof Throwable) {
            return (Throwable) lastArg;
        }

        return null;
    }
}
