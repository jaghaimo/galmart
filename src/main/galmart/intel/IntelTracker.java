package galmart.intel;

import java.util.HashMap;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class IntelTracker extends HashMap<String, GalmartIntel> {

    private static final long serialVersionUID = 1L;
    private IntelManager manager;

    public IntelTracker() {
        super();
        manager = new IntelManager();
    }

    public boolean has(String action, String commodityId, MarketAPI market) {
        String key = getKey(action, commodityId, market);
        GalmartIntel intel = get(key);
        return intel != null;
    }

    public void toggle(String action, String commodityId, MarketAPI market) {
        String key = getKey(action, commodityId, market);
        GalmartIntel intel = get(key);
        if (intel == null) {
            intel = new GalmartIntel(action, commodityId, market);
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
