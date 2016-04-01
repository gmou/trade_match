package com.gmou.trading;

import com.gmou.trading.core.TradingMarket2;
import com.gmou.trading.model.Trade;
import org.junit.Test;

/**
 * Created by gmou on 15/11/17.
 */
public class App {

    @Test
    public void test() {
        TradingMarket2.addSell(new Trade(1, 1, 200, Trade.TradeType.SELL.code));
        TradingMarket2.addSell(new Trade(2, 2, 200, Trade.TradeType.SELL.code));
        TradingMarket2.addSell(new Trade(3, 3, 300, Trade.TradeType.SELL.code));

        TradingMarket2.addBuy(new Trade(4, 6, 100, Trade.TradeType.BUY.code));
        TradingMarket2.addBuy(new Trade(5, 6, 200, Trade.TradeType.BUY.code));
        TradingMarket2.addBuy(new Trade(6, 5, 300, Trade.TradeType.BUY.code));
        TradingMarket2.addBuy(new Trade(7, 5, 200, Trade.TradeType.BUY.code));
        TradingMarket2.addBuy(new Trade(8, 5, 400, Trade.TradeType.BUY.code));
        TradingMarket2.addBuy(new Trade(9, 5, 100, Trade.TradeType.BUY.code));
        TradingMarket2.addBuy(new Trade(10, 5, 50, Trade.TradeType.BUY.code));

        TradingMarket2.trading();
    }

}
