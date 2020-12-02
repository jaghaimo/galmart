package galacom.extractor;

import java.util.Collections;
import java.util.Comparator;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public abstract class SortableMarketExtractor extends MarketExtractor {

    protected SortableMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
    }

    protected void sortMarkets() {
        Collections.sort(markets, new Comparator<MarketAPI>() {

            @Override
            public int compare(MarketAPI marketA, MarketAPI marketB) {
                float priceA = getPrice(marketA);
                float priceB = getPrice(marketB);
                return (int) Math.signum(priceA - priceB);
            }
        });
    }

    protected abstract float getPrice(MarketAPI market);
}
