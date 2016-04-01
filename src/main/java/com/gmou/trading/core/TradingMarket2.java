package com.gmou.trading.core;

import com.gmou.trading.model.Trade;
import com.gmou.trading.model.TradeComparator;
import com.gmou.trading.model.TradeSegment;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by gmou on 15/11/17.
 */
public class TradingMarket2 {

    // 接受帮助(卖方)
    public static PriorityBlockingQueue<Trade> sells = new PriorityBlockingQueue<Trade>(1000, new TradeComparator());

    // 提供帮助(买方)
    public static PriorityBlockingQueue<Trade> buys = new PriorityBlockingQueue<Trade>(1000, new TradeComparator());

    public static void addSell(Trade trade) {
        sells.offer(trade);
    }
    public static void addBuy(Trade trade) {
        buys.offer(trade);
    }

    // 进行撮合
    public static void trading() {

        // 以sells为纬度进行撮合
        Trade sell = sells.poll();

        System.out.println("** begin **");

        while (sell != null) {

//            System.out.println(String.format("\n开始进行交易:买家id:%s, Amount:%s, uid:%s", sell.getId(), sell.getLeftAmount(), sell.getUserId()));

            // 获取交易买单，判断是否符合条件(最小成交金额)
            Trade buy = buys.poll();

            while (buy != null && buy.getLeftAmount() < sell.getMinTradeAmount()) {
                buy = buys.poll();
            }
            if (buy == null || buy.getLeftAmount() < sell.getMinTradeAmount()) {
                handleDoneTrade(sell);
                break;
            }

            // 判断卖单是否符合条件
            while (sell != null && sell.getLeftAmount() < buy.getMinTradeAmount()) {
                handleDoneTrade(sell);
                sell = sells.poll();
            }
            if (sell == null || sell.getLeftAmount() < buy.getMinTradeAmount()) {
                break;
            }

            // 此次交易的金额
            long tradeAmount = buy.getLeftAmount() > sell.getLeftAmount() ? sell.getLeftAmount() : buy.getLeftAmount();

//            System.out.println(String.format(">>交易:%s from:%s id:%s", tradeAmount, buy.getUserId(), buy.getId()));

            sell.setLeftAmount(sell.getLeftAmount() - tradeAmount);
            buy.setLeftAmount(buy.getLeftAmount() - tradeAmount);
            sell.addOther(buy);
            sell.addSegment(buy.getId(), buy.getUserId(), tradeAmount, sell.getType());
            buy.addSegment(sell.getId(), sell.getUserId(), tradeAmount, buy.getType());


            if (sell.getLeftAmount() == 0) {
                handleDoneTrade(sell);
                sell = sells.poll();
            }
        }

        System.out.println("\n** end **");
    }

    public static void display(Trade trade) {
        System.out.println("订单:" + String.format("交易id:%s, 用户id:%s, Amount:%s, uid:%s", trade.getId(), trade.getUserId(), trade.getAmount(), trade.getUserId()));
        for (TradeSegment tradeSegment : trade.getSegments()) {
            System.out.println(String.format(">>交易id:%s, 用户id:%s, amount:%s", tradeSegment.getTradeId(), tradeSegment.getUserId(), tradeSegment.getAmount()));
        }
    }

    public static void display(List<Trade> trades) {
        if (trades != null && !trades.isEmpty()) {
            for (Trade trade : trades) {
                display(trade);
            }
        }
    }

    public static void handleDoneTrade(Trade trade) {
        if(trade.isHasChange()) {
            System.out.println("==this==");
            display(trade);
            System.out.println("==other==");
            display(trade.getOthers());
            System.out.println();
        }
    }
}
