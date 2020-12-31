package galmart.intel.element;

import java.util.Arrays;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

import org.lwjgl.input.Keyboard;

import galmart.extractor.BuyMarketFactory;
import galmart.extractor.BuyTableContent;
import galmart.extractor.MarketFactory;
import galmart.extractor.SellMarketFactory;
import galmart.extractor.SellTableContent;
import galmart.intel.GalmartBoard.CommodityTab;
import galmart.ui.Renderable;
import galmart.ui.Row;
import galmart.ui.Stack;
import galmart.ui.Table;
import galmart.ui.TableContent;

public class CommodityViewFactory {

    private EconomyAPI economy;
    private IntelSelectionFactory intelSelectionFactory;

    public CommodityViewFactory(IntelSelectionFactory intelSelectionFactory) {
        this.economy = Global.getSector().getEconomy();
        this.intelSelectionFactory = intelSelectionFactory;
    }

    public Renderable get(String commodityId, CommodityTab activeTab, float width, float height) {
        float tabsHeight = 15f;
        float tableHeight = height - tabsHeight;
        TableContent tableContent = getTableContent(commodityId, activeTab);
        Renderable tabs = getTabs(activeTab);
        Renderable table = new Table(commodityId, width, tableHeight, tableContent);
        return new Stack(tabs, table);
    }

    private Renderable getTabs(CommodityTab activeTab) {
        Renderable buyButton = new TabButton(CommodityTab.BUY, activeTab, Keyboard.KEY_B);
        Renderable sellButton = new TabButton(CommodityTab.SELL, activeTab, Keyboard.KEY_S);
        return new Row(Arrays.asList(buyButton, sellButton));
    }

    private TableContent getTableContent(String commodityId, CommodityTab activeTab) {
        TableContent tableContent = null;
        if (activeTab == CommodityTab.BUY) {
            tableContent = getBuyTableContent(commodityId);
        } else if (activeTab == CommodityTab.SELL) {
            tableContent = getSellTableContent(commodityId);
        }
        return tableContent;
    }

    private TableContent getBuyTableContent(String commodityId) {
        return new BuyTableContent(commodityId, getMarkets(new BuyMarketFactory(commodityId, economy)), economy);
    }

    private TableContent getSellTableContent(String commodityId) {
        return new SellTableContent(commodityId, getMarkets(new SellMarketFactory(commodityId, economy)), economy);
    }

    private List<MarketAPI> getMarkets(MarketFactory factory) {
        List<MarketAPI> markets = factory.getMarkets();
        intelSelectionFactory.setMarkets(markets);
        return markets;
    }
}
