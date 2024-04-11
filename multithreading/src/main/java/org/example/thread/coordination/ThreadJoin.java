package org.example.thread.coordination;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ThreadJoin {

    /*
    * we have a list of numbers that we want to calculate Their factorial.
    *  Factorial calculation is a CPU intensive task, which involves a lot of multiplications.
    *  So we would like to utilize multithreading to delegate the calculation of each number's
    *  factorial to a different thread. This way we can calculate all the numbers factorial in parallel.
    *  So let's look at our factorial calculation thread.
    * */

    public static void main(String[] args) {
        List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);
    }
}
