package galmart;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

import galmart.intel.GalmartBoard;

public class GalmartMod extends BaseModPlugin {

    public static int numberOfIntelToShow;

    @Override
    public void onNewGame() {
        init();
    }

    @Override
    public void onGameLoad(boolean newGame) {
        init();
    }

    private void init() {
        numberOfIntelToShow = Global.getSettings().getInt("numberOfIntelToShow");
        GalmartBoard.getInstance();
    }
}
