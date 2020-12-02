package galacom.extractor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.util.Misc;

import galacom.ui.TableContent;

public abstract class MarketExtractor implements TableContent {

    protected String commodityId;
    protected List<MarketAPI> markets;

    protected MarketExtractor(String commodityId, EconomyAPI economy) {
        this.commodityId = commodityId;
        this.markets = economy.getMarketsCopy();
    }

    @Override
    public List<Object[]> getRows() {
        List<Object[]> content = new ArrayList<>();
        for (MarketAPI market : markets) {
            Object[] row = getRow(market);
            content.add(row);
        }
        return content;
    }

    protected abstract Object[] getRow(MarketAPI market);

    protected Color getClaimingFactionColor(MarketAPI market) {
        FactionAPI faction = Misc.getClaimingFaction(market.getPrimaryEntity());
        if (faction == null) {
            return Misc.getGrayColor();
        }
        return faction.getColor();
    }

    protected String getSystemName(MarketAPI market) {
        StarSystemAPI starSystem = market.getStarSystem();
        if (starSystem == null) {
            return "Hyperspace";
        }
        return starSystem.getBaseName();
    }
}
