package galmart.extractor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.util.Misc;

import galmart.ui.TableContent;

public abstract class MarketExtractor implements TableContent {

    protected String commodityId;
    protected List<MarketAPI> markets;

    protected MarketExtractor(String commodityId, EconomyAPI economy) {
        this.commodityId = commodityId;
        this.markets = economy.getMarketsCopy();
    }

    @Override
    public Object[] getHeaders(float width) {
        return getHeader(width, "", "");
    }

    @Override
    public List<Object[]> getRows() {
        List<Object[]> content = new ArrayList<>();
        for (MarketAPI market : markets) {
            Object[] row = getRow(market);
            if (row == null) {
                continue;
            }
            content.add(row);
        }
        return content;
    }

    public String getDistance(MarketAPI market) {
        float distance = Misc.getDistanceToPlayerLY(market.getPrimaryEntity());
        return String.format("%.1f", distance);
    }

    public String getLocation(MarketAPI market) {
        return market.getName() + " - " + market.getFaction().getDisplayName();
    }

    public List<MarketAPI> getMarkets() {
        return markets;
    }

    public String getSystemName(MarketAPI market) {
        StarSystemAPI starSystem = market.getStarSystem();
        if (starSystem == null) {
            return "Hyperspace";
        }
        return starSystem.getBaseName();
    }

    protected Object[] getHeader(float width, String availableOrDemand, String excessOrDeficit) {
        Object header[] = { "Price", .10f * width, availableOrDemand, .1f * width, excessOrDeficit, .1f * width,
                "Location", .35f * width, "Star system", .2f * width, "Dist (ly)", .1f * width };
        return header;
    }

    protected Object[] getRow(MarketAPI market) {
        return new Object[18];
    }

    protected Color getClaimingFactionColor(MarketAPI market) {
        FactionAPI faction = Misc.getClaimingFaction(market.getPrimaryEntity());
        if (faction == null) {
            return Misc.getGrayColor();
        }
        return faction.getColor();
    }
}
