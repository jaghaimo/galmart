package galacom.board.element;

import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.util.Misc;

import galacom.board.GalacomBoard;
import galacom.ui.Callable;
import galacom.ui.Size;
import galacom.ui.ToggleButton;

public class CommodityButton extends ToggleButton {

    public CommodityButton(final CommoditySpecAPI commodity, boolean isOn) {
        super(new Size(200, 25), commodity.getName(), commodity.getName(), true, Misc.getHighlightColor(),
                Misc.getGrayColor(), isOn);

        setCallback(new Callable() {

            String commodityId = commodity.getId();

            @Override
            public void callback() {
                GalacomBoard board = GalacomBoard.getInstance();
                board.setActive(commodityId);
            }
        });
    }
}
