package galmart.intel;

import java.util.List;
import java.util.Set;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.comm.IntelInfoPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.IntelUIAPI;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import galmart.intel.element.ButtonViewFactory;
import galmart.intel.element.CommodityViewFactory;
import galmart.ui.Callable;
import galmart.ui.GridRenderer;
import galmart.ui.Size;

public class GalmartBoard extends BaseIntelPlugin {

    public enum CommodityTab {
        BUY, SELL;
    }

    private String activeId;
    private CommodityTab activeTab;
    private ButtonViewFactory buttonViewFactory;
    private CommodityViewFactory commodityViewFactory;
    private IntelManager intelManager;
    private IntelFactory intelFactory;

    public static GalmartBoard getInstance() {
        IntelManager intelManager = new IntelManager();
        IntelInfoPlugin intel = intelManager.get(GalmartBoard.class);
        if (intel == null) {
            intel = new GalmartBoard();
            intelManager.add(intel);
        }
        return (GalmartBoard) intel;
    }

    public GalmartBoard() {
        readResolve();
    }

    @Override
    public void buttonPressConfirmed(Object buttonId, IntelUIAPI ui) {
        Callable callable = (Callable) buttonId;
        callable.callback();
        ui.updateUIForItem(this);
    }

    @Override
    public void createIntelInfo(TooltipMakerAPI info, ListInfoMode mode) {
        info.addPara("Commodity Market", getTitleColor(mode), 0);
    }

    @Override
    public void createLargeDescription(CustomPanelAPI panel, float width, float height) {
        float commodityViewWidth = width - 210;
        GridRenderer renderer = new GridRenderer(new Size(width, height));
        renderer.setTopLeft(commodityViewFactory.get(activeId, activeTab, commodityViewWidth, height));
        renderer.setTopRight(buttonViewFactory.get(activeId));
        // TODO: add buttons to issue intel
        renderer.render(panel);
    }

    @Override
    public String getIcon() {
        return Global.getSettings().getSpriteName("galmart", "board");
    }

    @Override
    public Set<String> getIntelTags(SectorMapAPI map) {
        Set<String> tags = super.getIntelTags(map);
        tags.add("Commodities");
        return tags;
    }

    @Override
    public boolean hasLargeDescription() {
        return true;
    }

    @Override
    public boolean hasSmallDescription() {
        return false;
    }

    public void issueIntel(String commodityId) {
        intelManager.remove(GalmartIntel.class);
        List<GalmartIntel> intels = intelFactory.get(commodityId);
        for (GalmartIntel intel : intels) {
            intelManager.add(intel);
        }
    }

    public void setActiveId(String activeId) {
        this.activeId = activeId;
    }

    public void setActiveTab(CommodityTab activeTab) {
        this.activeTab = activeTab;
    }

    protected Object readResolve() {
        if (activeId == null) {
            activeId = Commodities.SUPPLIES;
        }
        if (activeTab == null) {
            activeTab = CommodityTab.BUY;
        }
        if (buttonViewFactory == null) {
            buttonViewFactory = new ButtonViewFactory();
        }
        if (commodityViewFactory == null) {
            commodityViewFactory = new CommodityViewFactory();
        }
        if (intelManager == null) {
            intelManager = new IntelManager();
        }
        if (intelFactory == null) {
            intelFactory = new IntelFactory();
        }
        return this;
    }
}
