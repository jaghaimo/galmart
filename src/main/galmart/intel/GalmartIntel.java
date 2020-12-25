package galmart.intel;

import java.util.Set;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class GalmartIntel extends BaseIntelPlugin {

    private String action;
    private CommoditySpecAPI commodity;
    private MarketAPI market;

    public GalmartIntel(String action, String commodityId, MarketAPI market) {
        this.action = action;
        this.commodity = Global.getSector().getEconomy().getCommoditySpec(commodityId);
        this.market = market;
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        info.addPara(action + " " + commodity.getName(), 0f, getTitleColor(mode));
        info.addPara(market.getName(), 1f);
        // TODO: price, market, system
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
    }

    @Override
    public SectorEntityToken getMapLocation(SectorMapAPI map) {
        return market.getPrimaryEntity();
    }

    @Override
    public String getIcon() {
        return commodity.getIconName();
    }

    @Override
    public Set<String> getIntelTags(SectorMapAPI map) {
        Set<String> tags = super.getIntelTags(map);
        tags.add("Commodities");
        return tags;
    }

    @Override
    public IntelSortTier getSortTier() {
        return IntelSortTier.TIER_1;
    }

    @Override
    public boolean hasLargeDescription() {
        return false;
    }

    @Override
    public boolean hasSmallDescription() {
        return true;
    }
}
