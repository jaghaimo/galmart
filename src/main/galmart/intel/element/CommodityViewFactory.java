package galmart.intel.element;

import java.util.Arrays;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;

import org.lwjgl.input.Keyboard;

import galmart.extractor.BuyFromMarketExtractor;
import galmart.extractor.BuyFromMarketFactory;
import galmart.extractor.SellOnMarketExtractor;
import galmart.extractor.SellOnMarketFactory;
import galmart.intel.GalmartBoard.CommodityTab;
import galmart.ui.Renderable;
import galmart.ui.Row;
import galmart.ui.Stack;
import galmart.ui.Table;
import galmart.ui.TableContent;

public class CommodityViewFactory {

    private EconomyAPI economy;

    public CommodityViewFactory() {
        economy = Global.getSector().getEconomy();
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
        BuyFromMarketFactory factory = new BuyFromMarketFactory(commodityId, economy);
        return new BuyFromMarketExtractor(commodityId, factory.getMarkets(), economy);
    }

    private TableContent getSellTableContent(String commodityId) {
        SellOnMarketFactory factory = new SellOnMarketFactory(commodityId, economy);
        return new SellOnMarketExtractor(commodityId, factory.getMarkets(), economy);
    }
}
