package com.gmou.trading.model;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gmou on 15/11/17.
 */
public class Trade {

    public static AtomicLong count = new AtomicLong();
    // 最大拆单数
    public static int MAX_TRADE_NUM = 5;

    private long id;
    private String tid;
    private long userId;
    private long amount;
    private long leftAmount;        // 此单未成交金额
    private long minTradeAmount;    // 此单拆单最小成交金额
    private int type;
    private boolean hasChange;

    private List<Trade> others = new ArrayList<Trade>();

    public enum TradeType {
        SELL(0),BUY(1);
        public int code;
        TradeType(int code) {
            this.code = code;
        }
    }

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
        this.leftAmount = amount;
        this.type = type;
        this.tid = UUID.randomUUID().toString();
        this.tid.replace("-", "");
    }

    public Trade(long id, long userId, long amount, int type) {
        this.id = id; //count.incrementAndGet();
        this.userId = userId;
        this.amount = amount;
        this.leftAmount = amount;
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

    public long getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(long leftAmount) {
        this.hasChange = true;
        this.leftAmount = leftAmount;
    }

    public long getMinTradeAmount() {
        if(MAX_TRADE_NUM == 0) {
            return 0;
        }
        double m = Math.ceil((double)leftAmount/MAX_TRADE_NUM);
        return (long)m;
    }

    public void setMinTradeAmount(long minTradeAmount) {
        this.minTradeAmount = minTradeAmount;
    }

    public boolean isHasChange() {
        return hasChange;
    }

    public void setHasChange(boolean hasChange) {
        this.hasChange = hasChange;
    }

    public List<Trade> getOthers() {
        return others;
    }

    public void setOthers(List<Trade> others) {
        this.others = others;
    }

    public void addOther(Trade trade) {
        this.others.add(trade);
    }

    public void addSegment(long tradeId, long userId, long amount, int type) {
        this.segments.add(new TradeSegment(tradeId, userId, amount, type));
    }
}
