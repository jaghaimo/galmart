package galacom.board.element;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;

import galacom.extractor.BuyFromMarketExtractor;
import galacom.extractor.SellOnMarketExtractor;
import galacom.ui.Header;
import galacom.ui.Renderable;
import galacom.ui.Stack;
import galacom.ui.Table;
import galacom.ui.TableContent;

public class CommodityViewFactory {

    private EconomyAPI economy;

    public CommodityViewFactory() {
        economy = Global.getSector().getEconomy();
    }

    public Renderable get(String commodityId, float width, float height) {
        String commodityName = getCommodityName(commodityId);
        float labelHeight = 25f;
        float tableHeight = (height / 2) - labelHeight;
        TableContent buyTableContent = new BuyFromMarketExtractor(commodityId, economy);
        TableContent sellTableContent = new SellOnMarketExtractor(commodityId, economy);
        return new Stack(new Header("Buy Price for 100 " + commodityName, width),
                new Table(commodityId, width, tableHeight, buyTableContent),
                new Header("Sell Price for 100 " + commodityName, width),
                new Table(commodityId, width, tableHeight, sellTableContent));
    }

    private String getCommodityName(String commodityId) {
        CommoditySpecAPI commodity = economy.getCommoditySpec(commodityId);
        return commodity.getName();
    }
}
