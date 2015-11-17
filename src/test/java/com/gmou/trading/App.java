package com.gmou.trading;

import com.alibaba.fastjson.JSON;
import com.gmou.trading.core.TradingMarket;
import com.gmou.trading.model.Trade;
import org.junit.Test;

/**
 * Created by gmou on 15/11/17.
 */
public class App {

    @Test
    public void test() {
        TradingMarket.addSell(new Trade(1, 1, 200, Trade.TYPE_SELL));
        TradingMarket.addSell(new Trade(2, 2, 200, Trade.TYPE_SELL));
        TradingMarket.addSell(new Trade(3, 3, 300, Trade.TYPE_SELL));

        TradingMarket.addBuy(new Trade(4, 6, 100, Trade.TYPE_BUY));
        TradingMarket.addBuy(new Trade(5, 6, 200, Trade.TYPE_BUY));
        TradingMarket.addBuy(new Trade(6, 5, 300, Trade.TYPE_BUY));
        TradingMarket.addBuy(new Trade(7, 5, 200, Trade.TYPE_BUY));
        TradingMarket.addBuy(new Trade(8, 5, 400, Trade.TYPE_BUY));
        TradingMarket.addBuy(new Trade(9, 5, 100, Trade.TYPE_BUY));

        TradingMarket.trading();
    }

    @Test
    public void testQueue() {
        TradingMarket.addBuy(new Trade(6, 100, Trade.TYPE_BUY));
        TradingMarket.addBuy(new Trade(6, 200, Trade.TYPE_BUY));
        TradingMarket.addBuy(new Trade(5, 300, Trade.TYPE_BUY));
        TradingMarket.addBuy(new Trade(5, 100, Trade.TYPE_BUY));

        Trade trade = TradingMarket.buys.poll();
        while(trade != null) {
            System.out.println(JSON.toJSONString(trade));
            trade = TradingMarket.buys.poll();
        }

    }

}
