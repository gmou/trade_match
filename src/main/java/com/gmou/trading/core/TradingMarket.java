package com.gmou.trading.core;

import com.gmou.trading.model.Trade;
import com.gmou.trading.model.TradeComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by gmou on 15/11/17.
 */
public class TradingMarket {

    // 接受帮助(卖方)
    public static PriorityQueue<Trade> sells = new PriorityQueue<Trade>(1000, new TradeComparator());

    // 提供帮助(买方)
    public static PriorityQueue<Trade> buys = new PriorityQueue<Trade>(1000, new TradeComparator());

    // 已完成列表
    public static List<Trade> sellDones = new ArrayList<Trade>(1000);
    public static List<Trade> buyDones = new ArrayList<Trade>(1000);

    // 添加交易信息
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
        Trade buy = buys.poll();
        boolean stop = false;

        System.out.println("** begin **");

        while (sell != null && buy != null && !stop) {

            System.out.println(String.format("\n开始进行交易:买家id:%s, Amount:%s, uid:%s", sell.getId(), sell.getLeftAmount(), sell.getUserId()));

            while(buy != null && sell.getLeftAmount() > 0) {
                // 此次交易的金额
                long tradeAmount = buy.getAmount() > sell.getAmount() ? sell.getAmount() : buy.getAmount();

                // 卖单过小
                while(buy != null && tradeAmount < sell.getMinTradeAmount()) {
                    buyDones.add(buy);
                    buy = buys.poll();
                }

                if(buy == null || tradeAmount < sell.getMinTradeAmount()) {
                    stop = true;
                    break;
                }

                // 买单过小
                if(sell != null && tradeAmount < buy.getMinTradeAmount()) {
                    sellDones.add(sell);
                    sell = sells.poll();
                }

                System.out.println(String.format(">>交易:%s from:%s id:%s", tradeAmount, buy.getUserId(), buy.getId()));

                sell.setLeftAmount(sell.getLeftAmount() - tradeAmount);
                buy.setLeftAmount(buy.getLeftAmount() - tradeAmount);

                sell.addSegment(buy.getId(), buy.getUserId(), tradeAmount, sell.getType());
                buy.addSegment(sell.getId(), sell.getUserId(), tradeAmount, buy.getType());

                if(sell.getLeftAmount() == 0) {
                    sellDones.add(sell);
                }

                if(buy.getLeftAmount() == 0) {
                    buy = buys.poll();
                }

            }

            if(sell.getLeftAmount() > 0) {
                break;
            }

            sell = sells.poll();
        }

        System.out.println("\n** end **");

        if(sell != null && sell.getLeftAmount() > 0) {
            sells.add(sell);
        }

        if(buy != null && buy.getLeftAmount() > 0) {
            buys.add(buy);
        }

        System.out.println("\n未完成的卖单:");
        display(sells);

        System.out.println("\n未完成的买单:");
        display(buys);

    }

    public static void display(PriorityQueue<Trade> queue) {
        Trade trade = queue.poll();

        while(trade != null) {
            System.out.println(String.format("id:%s, Amount:%s, uid:%s", trade.getId(), trade.getAmount(), trade.getUserId()));
            trade = queue.poll();
        }
    }
}
