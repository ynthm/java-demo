package com.ynthm.tools.designpattern.bulider;

/**
 * Author : Ynthm
 */
public class Director {
    public Product construct(Builder builder) {
        builder.buildPart1();
        builder.buildPart2();
        return builder.getProduct();
    }
}
