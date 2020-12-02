package galacom.extractor;

import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.OpenMarketPlugin;

public class BuyFromMarketExtractor extends SortableMarketExtractor {

    public BuyFromMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
    }

    @Override
    public Object[] getHeaders(float width) {
        return getHeader(width, "Available", "Excess");
    }

    @Override
    public List<Object[]> getRows() {
        sortMarkets();
        return super.getRows();
    }

    @Override
    protected float getPrice(MarketAPI market) {
        float econUnit = commoditySpec.getEconUnit();
        return market.getSupplyPrice(commodityId, econUnit, true) / econUnit;
    }

    @Override
    protected Object[] getRow(MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        int available = getAvailable(commodity);
        if (available <= 0) {
            return null;
        }
        int excess = commodity.getExcessQuantity();
        return getRow(market, commodity, available, excess);
    }

    private int getAvailable(CommodityOnMarketAPI commodity) {
        int available = OpenMarketPlugin.getApproximateStockpileLimit(commodity);
        available += commodity.getPlayerTradeNetQuantity();
        return available;
    }
}
