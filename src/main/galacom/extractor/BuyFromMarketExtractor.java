package galacom.extractor;

import java.util.List;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class BuyFromMarketExtractor extends SortableMarketExtractor {

    public BuyFromMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
    }

    @Override
    protected float getPrice(MarketAPI market) {
        return market.getSupplyPrice(commodityId, 100, true) / 100;
    }

    @Override
    public List<Object[]> getRows() {
        sortMarkets();
        return super.getRows();
    }
}
