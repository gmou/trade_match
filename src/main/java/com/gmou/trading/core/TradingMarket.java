package com.gmou.trading.core;

import com.gmou.trading.model.Trade;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by gmou on 15/11/17.
 */
public class TradingMarket {

    // 接受帮助(卖方)
    public static PriorityBlockingQueue<Trade> sells = new PriorityBlockingQueue<Trade>(1000, new Comparator<Trade>() {
        public int compare(Trade o1, Trade o2) {
            return o1.getAmount() > o2.getAmount()? 1 : (o1.getAmount() == o2.getAmount() ? 0 : -1);
        }
    });

    // 提供帮助(买方)
    public static PriorityBlockingQueue<Trade> buys = new PriorityBlockingQueue<Trade>(1000, new Comparator<Trade>() {
        public int compare(Trade o1, Trade o2) {
            return o1.getAmount() > o2.getAmount()? 1 : (o1.getAmount() == o2.getAmount() ? 0 : -1);
        }
    });

    // 已完成列表
    public static List<Trade> dones = new ArrayList<Trade>(1000);

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

        System.out.println("** begin **");

        while (sell != null && buy != null) {

            System.out.println(String.format("\n开始进行交易:id:%s, Amount:%s, uid:%s", sell.getId(), sell.getAmount(), sell.getUserId()));

//            long left = sell.getAmount();

            while(buy != null && sell.getAmount() > 0) {
                // 此次交易的金额
                long tradeAmount = buy.getAmount() > sell.getAmount() ? sell.getAmount() : buy.getAmount();

                System.out.println(String.format(">>交易:%s from:%s id:%s", tradeAmount, buy.getUserId(), buy.getId()));

                sell.setAmount(sell.getAmount() - tradeAmount);

                buy.setAmount(buy.getAmount() - tradeAmount);
                sell.addSegment(buy.getUserId(), tradeAmount, sell.getType());

                if(sell.getAmount() == 0) {
                    dones.add(sell);
                }

                if(buy.getAmount() == 0) {
                    buy = buys.poll();
                }

            }

            if(sell.getAmount() > 0) {
                break;
            }

            sell = sells.poll();
        }

        System.out.println("\n** end **");

        if(sell != null && sell.getAmount() > 0) {
            sells.add(sell);
        }

        if(buy != null && buy.getAmount() > 0) {
            buys.add(buy);
        }

        System.out.println("\n未完成的卖单:");
        display(sells);

        System.out.println("\n外完成的买单:");
        display(buys);

    }

    public static void display(PriorityBlockingQueue<Trade> queue) {
        Trade trade = queue.poll();

        while(trade != null) {
            System.out.println(String.format("id:%s, Amount:%s, uid:%s", trade.getId(), trade.getAmount(), trade.getUserId()));
            trade = queue.poll();
        }
    }
}
