package galacom;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.comm.IntelInfoPlugin;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;

public class GalacomBoard extends BaseIntelPlugin {

    public static GalacomBoard getInstance() {
        IntelInfoPlugin intel = Global.getSector().getIntelManager().getFirstIntel(GalacomBoard.class);
        if (intel == null) {
            GalacomBoard board = new GalacomBoard();
            Global.getSector().getIntelManager().addIntel(board);
        }
        return (GalacomBoard) intel;
    }
}
