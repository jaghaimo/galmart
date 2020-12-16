package galmart.intel;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.comm.IntelInfoPlugin;
import com.fs.starfarer.api.campaign.comm.IntelManagerAPI;

public class IntelManager {

    IntelManagerAPI intelManager;

    public IntelManager() {
        this.intelManager = Global.getSector().getIntelManager();
    }

    public void add(IntelInfoPlugin intel) {
        intelManager.addIntel(intel);
    }

    public IntelInfoPlugin get(Class<?> className) {
        return intelManager.getFirstIntel(className);
    }

    public void remove(IntelInfoPlugin intel) {
        intelManager.removeIntel(intel);
    }

    public void remove(Class<?> className) {
        IntelInfoPlugin intel = intelManager.getFirstIntel(className);
        while (intel != null) {
            remove(intel);
            intel = intelManager.getFirstIntel(className);
        }
    }
}
