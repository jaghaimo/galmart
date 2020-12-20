package galmart.intel.element;

import java.awt.event.KeyEvent;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.CutStyle;
import com.fs.starfarer.api.util.Misc;

import galmart.KeyboardHelper;
import galmart.intel.GalmartIntel;
import galmart.intel.IntelManager;
import galmart.ui.Button;
import galmart.ui.Callable;
import galmart.ui.Size;

public class IntelButton extends Button {

    public IntelButton(int i, final String action, final String commodityId, final MarketAPI market) {
        super(new Size(28f, 24f), String.valueOf(i), true, Misc.getTextColor());
        setCallback(new Callable() {

            @Override
            public void callback() {
                IntelManager manager = new IntelManager();
                GalmartIntel intel = new GalmartIntel(action, commodityId, market);
                manager.add(intel);
                KeyboardHelper.send(KeyEvent.VK_E);
            }
        });
        setCutStyle(CutStyle.TL_BR);
    }
}
