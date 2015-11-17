package com.gmou.trading.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gmou on 15/11/17.
 */
public class Trade implements Comparable<Trade> {

    public static AtomicLong count = new AtomicLong();

    private long id;
    private String tid;
    private long userId;
    private long amount;
    private int type;

    public static final int TYPE_SELL = 0;
    public static final int TYPE_BUY = 0;

    private List<TradeSegment> segments = Lists.newArrayList();

    public List<TradeSegment> getSegments() {
        return segments;
    }

    public Trade() {
    }

    public Trade(long userId, long amount, int type) {
        this.id = count.incrementAndGet();
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.tid = UUID.randomUUID().toString();
        this.tid.replace("-", "");
    }

    public Trade(long id, long userId, long amount, int type) {
        this.id = id; //count.incrementAndGet();
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.tid = UUID.randomUUID().toString();
        this.tid.replace("-", "");
    }

    public void setSegments(List<TradeSegment> segments) {
        this.segments = segments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public void addSegment(long userId, long amount, int type) {
        this.segments.add(new TradeSegment(userId, amount, type));
    }

    public int compareTo(Trade o) {
        return this.getAmount() > this.getAmount() ? 1 : (this.getAmount() == this.getAmount() ? 0 : -1);
    }
}
