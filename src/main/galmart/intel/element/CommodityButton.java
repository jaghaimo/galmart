package galmart.intel.element;

import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.util.Misc;

import galmart.intel.GalmartBoard;
import galmart.ui.Callable;
import galmart.ui.Size;
import galmart.ui.ToggleButton;

public class CommodityButton extends ToggleButton {

    public CommodityButton(final CommoditySpecAPI commodity, boolean isOn) {
        super(new Size(200, 25), commodity.getName(), commodity.getName(), true, Misc.getHighlightColor(),
                Misc.getGrayColor(), isOn);

        setCallback(new Callable() {

            String commodityId = commodity.getId();

            @Override
            public void callback() {
                GalmartBoard board = GalmartBoard.getInstance();
                board.setActive(commodityId);
                board.issueIntel(commodityId);
            }
        });
    }
}
