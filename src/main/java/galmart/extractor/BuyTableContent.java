package galmart.extractor;

import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class BuyTableContent extends MarketTableContent {

    public BuyTableContent(String commodityId, List<MarketAPI> markets, EconomyAPI economy) {
        super(commodityId, markets, new SupplyPrice(commodityId, economy));
    }

    @Override
    public Object[] getHeaders(float width) {
        return getHeader(width, "Available", "Excess");
    }

    @Override
    protected Object[] getRow(int i, MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        float price = getPrice(market);
        int available = helper.getAvailable(commodity);
        int excess = commodity.getExcessQuantity();
        return getRow(i, market, commodity, price, available, excess);
    }
}
