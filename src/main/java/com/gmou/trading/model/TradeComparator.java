package com.gmou.trading.model;

import java.util.Comparator;

/**
 * Created by gmou on 16/4/1.
 */
public class TradeComparator implements Comparator<Trade> {

    // 由小到大排序
    public int compare(Trade o1, Trade o2) {
        return o1.getLeftAmount() > o2.getLeftAmount()? 1 : (o1.getLeftAmount() == o2.getLeftAmount() ? 0 : -1);
    }
}
