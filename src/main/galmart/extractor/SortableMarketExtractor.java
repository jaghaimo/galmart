package galmart.extractor;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.util.Misc;

public abstract class SortableMarketExtractor extends MarketExtractor {

    protected CommoditySpecAPI commoditySpec;

    protected SortableMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
        commoditySpec = economy.getCommoditySpec(commodityId);
    }

    protected Object[] getRow(MarketAPI market, CommodityOnMarketAPI commodity, int available, int excess) {
        Object[] row = super.getRow(market);
        // Price
        row[0] = Alignment.MID;
        row[1] = Misc.getHighlightColor();
        row[2] = Misc.getDGSCredits(getPrice(market));
        // Available or Demand
        row[3] = Alignment.MID;
        row[4] = Misc.getHighlightColor();
        row[5] = Misc.getWithDGS(available);
        // Excess or Deficit
        row[6] = Alignment.MID;
        row[7] = getExcessColor(excess);
        row[8] = getExcessValue(excess);
        // Location
        row[9] = Alignment.LMID;
        row[10] = market.getTextColorForFactionOrPlanet();
        row[11] = getLocation(market);
        // Star system
        row[12] = Alignment.MID;
        row[13] = getClaimingFactionColor(market);
        row[14] = getSystemName(market);
        // Distance
        row[15] = Alignment.MID;
        row[16] = Misc.getHighlightColor();
        row[17] = getDistance(market);

        return row;
    }

    protected void sortMarkets() {
        Collections.sort(markets, new Comparator<MarketAPI>() {

            @Override
            public int compare(MarketAPI marketA, MarketAPI marketB) {
                float priceA = getPrice(marketA);
                float priceB = getPrice(marketB);
                return (int) Math.signum(priceA - priceB);
            }
        });
    }

    protected abstract float getPrice(MarketAPI market);

    private Color getExcessColor(int excess) {
        if (excess > 0) {
            return Misc.getPositiveHighlightColor();
        }
        if (excess < 0) {
            return Misc.getNegativeHighlightColor();
        }
        return Misc.getGrayColor();
    }

    private String getExcessValue(int excess) {
        if (excess > 0) {
            return Misc.getWithDGS(excess);
        }
        if (excess < 0) {
            return Misc.getWithDGS(-excess);
        }
        return "---";
    }
}
