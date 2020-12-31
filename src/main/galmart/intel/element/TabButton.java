package galmart.intel.element;

import com.fs.starfarer.api.ui.CutStyle;
import com.fs.starfarer.api.util.Misc;

import galmart.intel.GalmartBoard;
import galmart.intel.GalmartBoard.CommodityTab;
import galmart.ui.Button;
import galmart.ui.Callable;
import galmart.ui.Size;

public class TabButton extends Button {

    public TabButton(final CommodityTab currentTab, CommodityTab activeTab, int shortcut) {
        super(new Size(200, 22), currentTab.title, true, Misc.getGrayColor());
        if (currentTab.equals(activeTab)) {
            setColor(Misc.getButtonTextColor());
        }
        setCallback(new Callable() {

            @Override
            public void callback() {
                GalmartBoard board = GalmartBoard.getInstance();
                board.setActiveTab(currentTab);
            }
        });
        setCutStyle(CutStyle.TOP);
        setShortcut(shortcut);
    }
}
