package galmart.intel;

import java.util.HashMap;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import galmart.extractor.Price;
import galmart.extractor.PriceFactory;
import galmart.intel.GalmartBoard.CommodityTab;

public class IntelTracker extends HashMap<String, GalmartIntel> {

    private static final long serialVersionUID = 1L;
    private IntelManager manager;
    private PriceFactory priceFactory;

    public IntelTracker() {
        super();
        manager = new IntelManager();
        priceFactory = new PriceFactory();
    }

    public void remove(GalmartIntel intel) {
        String key = getKey(intel.getAction(), intel.getCommodityId(), intel.getMarket());
        manager.remove(intel);
        remove(key);
    }

    public boolean has(String action, String commodityId, MarketAPI market) {
        String key = getKey(action, commodityId, market);
        GalmartIntel intel = get(key);
        return intel != null;
    }

    public void toggle(String commodityId, CommodityTab commodityTab, MarketAPI market) {
        String action = commodityTab.title;
        String key = getKey(action, commodityId, market);
        GalmartIntel intel = get(key);
        if (intel == null) {
            CommoditySpecAPI commodity = Global.getSector().getEconomy().getCommoditySpec(commodityId);
            Price price = priceFactory.get(commodityId, commodityTab);
            intel = new GalmartIntel(action, commodity, market, this, price);
            manager.add(intel);
            put(key, intel);
        } else {
            manager.remove(intel);
            remove(key);
        }
    }

    private String getKey(String action, String commodityId, MarketAPI market) {
        return action + ":" + commodityId + ":" + market.getName();
    }
}
