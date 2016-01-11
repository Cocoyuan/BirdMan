package controller;

import action.*;
import main.Constants;

import javax.swing.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
//import action.DragImage;
//import com.apple.laf.AquaInternalFrameManager;

/**
 * Created by HighLengCo on 15/10/28.
 */
public class CanvasController implements AbstractController, ActionListener, Serializable{

    static final long serialVersionUID = 75443838583928L;

    private Component          mComponent;
    private AbstractController parentController;
    private JPanel mCanvasPanel = new JPanel();
    private DataFlavor dataFlover;

    private AnimatedImage                 ghost         = new AnimatedImage(Constants.IMAGE_PATH_GHOST);
    // private AnimatedImage ghost2        = new AnimatedImage(Constants.IMAGE_PATH_GHOST);
    //private AnimatedImage                 brick         = new AnimatedImage(Constants.IMAGE_PATH_BRICK);
    private ArrayList<AnimationComponent> componentList = new ArrayList<AnimationComponent>();
    private AnimatedLabel                 animatedLabel = new AnimatedLabel("0 s");
    private UserSettingsController usc ;
    private int bx, by;
    private int timeCheck =0;
    public AudioClip wind = Applet.newAudioClip(getClass().getResource("wind.wav"));
    public AudioClip win = Applet.newAudioClip(getClass().getResource("win.wav"));
    public AudioClip lose = Applet.newAudioClip(getClass().getResource("lose.wav"));

    public CanvasController() {
        mComponent = mCanvasPanel;
        initUI();
    }

    private void initUI() {

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        GridBagLayout      layout             = new GridBagLayout();

        mCanvasPanel.setLayout(null);
        mCanvasPanel.setBorder(BorderFactory.createTitledBorder("Game Panel"));
        mCanvasPanel.setPreferredSize(new Dimension(560, 930));

        animatedLabel.setAnimation(new AnimationComponent.Change() {
            @Override
            public void perform(JComponent c) {
                JLabel l = (JLabel) c;
                
                   String[] time = l.getText().split(" ");
                   int time0 = Integer.parseInt(time[0]);
                if (l != null&&timeCheck == 0) {
                    ++time0;
                    l.setText(Integer.toString(time0) + " s");
                    
                }
                else if(timeCheck ==1){
                	l.setText(Integer.toString(time0) + " s");
                }
            }
        });
        animatedLabel.setBounds(400, 20, 40, 25);
        animatedLabel.addToContainer(mCanvasPanel);

        new AnimationDropHandler(mCanvasPanel, AnimatedImage.dataFlavor, this);
        initGhost();
    }
    //lose game
    private void testLogic(){
        for (AnimationComponent e:componentList) {
            //System.out.print("isTouched");
            //System.out.println(e.isIntersectedVerticallyWith(ghost));
            if(e.isIntersectedVerticallyWith(ghost)==true){
            	ghost.stopAnimation();
            	if(usc.soundOff.isSelected()==false){
            		lose.play();
            	}
            	//AudioClip sound = Applet.newAudioClip(getClass().getResource("lose.wav"));
        		
        		timeCheck=1;
            	//System.out.println("you dead!");
            	JOptionPane.showMessageDialog(null, "Sorry you dead!", "Lose", JOptionPane.ERROR_MESSAGE);
            	//JOptionPane.showConfirmDialog(null, "choose one", "choose one", JOptionPane.YES_NO_OPTION);
            	
            }
        }
    }

    private void initGhost(){

        ghost.setLocation(new Point(240, 20));
        ghost.setSize(new Dimension(40, 40));
        ghost.addToContainer(mCanvasPanel);


        KeyBoardPress.registerAsObserver(Constants.KEY_EVENT_LEFT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = ghost.getX(), y = ghost.getY();
                int r = Integer.valueOf(usc.windSpeed.getText()).intValue();
                ghost.setLocation(x - r, y);
                //URL url = getClass().getResource("");
        		//System.out.println(url);
                //AudioClip wind = Applet.newAudioClip(getClass().getResource("wind.wav"));
        		
                if(usc.soundOff.isSelected()==false){
                	wind.play();
            	}
            }
        });

        //press to right
        KeyBoardPress.registerAsObserver(Constants.KEY_EVENT_RIGHT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = ghost.getX(), y = ghost.getY();
                int r = Integer.valueOf(usc.windSpeed.getText()).intValue();
                ghost.setLocation(x + r, y);
                //AudioClip sound = Applet.newAudioClip(getClass().getResource("wind.wav"));
                if(usc.soundOff.isSelected()==false){
                	wind.play();
            	}
                
                //System.out.println(by);
                
            }
        });
        
        KeyBoardPress.registerAsObserver(Constants.KEY_EVENT_DOWN, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = ghost.getX(), y = ghost.getY();
                ghost.setLocation(x, y+5);
                //System.out.println(by);
                
            }
        });
        
        //check win 
        ghost.setAnimation(new AnimationComponent.Change() {
            @Override
            public void perform(JComponent c) {
                int x = c.getX(), y = c.getY();
                int r = Integer.valueOf(usc.fallSpeed.getText()).intValue();
                if(y<560){
                	c.setLocation(x, y + r);
                }
                else{
                	//AudioClip sound = Applet.newAudioClip(getClass().getResource("win.wav"));
                	if(usc.soundOff.isSelected()==false){
                    	win.play();
                	}
                	
                	System.out.println("you win!");
                	JOptionPane.showMessageDialog(null, "Congratulations! You win!", "Win", JOptionPane.INFORMATION_MESSAGE);
                	//JOptionPane.showConfirmDialog(null, "choose one", "choose one", JOptionPane.YES_NO_OPTION);
                	ghost.stopAnimation();
                }
                testLogic();
                //comprisonLocation(ghost, brick);
            }
        });

//        ghost2.setAnimation(new AnimationComponent.Change() {
//            @Override
//            public void perform(JComponent c) {
//                int x = c.getX(), y = c.getY();
//                c.setLocation(x, y + 1);
//            }
//        });
    }

    public void setUsc(UserSettingsController usc) {
        this.usc = usc;
    }

    public void addCompoenetToList(AnimationComponent a){
        componentList.add(a);
    }

    @Override
    public void addSubController(AbstractController controller) {
        mCanvasPanel.add(controller.getComponent());
    }

    @Override
    public Component getComponent() {
        return mComponent;
    }

    @Override
    public AbstractController getParentController() {
        return parentController;
    }

    public void brickLocationX(int brickX){
    	 bx= brickX;
    	
    }
    
    public void brickLocationY(int brickY){
    	by= brickY;
    	
    }
//    public void comprisonLocation (AnimatedImage gst, AnimatedImage brk){
//    	gst = ghost; 
//    	//brk = brick;
//    	
//    	int x = ghost.getX(), y = ghost.getY();
//    	//int a = brickLocationX(bx), b = adh.getBrickY();
//    	int w = brick.getWidth(), h = brick.getHeight();
//    	
//    	if(x<(a+w)&&x>a&&y>b&&y<(b+h)){
//    		ghost.stopAnimation();
//    		System.out.println("you lose");
//    		
//    	}
//    	
//    }
    
//    public boolean equals(Object obj) {
//        return (this == obj);
//    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if (command.equals(Constants.BUTTON_EVENT_START)) {
            ghost.startAnimation(20);
            animatedLabel.startAnimation(1000);
            //ghost2.startAnimation(20);
        } else if (command.equals(Constants.BUTTON_EVENT_STOP)) {
            ghost.stopAnimation();
            animatedLabel.stopAnimation();
            animatedLabel.resetLabel();

        } else if(command.equals(Constants.BUTTON_EVENT_RESTART)){
        	ghost.setLocation(new Point(240, 20));
            ghost.setSize(new Dimension(40, 40));
            ghost.addToContainer(mCanvasPanel);
            ghost.startAnimation(20);
        	animatedLabel.startAnimation(1000);
        }
    }
}