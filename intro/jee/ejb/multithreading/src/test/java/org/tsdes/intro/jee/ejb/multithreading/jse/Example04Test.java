package org.tsdes.intro.jee.ejb.multithreading.jse;

import org.tsdes.intro.jee.ejb.multithreading.Counter;
import org.tsdes.intro.jee.ejb.multithreading.CounterTestBase;

public class Example04Test extends CounterTestBase {

    @Override
    protected Counter getCounter() {
        return new Example04();
    }
}