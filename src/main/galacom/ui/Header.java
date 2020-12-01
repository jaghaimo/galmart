package galacom.ui;

import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class Header extends Renderable {

    private String title;
    private float width;

    public Header(String title, float width) {
        this.title = title;
        this.width = width;
    }

    @Override
    public Size getSize() {
        return new Size(width, 20);
    }

    @Override
    public void render(TooltipMakerAPI tooltip) {
        tooltip.addSectionHeading(title, Alignment.MID, 0f);
    }
}
