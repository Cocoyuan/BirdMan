package action;

import main.Constants;
import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * Created by HighLengCo on 15/11/6.
 */
public class AnimatedLabel extends JLabel implements AnimationComponent {

    private JPanel mContainer;
    private Timer mTimer = new Timer(Constants.DEFAULT_DELAY, this);
    private Change mCallback;

    public AnimatedLabel(String s){
        super(s);
    }

    @Override
    public void startAnimation(int delay) {
        if (!mTimer.isRunning()) {
            mTimer.setDelay(delay);
            mTimer.start();
        }
    }

    @Override
    public void stopAnimation() {
        mTimer.stop();
    }
    ;
    public void resetLabel(){
        setText("0 s");
    }

    @Override
    public double distanceBetween(JComponent c) {
        return 0;
    }

    @Override
    public boolean isIntersectedVerticallyWith(JComponent c) {
        return false;
    }

    @Override
    public void reStartAnimation() {
        if (mTimer.isRunning()) {
            mTimer.restart();
        }
    }

    @Override
    public void addToContainer(JPanel container) {
        container.add(this);
        mContainer = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (this) {
            if (mCallback != null) {
                mCallback.perform(this);
            }
        }
    }

    public JPanel getContainer() {
        return mContainer;
    }

    public void setContainer(JPanel container) {
        this.mContainer = container;
    }

    public void setAnimation(Change callback) {
        mCallback = callback;
    }
}