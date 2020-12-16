package galmart.extractor;

import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class SellOnMarketExtractor extends SortableMarketExtractor {

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
        Collections.reverse(markets);
        return super.getRows();
    }

    @Override
    protected float getPrice(MarketAPI market) {
        float econUnit = commoditySpec.getEconUnit();
        return market.getDemandPrice(commodityId, econUnit, true) / econUnit;
    }

    @Override
    protected Object[] getRow(MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        int demand = getDemand(market, commodity);
        if (demand <= 0) {
            return null;
        }
        int deficit = -commodity.getDeficitQuantity();
        return getRow(market, commodity, demand, deficit);
    }

    private int getDemand(MarketAPI market, CommodityOnMarketAPI commodity) {
        int demandIcons = commodity.getMaxDemand();
        if (!commodity.getCommodity().isPrimary()) {
            CommodityOnMarketAPI primary = market.getCommodityData(commodity.getCommodity().getDemandClass());
            demandIcons = primary.getMaxDemand();
        }
        int demand = (int) (commodity.getCommodity().getEconUnit() * demandIcons);
        demand -= commodity.getPlayerTradeNetQuantity();
        return demand;
    }
}
