package galmart.extractor;

import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class SupplyPrice implements Price {

    private String commodityId;
    private CommoditySpecAPI commoditySpec;

    public SupplyPrice(String commodityId, EconomyAPI economy) {
        this.commodityId = commodityId;
        this.commoditySpec = economy.getCommoditySpec(commodityId);
    }

    @Override
    public float getPrice(MarketAPI market) {
        float econUnit = commoditySpec.getEconUnit();
        return market.getSupplyPrice(commodityId, econUnit, true) / econUnit;
    }
}
