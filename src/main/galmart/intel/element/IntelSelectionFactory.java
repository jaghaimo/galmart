package galmart.intel.element;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.MarketAPI;

import galmart.intel.IntelTracker;
import galmart.intel.GalmartBoard.CommodityTab;
import galmart.ui.Renderable;
import galmart.ui.Row;

public class IntelSelectionFactory {

    private List<MarketAPI> markets;
    private IntelTracker tracker;

    public IntelSelectionFactory() {
        markets = Collections.<MarketAPI>emptyList();
        tracker = new IntelTracker();
    }

    public Renderable get(String commodityId, CommodityTab actionTab, float width) {
        int buttonsOnScreen = (int) Math.floor(width / 28f);
        int maxButtons = markets.size();
        int numberOfButtons = Math.min(buttonsOnScreen, maxButtons);
        List<Renderable> buttons = new LinkedList<>();
        for (int i = 0; i < numberOfButtons; i++) {
            MarketAPI market = markets.get(i);
            buttons.add((Renderable) new IntelButton(i + 1, actionTab.title, commodityId, market, tracker));
        }
        return new Row(buttons);
    }

    public void setMarkets(List<MarketAPI> markets) {
        this.markets = markets;
    }
}
