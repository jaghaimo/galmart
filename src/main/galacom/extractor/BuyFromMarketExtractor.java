package galacom.extractor;

import java.awt.Color;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.OpenMarketPlugin;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.util.Misc;

public class BuyFromMarketExtractor extends SortableMarketExtractor {

    public BuyFromMarketExtractor(String commodityId, EconomyAPI economy) {
        super(commodityId, economy);
    }

    @Override
    public Object[] getHeaders(float width) {
        Object header[] = { //
                "Price", .10f * width, //
                "Available", .1f * width, //
                "Excess", .1f * width, //
                "Location", .35f * width, //
                "Star system", .2f * width, //
                "Dist (ly)", .1f * width //
        };
        return header;
    }

    @Override
    public List<Object[]> getRows() {
        sortMarkets();
        return super.getRows();
    }

    @Override
    protected float getPrice(MarketAPI market) {
        float econUnit = commoditySpec.getEconUnit();
        return market.getSupplyPrice(commodityId, econUnit, true) / econUnit;
    }

    @Override
    protected Object[] getRow(MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        int available = getAvailable(commodity);
        if (available <= 0) {
            return null;
        }
        return getRow(market, commodity, available);
    }

    private Object[] getRow(MarketAPI market, CommodityOnMarketAPI commodity, int available) {
        Object[] row = new Object[18];
        int excess = commodity.getExcessQuantity();
        // Price
        row[0] = Alignment.MID;
        row[1] = Misc.getHighlightColor();
        row[2] = Misc.getDGSCredits(getPrice(market));
        // Available
        row[3] = Alignment.MID;
        row[4] = Misc.getHighlightColor();
        row[5] = Misc.getWithDGS(available);
        // Excess
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

    private int getAvailable(CommodityOnMarketAPI commodity) {
        int available = OpenMarketPlugin.getApproximateStockpileLimit(commodity);
        available += commodity.getPlayerTradeNetQuantity();
        return available;
    }

    private Color getExcessColor(int excess) {
        if (excess > 0) {
            return Misc.getPositiveHighlightColor();
        }
        return Misc.getGrayColor();
    }

    private String getExcessValue(int excess) {
        if (excess > 0) {
            return Misc.getWithDGS(excess);
        }
        return "---";
    }
}
