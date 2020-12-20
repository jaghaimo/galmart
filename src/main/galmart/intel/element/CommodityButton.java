package galmart.intel.element;

import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.util.Misc;

import galmart.intel.GalmartBoard;
import galmart.ui.Callable;
import galmart.ui.Size;
import galmart.ui.ToggleButton;

public class CommodityButton extends ToggleButton {

    public CommodityButton(final CommoditySpecAPI commodity, boolean isOn) {
        super(new Size(200, 24), commodity.getName(), commodity.getName(), true, Misc.getHighlightColor(),
                Misc.getGrayColor(), isOn);

        setCallback(new Callable() {

            @Override
            public void callback() {
                String commodityId = commodity.getId();
                GalmartBoard board = GalmartBoard.getInstance();
                board.setActiveId(commodityId);
            }
        });
    }
}
