package galmart.extractor;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;

import galmart.intel.GalmartBoard.CommodityTab;

public class PriceFactory {

    private EconomyAPI economy;

    public PriceFactory() {
        this.economy = Global.getSector().getEconomy();
    }

    public Price get(String commodityId, CommodityTab commodityTab) {
        Price price = new DummyPrice();
        switch (commodityTab) {
            case BUY:
                price = new SupplyPrice(commodityId, economy);
                break;

            case SELL:
                price = new DemandPrice(commodityId, economy);
                break;
        }
        return price;
    }
}
