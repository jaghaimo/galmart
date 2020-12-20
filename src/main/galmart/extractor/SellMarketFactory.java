package galmart.extractor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import galmart.CollectionsHelper;
import galmart.filter.market.CommodityDemandFilter;

public class SellMarketFactory extends MarketFactory {

    private DemandPrice price;

    public SellMarketFactory(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
        this.price = new DemandPrice(commodityId, economy);
    }

    @Override
    protected void filterMarkets(List<MarketAPI> markets) {
        CollectionsHelper.reduce(markets, new CommodityDemandFilter(commodityId, economy));
    }

    @Override
    protected void sortMarkets(List<MarketAPI> markets) {
        Collections.sort(markets, new Comparator<MarketAPI>() {

            @Override
            public int compare(MarketAPI marketA, MarketAPI marketB) {
                float priceA = getPrice(marketA);
                float priceB = getPrice(marketB);
                return (int) Math.signum(priceB - priceA);
            }
        });
    }

    private float getPrice(MarketAPI market) {
        return price.getPrice(market);
    }
}
