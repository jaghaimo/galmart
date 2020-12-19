package galmart.ui;

import java.awt.Color;

import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.CutStyle;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class Button extends Renderable implements Callable {

    private final Size size;
    private final String title;
    private final Color color;
    private boolean isEnabled;
    private Callable callback;
    protected CutStyle cutStyle;

    public Button(Size size, String title, boolean isEnabled, Color color) {
        this.size = size;
        this.title = title;
        this.isEnabled = isEnabled;
        this.color = color;
        this.cutStyle = CutStyle.C2_MENU;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setCallback(Callable callback) {
        this.callback = callback;
    }

    public void setCutStyle(CutStyle cutStyle) {
        this.cutStyle = cutStyle;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void callback() {
        if (callback != null) {
            callback.callback();
        }
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void render(TooltipMakerAPI tooltip) {
        Color foregroundColor = getColor();
        Color backgroundColor = Misc.scaleColor(foregroundColor, 0.5f);
        ButtonAPI button = tooltip.addButton(title, this, foregroundColor, backgroundColor, Alignment.MID, cutStyle,
                size.getWidth() - 5f, size.getHeigth() - 5f, 5f);
        button.setEnabled(isEnabled);
    }
}
