package galmart.filter.market;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import galmart.extractor.TableCellHelper;

public class CommodityDemandFilter implements MarketFilter {

    private String commodityId;
    private TableCellHelper helper;

    public CommodityDemandFilter(String commodityId, EconomyAPI economy) {
        this.commodityId = commodityId;
        this.helper = new TableCellHelper();
    }

    @Override
    public boolean accept(MarketAPI market) {
        CommodityOnMarketAPI commodity = market.getCommodityData(commodityId);
        int demand = helper.getDemand(market, commodity);
        if (demand <= 0) {
            return false;
        }
        return true;
    }
}
