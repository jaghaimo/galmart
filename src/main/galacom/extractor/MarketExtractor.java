package galacom.extractor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.util.Misc;

import galacom.ui.TableContent;

public abstract class MarketExtractor implements TableContent {

    protected String commodityId;
    protected List<MarketAPI> markets;

    protected MarketExtractor(String commodityId, EconomyAPI economy) {
        this.commodityId = commodityId;
        this.markets = economy.getMarketsCopy();
    }

    protected abstract float getPrice(MarketAPI market);

    @Override
    public Object[] getHeaders(float width) {
        Object header[] = { "Market - Faction", .3f * width, "System", .3f * width, "Price", .1f * width, "Quantity",
                .15f * width, "+/-", .1f * width };
        return header;
    }

    @Override
    public List<Object[]> getRows() {
        List<Object[]> content = new ArrayList<>();
        for (MarketAPI market : markets) {
            Object[] row = new Object[15];
            // Market
            row[0] = Alignment.MID;
            row[1] = market.getTextColorForFactionOrPlanet();
            row[2] = market.getName() + " - " + market.getFaction().getDisplayName();
            // System
            row[3] = Alignment.MID;
            row[4] = getClaimingFactionColor(market);
            row[5] = getSystemName(market);
            // Buy
            row[6] = Alignment.MID;
            row[7] = Misc.getHighlightColor();
            row[8] = Misc.getDGSCredits(getPrice(market));
            // Quantity
            CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
            row[9] = Alignment.MID;
            row[10] = Misc.getTextColor();
            row[11] = String.format("%.0f", commodity.getStockpile());
            // +/-
            row[12] = Alignment.MID;
            row[13] = getSupplyOrDemandColor(commodity.getExcessQuantity(), commodity.getDeficitQuantity());
            row[14] = getSupplyOrDemandValue(commodity.getExcessQuantity(), commodity.getDeficitQuantity());
            // done
            content.add(row);
        }
        return content;
    }

    private Color getSupplyOrDemandColor(int excess, int deficit) {
        if (excess > 0) {
            return Misc.getPositiveHighlightColor();
        }
        if (deficit > 0) {
            return Misc.getNegativeHighlightColor();
        }
        return Misc.getTextColor();
    }

    private String getSupplyOrDemandValue(int excess, int deficit) {
        if (excess > 0) {
            return String.valueOf(excess);
        }
        if (deficit > 0) {
            return String.valueOf(deficit);
        }
        return "";

    }

    private Color getClaimingFactionColor(MarketAPI market) {
        FactionAPI faction = Misc.getClaimingFaction(market.getPrimaryEntity());
        if (faction == null) {
            return Misc.getTextColor();
        }
        return faction.getColor();
    }

    private String getSystemName(MarketAPI market) {
        StarSystemAPI starSystem = market.getStarSystem();
        if (starSystem == null) {
            return "Hyperspace";
        }
        return starSystem.getName();
    }
}
