package com.minkyu.springtddstudy.domain.membership.service;

/**
 * Created by MinKyu Kim
 * Created on 2022-12-08.
 **/
public class RatePointService implements PointService{
    private static final int POINT_RATE = 1;

    @Override
    public int calculatePoint(int price) {
        return price * POINT_RATE / 100;
    }
}
