package galmart;

import com.fs.starfarer.api.BaseModPlugin;

import galmart.intel.GalmartBoard;

public class GalmartMod extends BaseModPlugin {

    @Override
    public void onNewGame() {
        init();
    }

    @Override
    public void onGameLoad(boolean newGame) {
        init();
    }

    private void init() {
        GalmartBoard.getInstance();
    }
}
