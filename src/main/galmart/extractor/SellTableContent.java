package galmart.extractor;

import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class SellTableContent extends MarketTableContent {

    public SellTableContent(String commodityId, List<MarketAPI> markets, EconomyAPI economy) {
        super(commodityId, markets, new DemandPrice(commodityId, economy));
    }

    @Override
    public Object[] getHeaders(float width) {
        return getHeader(width, "Demand", "Deficit");
    }

    @Override
    protected Object[] getRow(MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        float price = getPrice(market);
        int demand = helper.getDemand(market, commodity);
        int deficit = -commodity.getDeficitQuantity();
        return getRow(market, commodity, price, demand, deficit);
    }
}
