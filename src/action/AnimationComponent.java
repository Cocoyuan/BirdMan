package action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by HighLengCo on 15/10/28.
 */
public interface AnimationComponent extends ActionListener{

    public interface Change {
        void perform(JComponent c);
    }

    JPanel mContainer = null;
    Timer mTimer = null;

    public void startAnimation(int delay);
    public void stopAnimation();
    public void reStartAnimation();
    public double distanceBetween(JComponent c);
    public boolean isIntersectedVerticallyWith(JComponent c);
    /**
     * @description should always use this method to add to its container.
     */
    public void addToContainer(JPanel container);

}
