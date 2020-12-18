package galmart.extractor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import galmart.CollectionsHelper;
import galmart.filter.market.CommodityAvailableFilter;

public class BuyFromMarketFactory extends MarketFactory {

    private SupplyPrice price;

    public BuyFromMarketFactory(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
        this.price = new SupplyPrice(commodityId, economy);
    }

    @Override
    protected void filterMarkets(List<MarketAPI> markets) {
        CollectionsHelper.reduce(markets, new CommodityAvailableFilter(commodityId, economy));
    }

    @Override
    protected void sortMarkets(List<MarketAPI> markets) {
        Collections.sort(markets, new Comparator<MarketAPI>() {

            @Override
            public int compare(MarketAPI marketA, MarketAPI marketB) {
                float priceA = getPrice(marketA);
                float priceB = getPrice(marketB);
                return (int) Math.signum(priceA - priceB);
            }
        });
    }

    private float getPrice(MarketAPI market) {
        return price.getPrice(market);
    }
}
