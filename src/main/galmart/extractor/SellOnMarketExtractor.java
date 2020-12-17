package galmart.extractor;

import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class SellOnMarketExtractor extends MarketExtractor {

    public SellOnMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
    }

    @Override
    public Object[] getHeaders(float width) {
        return getHeader(width, "Demand", "Deficit");
    }

    @Override
    public List<Object[]> getRows() {
        sortMarkets();
        return super.getRows();
    }

    @Override
    public List<MarketAPI> getMarkets() {
        sortMarkets();
        return markets;
    }

    @Override
    public float getPrice(MarketAPI market) {
        return helper.getDemandPrice(market);
    }

    @Override
    protected Object[] getRow(MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        int demand = helper.getDemand(market, commodity);
        // TODO replace with filter
        if (demand <= 0) {
            return null;
        }
        int deficit = -commodity.getDeficitQuantity();
        return getRow(market, commodity, demand, deficit);
    }

    @Override
    protected void sortMarkets() {
        super.sortMarkets();
        Collections.reverse(markets);
    }
}
