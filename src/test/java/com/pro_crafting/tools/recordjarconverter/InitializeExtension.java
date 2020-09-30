package com.pro_crafting.tools.recordjarconverter;

import org.junit.jupiter.api.extension.Extension;

public class InitializeExtension implements Extension {
    static {

        int sleepBefore = Integer.parseInt(System.getProperties().getProperty("sleep.before", "0"));
        System.out.println("Sleep before test execution " + sleepBefore);
        try {
            Thread.sleep(sleepBefore);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
