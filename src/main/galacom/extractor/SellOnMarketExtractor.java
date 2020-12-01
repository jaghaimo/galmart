package galacom.extractor;

import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class SellOnMarketExtractor extends SortableMarketExtractor {

    public SellOnMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
    }

    @Override
    protected float getPrice(MarketAPI market) {
        return market.getDemandPrice(commodityId, 100, true) / 100;
    }

    @Override
    public List<Object[]> getRows() {
        sortMarkets();
        return super.getRows();
    }

    @Override
    protected void sortMarkets() {
        super.sortMarkets();
        Collections.reverse(markets);
    }
}
