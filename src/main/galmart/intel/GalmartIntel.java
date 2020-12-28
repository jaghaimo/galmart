package galmart.intel;

import java.awt.Color;
import java.util.Set;

import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.IntelUIAPI;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import org.lwjgl.input.Keyboard;

import galmart.KeyboardHelper;
import galmart.extractor.TableCellHelper;

public class GalmartIntel extends BaseIntelPlugin {

    private String action;
    private CommoditySpecAPI commodity;
    private MarketAPI market;
    private IntelTracker tracker;
    private float price;

    public GalmartIntel(String action, CommoditySpecAPI commodity, MarketAPI market, IntelTracker tracker,
            float price) {
        this.action = action;
        this.commodity = commodity;
        this.market = market;
        this.tracker = tracker;
        this.price = price;
    }

    @Override
    public void buttonPressConfirmed(Object buttonId, IntelUIAPI ui) {
        tracker.remove(this);
        KeyboardHelper.send(Keyboard.KEY_E);
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        TableCellHelper helper = new TableCellHelper();
        Color bulletColor = getBulletColorForMode(mode);
        info.addPara(getTitle(), 0f, getTitleColor(mode));
        info.beginGridFlipped(300f, 1, Misc.getTextColor(), 80f, 10f);
        info.addToGrid(0, 0, market.getName(), "Market", bulletColor);
        info.addToGrid(0, 1, market.getFaction().getDisplayName(), "Faction", bulletColor);
        info.addToGrid(0, 2, helper.getSystemName(market), "System", bulletColor);
        info.addGrid(3f);
    }

    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height) {
        info.addButton("Delete", null, width, 20f, 5f);
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
    public String getSortString() {
        return getTitle();
    }

    @Override
    public boolean hasLargeDescription() {
        return false;
    }

    @Override
    public boolean hasSmallDescription() {
        return true;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public String getAction() {
        return action;
    }

    public String getCommodityId() {
        return commodity.getId();
    }

    public MarketAPI getMarket() {
        return market;
    }

    private String getTitle() {
        return String.format("%s %s for %.0f", action, commodity.getName(), price);
    }
}
