package galmart.intel;

import java.util.LinkedList;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import galmart.GalmartMod;
import galmart.extractor.BuyFromMarketFactory;
import galmart.extractor.MarketFactory;
import galmart.extractor.SellOnMarketFactory;

public class IntelFactory {

    private EconomyAPI economy;

    public IntelFactory() {
        this.economy = Global.getSector().getEconomy();
    }

    public List<GalmartIntel> get(String commodityId) {
        List<GalmartIntel> intels = new LinkedList<>();
        intels.addAll(getIntels("Buy", commodityId, new BuyFromMarketFactory(commodityId, economy)));
        intels.addAll(getIntels("Sell", commodityId, new SellOnMarketFactory(commodityId, economy)));
        return intels;
    }

    private List<MarketAPI> getMarkets(MarketFactory factory) {
        List<MarketAPI> markets = factory.getMarkets();
        return markets.subList(0, GalmartMod.numberOfIntelToShow);
    }

    private List<GalmartIntel> getIntels(String action, String commodityId, MarketFactory factory) {
        List<GalmartIntel> intels = new LinkedList<>();
        for (MarketAPI market : getMarkets(factory)) {
            GalmartIntel intel = new GalmartIntel(action, commodityId, market);
            intels.add(intel);
        }
        return intels;
    }
}
