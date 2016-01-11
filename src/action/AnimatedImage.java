package action;


import main.Constants;
import view.ImageComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by HighLengCo on 15/10/28.
 */

public class AnimatedImage extends ImageComponent implements AnimationComponent {


    public static  DataFlavor                  dataFlavor = new DataFlavor(AnimatedImage.class, Constants.FLAVOR_DES_ANIMATION);
    private static ScheduledThreadPoolExecutor pool       = new ScheduledThreadPoolExecutor(6);
    private        Timer                       mTimer     = new Timer(Constants.DEFAULT_DELAY, this);
    private JPanel mContainer;
    private Change mCallback;
    private Boolean    enableDrag = false;

    private DragSource source     = new DragSource();
    private DragGestureListener listener;

    public AnimatedImage(String path) {
        super(path);
    }

    public AnimatedImage(ImageIcon imageIcon) {
        super(imageIcon);
    }

    //implement interface
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        synchronized (this) {
            if (mCallback != null) {
                mCallback.perform(this);
            }
        }
    }

    @Override
    public void startAnimation(int delay) {
        if (!mTimer.isRunning()) {
            mTimer.setDelay(delay);
            mTimer.start();
        }
    }

    public void startAnimation() {
        startAnimation(Constants.DEFAULT_DELAY);
    }

    @Override
    public void stopAnimation() {
        mTimer.stop();
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

    /**
     * used to calcul the distance between two components
     *
     * @param c another component
     * @return distince from one's center to another's
     */
    public double distanceBetween(JComponent c) {
        int    x          = getX(), cx = c.getX();
        int    y          = getY(), cy = c.getY();
        int    width      = getWidth(), cwidth = c.getWidth();
        int    height     = getHeight(), cheight = c.getHeight();
        double centerX    = x + width * 0.5, centerY = y + height * 0.5;
        double centerXOfC = cx + cwidth * 0.5, centerYOfC = cy + cheight * 0.5;
        return Math.sqrt(Math.pow(centerX - centerXOfC, 2) + Math.pow(centerY - centerYOfC, 2));
    }

    /**
     * determine if one component is intersected with another vertically, used for receiving characters by boat.
     *
     * @param c another component
     * @return true if two component is intersected vertically , vice versa
     */
    public boolean isIntersectedVerticallyWith(JComponent c) {
        int    x          = getX(), cx = c.getX();
        int    y          = getY(), cy = c.getY();
        int    width      = getWidth(), cwidth = c.getWidth();
        int    height     = getHeight(), cheight = c.getHeight();
        double centerX    = x + width * 0.5, centerY = y + height * 0.5;
        double centerXOfC = cx + cwidth * 0.5, centerYOfC = cy + cheight * 0.5;
        System.out.print("distancX:"); System.out.print(Math.abs(centerX - centerXOfC));
        System.out.print("widthX:"); System.out.print(width*0.5);
        System.out.print("widthC:"); System.out.println(cwidth*0.5);
        System.out.print("distancY:"); System.out.print(Math.abs(centerY - centerYOfC));
        System.out.print("heightX:"); System.out.print(height*0.5);
        System.out.print("heightC:"); System.out.println(cheight*0.5);
        return ( (Math.abs(centerX - centerXOfC) - cwidth * 0.5) <= width*0.5 ) && ((Math.abs(centerY - centerYOfC) - cheight*0.5) <= height*0.5 );
    }

    // getter setter
    public JPanel getContainer() {
        return mContainer;
    }

    public void setContainer(JPanel container) {
        this.mContainer = container;
    }

    public void setAnimation(Change callback) {
        mCallback = callback;
    }

    public void setDragEnabled(Boolean enableDrag) {
        this.enableDrag = enableDrag;
        if (enableDrag) {
            listener = new PanelDragMoveHandler(AnimatedImage.dataFlavor);
            source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, listener);
        } else {
            if (source != null) {
                source.removeDragSourceListener((DragSourceListener) listener);
            }
        }
    }
}