package galmart.extractor;

import java.util.List;

import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import galmart.CollectionsHelper;
import galmart.filter.market.MarketNotHidden;

public abstract class MarketFactory {

    protected String commodityId;
    protected EconomyAPI economy;

    public MarketFactory(String commodityId, EconomyAPI economy) {
        this.commodityId = commodityId;
        this.economy = economy;
    }

    public List<MarketAPI> getMarkets() {
        List<MarketAPI> markets = economy.getMarketsCopy();
        CollectionsHelper.reduce(markets, new MarketNotHidden());
        filterMarkets(markets);
        sortMarkets(markets);
        return markets;
    }

    protected abstract void filterMarkets(List<MarketAPI> markets);

    protected abstract void sortMarkets(List<MarketAPI> markets);
}
