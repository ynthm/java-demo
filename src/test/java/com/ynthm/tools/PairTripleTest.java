package com.ynthm.tools;

import com.ynthm.tools.tuple.Tuple4;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

public class PairTripleTest {

    @Test
    void test() {
        ImmutablePair<Integer, String> pair = ImmutablePair.of(100, "ynthm.com");

        System.out.println(pair.getKey());
        System.out.println(pair.getRight());


        System.out.println(pair.equals(ImmutablePair.of(100, "ynthm.com")));

        System.out.println(pair.equals(ImmutablePair.of(100, "wang.com")));


        Triple<Integer, String, Integer> t1 = Triple.of(1, "Ethan", 18);
        System.out.println(t1.getMiddle());

        Tuple4<Integer, String, String, Integer> t4 = Tuple4.of(1, "Ethan", "Wang", 18);
        System.out.println(t4.getValue2());


    }
}
