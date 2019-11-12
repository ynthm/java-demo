package com.ynthm.tools.json.bean;

import lombok.Data;

@Data
public class Result {

    private Location location;
    private int precise;
    private int confidence;
}
