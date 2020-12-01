package galacom.board.element;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;

import galacom.extractor.BuyFromMarketExtractor;
import galacom.extractor.MarketExtractor;
import galacom.extractor.SellOnMarketExtractor;
import galacom.ui.Header;
import galacom.ui.Renderable;
import galacom.ui.Stack;
import galacom.ui.Table;

public class CommodityViewFactory {

    private EconomyAPI economy;

    public CommodityViewFactory() {
        economy = Global.getSector().getEconomy();
    }

    public Renderable get(String commodityId, float width, float height) {
        String commodityName = getCommodityName(commodityId);
        float labelHeight = 25f;
        float tableHeight = (height / 2) - labelHeight;
        return new Stack(new Header("Buy Price for 100 " + commodityName, width),
                getBuyTable(commodityId, width, tableHeight), new Header("Sell Price for 100 " + commodityName, width),
                getSellTable(commodityId, width, tableHeight));
    }

    private Table getBuyTable(String commodityId, float width, float height) {
        MarketExtractor marketExtractor = new BuyFromMarketExtractor(commodityId, economy);
        return new Table(getCommodityName(commodityId), width, height, marketExtractor);
    }

    private Table getSellTable(String commodityId, float width, float height) {
        MarketExtractor marketExtractor = new SellOnMarketExtractor(commodityId, economy);
        return new Table(getCommodityName(commodityId), width, height, marketExtractor);
    }

    private String getCommodityName(String commodityId) {
        CommoditySpecAPI commodity = economy.getCommoditySpec(commodityId);
        return commodity.getName();
    }
}
