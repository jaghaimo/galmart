package galacom.extractor;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.util.Misc;

public class SellOnMarketExtractor extends SortableMarketExtractor {

    public SellOnMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
    }

    @Override
    public Object[] getHeaders(float width) {
        Object header[] = { //
                "Price", .10f * width, //
                "Demand", .1f * width, //
                "Deficit", .1f * width, //
                "Location", .35f * width, //
                "Star system", .2f * width, //
                "Distance", .1f * width //
        };
        return header;
    }

    @Override
    public List<Object[]> getRows() {
        sortMarkets();
        Collections.reverse(markets);
        return super.getRows();
    }

    @Override
    protected float getPrice(MarketAPI market) {
        return market.getDemandPrice(commodityId, 100, true) / 100;
    }

    @Override
    protected Object[] getRow(MarketAPI market) {
        Object[] row = new Object[18];
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        int deficit = commodity.getDeficitQuantity();
        // Price
        row[0] = Alignment.MID;
        row[1] = Misc.getHighlightColor();
        row[2] = Misc.getDGSCredits(getPrice(market));
        // Demand
        row[3] = Alignment.MID;
        row[4] = Misc.getHighlightColor();
        row[5] = "?";
        // Deficit
        row[6] = Alignment.MID;
        row[7] = getDeficitColor(deficit);
        row[8] = getDeficitValue(deficit);
        // Location
        row[9] = Alignment.LMID;
        row[10] = market.getTextColorForFactionOrPlanet();
        row[11] = market.getName() + " - " + market.getFaction().getDisplayName();
        // Star system
        row[12] = Alignment.MID;
        row[13] = getClaimingFactionColor(market);
        row[14] = getSystemName(market);
        // Distance
        row[15] = Alignment.MID;
        row[16] = Misc.getHighlightColor();
        row[17] = "?";

        return row;
    }

    private Color getDeficitColor(int deficit) {
        if (deficit > 0) {
            return Misc.getNegativeHighlightColor();
        }
        return Misc.getGrayColor();
    }

    private String getDeficitValue(int deficit) {
        if (deficit > 0) {
            return String.valueOf(deficit);
        }
        return "---";
    }
}
