package galmart.intel;

import java.util.LinkedList;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import galmart.GalmartMod;
import galmart.extractor.BuyFromMarketExtractor;
import galmart.extractor.MarketExtractor;
import galmart.extractor.SellOnMarketExtractor;

public class IntelFactory {

    private EconomyAPI economy;

    public IntelFactory(EconomyAPI economy) {
        this.economy = economy;
    }

    public List<GalmartIntel> get(String commodityId) {
        List<GalmartIntel> intels = new LinkedList<>();
        intels.addAll(getIntels("Buy", commodityId, new BuyFromMarketExtractor(commodityId, economy)));
        intels.addAll(getIntels("Sell", commodityId, new SellOnMarketExtractor(commodityId, economy)));
        return intels;
    }

    private List<MarketAPI> getMarkets(MarketExtractor extractor) {
        List<MarketAPI> markets = extractor.getMarkets();
        return markets.subList(0, GalmartMod.numberOfIntelToShow);
    }

    private List<GalmartIntel> getIntels(String action, String commodityId, MarketExtractor extractor) {
        List<GalmartIntel> intels = new LinkedList<>();
        for (MarketAPI market : getMarkets(extractor)) {
            GalmartIntel intel = new GalmartIntel(action, commodityId, extractor, market);
            intels.add(intel);
        }
        return intels;
    }
}
