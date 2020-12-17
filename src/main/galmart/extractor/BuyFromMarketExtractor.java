package galmart.extractor;

import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class BuyFromMarketExtractor extends MarketExtractor {

    public BuyFromMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
    }

    @Override
    public Object[] getHeaders(float width) {
        return getHeader(width, "Available", "Excess");
    }

    @Override
    public List<MarketAPI> getMarkets() {
        sortMarkets();
        return markets;
    }

    @Override
    public List<Object[]> getRows() {
        sortMarkets();
        return super.getRows();
    }

    @Override
    public float getPrice(MarketAPI market) {
        return helper.getSupplyPrice(market);
    }

    @Override
    protected Object[] getRow(MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        int available = helper.getAvailable(commodity);
        // TODO replace with filter
        if (available <= 0) {
            return null;
        }
        int excess = commodity.getExcessQuantity();
        return getRow(market, commodity, available, excess);
    }
}
