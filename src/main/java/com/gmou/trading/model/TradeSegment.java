package com.gmou.trading.model;

/**
 * Created by gmou on 15/11/17.
 */
public class TradeSegment {

    private long tradeId;
    private long userId;
    private long amount;
    private int type;

    public TradeSegment(long tradeId, long userId, long amount, int type) {
        this.tradeId = tradeId;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
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

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }
}
