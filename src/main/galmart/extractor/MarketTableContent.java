package galmart.extractor;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.util.Misc;

import galmart.ui.TableContent;

public abstract class MarketTableContent implements TableContent {

    protected String commodityId;
    protected TableCellHelper helper;
    private List<MarketAPI> markets;
    private Price price;

    protected MarketTableContent(String commodityId, List<MarketAPI> markets, Price price) {
        this.commodityId = commodityId;
        this.helper = new TableCellHelper();
        this.markets = markets;
        this.price = price;
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
            content.add(row);
        }
        return content;
    }

    protected Object[] getHeader(float width, String availableOrDemand, String excessOrDeficit) {
        Object header[] = { "Price", .10f * width, availableOrDemand, .1f * width, excessOrDeficit, .1f * width,
                "Location", .35f * width, "Star system", .2f * width, "Dist (ly)", .1f * width };
        return header;
    }

    protected Object[] getRow(MarketAPI market, CommodityOnMarketAPI commodity, float price, int available,
            int excess) {
        Object[] row = new Object[18];
        // Price
        row[0] = Alignment.MID;
        row[1] = Misc.getHighlightColor();
        row[2] = Misc.getDGSCredits(price);
        // Available or Demand
        row[3] = Alignment.MID;
        row[4] = Misc.getHighlightColor();
        row[5] = Misc.getWithDGS(available);
        // Excess or Deficit
        row[6] = Alignment.MID;
        row[7] = helper.getExcessColor(excess);
        row[8] = helper.getExcessValue(excess);
        // Location
        row[9] = Alignment.LMID;
        row[10] = market.getTextColorForFactionOrPlanet();
        row[11] = helper.getLocation(market);
        // Star system
        row[12] = Alignment.MID;
        row[13] = helper.getClaimingFactionColor(market);
        row[14] = helper.getSystemName(market);
        // Distance
        row[15] = Alignment.MID;
        row[16] = Misc.getHighlightColor();
        row[17] = helper.getDistance(market);

        return row;
    }

    protected float getPrice(MarketAPI market) {
        return price.getPrice(market);
    }

    protected abstract Object[] getRow(MarketAPI market);
}
