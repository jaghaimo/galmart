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

    private String activeId;
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
        activeId = Commodities.SUPPLIES;
        buttonViewFactory = new ButtonViewFactory();
        commodityViewFactory = new CommodityViewFactory();
        intelManager = new IntelManager();
        intelFactory = new IntelFactory();
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
        GridRenderer renderer = new GridRenderer(new Size(width, height));
        renderer.setTopLeft(commodityViewFactory.get(activeId, width - 210, height));
        renderer.setTopRight(buttonViewFactory.get(activeId));
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

    public void setActive(String commodityId) {
        this.activeId = commodityId;
    }

    protected Object readResolve() {
        if (activeId == null) {
            activeId = Commodities.SUPPLIES;
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
