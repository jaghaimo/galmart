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
                "Dist (ly)", .1f * width //
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
        float econUnit = commoditySpec.getEconUnit();
        return market.getDemandPrice(commodityId, econUnit, true) / econUnit;
    }

    @Override
    protected Object[] getRow(MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        int demand = getDemand(market, commodity);
        if (demand <= 0) {
            return null;
        }
        return getRow(market, commodity, demand);
    }

    private Object[] getRow(MarketAPI market, CommodityOnMarketAPI commodity, int demand) {
        Object[] row = new Object[18];
        int deficit = commodity.getDeficitQuantity();
        // Price
        row[0] = Alignment.MID;
        row[1] = Misc.getHighlightColor();
        row[2] = Misc.getDGSCredits(getPrice(market));
        // Demand
        row[3] = Alignment.MID;
        row[4] = Misc.getHighlightColor();
        row[5] = Misc.getWithDGS(demand);
        // Deficit
        row[6] = Alignment.MID;
        row[7] = getDeficitColor(deficit);
        row[8] = getDeficitValue(deficit);
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

    private int getDemand(MarketAPI market, CommodityOnMarketAPI commodity) {
        int demandIcons = commodity.getMaxDemand();
        if (!commodity.getCommodity().isPrimary()) {
            CommodityOnMarketAPI primary = market.getCommodityData(commodity.getCommodity().getDemandClass());
            demandIcons = primary.getMaxDemand();
        }
        int demand = (int) (commodity.getCommodity().getEconUnit() * demandIcons);
        demand -= commodity.getPlayerTradeNetQuantity();
        return demand;
    }

    private Color getDeficitColor(int deficit) {
        if (deficit > 0) {
            return Misc.getNegativeHighlightColor();
        }
        return Misc.getGrayColor();
    }

    private String getDeficitValue(int deficit) {
        if (deficit > 0) {
            return Misc.getWithDGS(deficit);
        }
        return "---";
    }
}
