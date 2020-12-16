package galmart.intel.element;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;

import galmart.extractor.BuyFromMarketExtractor;
import galmart.extractor.SellOnMarketExtractor;
import galmart.ui.Padding;
import galmart.ui.Paragraph;
import galmart.ui.Renderable;
import galmart.ui.Stack;
import galmart.ui.Table;
import galmart.ui.TableContent;

public class CommodityViewFactory {

    private EconomyAPI economy;

    public CommodityViewFactory() {
        economy = Global.getSector().getEconomy();
    }

    public Renderable get(String commodityId, float width, float height) {
        String commodityName = getCommodityName(commodityId);
        float paddingHeight = 5f;
        float labelHeight = 25f;
        float tableHeight = (height / 2) - labelHeight - paddingHeight;
        TableContent buyTableContent = new BuyFromMarketExtractor(commodityId, economy);
        TableContent sellTableContent = new SellOnMarketExtractor(commodityId, economy);
        return new Stack(new Paragraph("Best places to sell " + commodityName + ":", width), new Padding(5f),
                new Table(commodityId, width, tableHeight, sellTableContent), new Padding(5),
                new Paragraph("Best places to buy " + commodityName + ":", width), new Padding(5),
                new Table(commodityId, width, tableHeight, buyTableContent));
    }

    private String getCommodityName(String commodityId) {
        CommoditySpecAPI commodity = economy.getCommoditySpec(commodityId);
        return commodity.getName();
    }
}
