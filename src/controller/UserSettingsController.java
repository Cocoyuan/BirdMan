package controller;

import action.AnimatedImage;
import action.ListDragCopyHandler;
import main.Constants;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by HighLengCo on 15/10/28.
 * user setting 
 */

public class UserSettingsController implements AbstractController, ActionListener {

    private Object[] blocks = new Object[]{
            new ImageIcon (getClass().getClassLoader().getResource("images/brick01.png")),
            new ImageIcon (getClass().getClassLoader().getResource("images/brick02.png")),
            new ImageIcon (getClass().getClassLoader().getResource("images/brick03.png"))
    };
    private CanvasController cc;
    private Component          mComponent;
    private AbstractController parentController;
    
    //main panel
    private JPanel  mUserSettingsPanel = new JPanel();
    
    //wall panel   
    private JPanel wallPanel = new JPanel();
    private JLabel bricksText = new JLabel("Drag to add the brick:    ");
    private JList dragList = new JList(blocks);
    
    //game parameter panel
    private JPanel parameterPanel = new JPanel();
    
    private JPanel windSpeedPanel = new JPanel();
    private JPanel fallingSpeedPanel = new JPanel();
    private JPanel fallingFreqPanel = new JPanel();
    
    private JLabel windspdText = new JLabel("Set wind speed:");
    private JLabel fallspdText = new JLabel("Set falling speed:");
    //private JLabel fallfreqText = new JLabel("Set falling frequency:");
    
    public JTextField windSpeed = new JTextField(5);
    public JTextField fallSpeed = new JTextField(5);
    //private JTextField fallFreq = new JTextField(5);
  
    //sound effect panel
    private JPanel soundEffectPanel = new JPanel();
    
    private JPanel musicPanel = new JPanel();
    private JLabel musicL = new JLabel("Music");
    private JRadioButton musicOn = new JRadioButton("On");
    private JRadioButton musicOff = new JRadioButton("Off");
    private ButtonGroup musicR = new ButtonGroup();
    
    private JPanel soundPanel = new JPanel();
    private JLabel soundL = new JLabel("Sound");
    private JRadioButton soundOn = new JRadioButton("On");
    public JRadioButton soundOff = new JRadioButton("Off");
    private ButtonGroup soundR = new ButtonGroup();
     
    //control panel
    private JPanel controlPanel = new JPanel();
    private JButton mReStartButton = new JButton("Restart");
    private JButton mStartButton = new JButton("Start");
    private JButton mStopButton = new JButton("Stop");

    
    private ActionListener listener;

    public UserSettingsController() {
        mComponent = mUserSettingsPanel;   	
    	mUserSettingsPanel.setLayout(new BoxLayout(mUserSettingsPanel, BoxLayout.Y_AXIS));
    	//wallPanel.setLayout(new BoxLayout(wallPanel, BoxLayout.PAGE_AXIS));
        initUI();
    }

    public void setCc(CanvasController cc) {
        this.cc = cc;
    }
    private void initUI() {
    	
        KeyBoardPress.addKeyboardAction(Constants.KEY_EVENT_LEFT, mUserSettingsPanel, KeyEvent.VK_LEFT);
        KeyBoardPress.addKeyboardAction(Constants.KEY_EVENT_RIGHT, mUserSettingsPanel, KeyEvent.VK_RIGHT);
        KeyBoardPress.addKeyboardAction(Constants.KEY_EVENT_DOWN, mUserSettingsPanel, KeyEvent.VK_DOWN);
        mReStartButton.addActionListener(this);
        mReStartButton.setActionCommand(Constants.BUTTON_EVENT_RESTART);
        mStartButton.addActionListener(this);
        mStartButton.setActionCommand(Constants.BUTTON_EVENT_START);
        mStopButton.addActionListener(this);
        mStopButton.setActionCommand(Constants.BUTTON_EVENT_STOP);

        //Init dragList
        dragList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dragList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        dragList.setVisibleRowCount(3);
        dragList.setTransferHandler(new ListDragCopyHandler(dragList, AnimatedImage.dataFlavor));
        dragList.setDropMode(DropMode.USE_SELECTION);
        dragList.setDragEnabled(true);
       
        //design wall
        mUserSettingsPanel.add(wallPanel);
        wallPanel.add(bricksText, BorderLayout.LINE_START);
        wallPanel.add(dragList);
        wallPanel.setBorder(BorderFactory.createTitledBorder("Wall Panel"));
       // wallPanel.setPreferredSize(new Dimension(560, 80));
        
        //game parameters
        mUserSettingsPanel.add(parameterPanel);
        parameterPanel.setVisible(true);
        parameterPanel.setBorder(BorderFactory.createTitledBorder("Params Panel"));
        parameterPanel.setLayout(new BoxLayout(parameterPanel, BoxLayout.Y_AXIS));
        parameterPanel.setPreferredSize(new Dimension(560, 10));
        
        parameterPanel.add(windSpeedPanel);     
        windSpeedPanel.setLayout(new FlowLayout());
        
        parameterPanel.add(fallingSpeedPanel);
        fallingSpeedPanel.setLayout(new FlowLayout());
        
        parameterPanel.add(fallingFreqPanel);
        fallingFreqPanel.setLayout(new FlowLayout());
        
        windSpeedPanel.add(windspdText);
        windSpeedPanel.add(windSpeed);
        windSpeed.setText("10");
        windSpeed.addActionListener(new ActionListener() {
//			
//			@Override
			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				//String ws = windSpeed.getText();
			}
		});
        
        fallingSpeedPanel.add(fallspdText);
        fallSpeed.setText("1");
        fallingSpeedPanel.add(fallSpeed);
//        fallfreqText.addInputMethodListener(new ActionListener(){
//        	public void actionPerformed(ActionEvent e) {
//                System.out.println("Text=" + text.getText());
//              }
//        });
        //fallingFreqPanel.add(fallfreqText);
        //fallingFreqPanel.add(fallFreq);
        
        
        //sound effect control
        mUserSettingsPanel.add(soundEffectPanel);
        soundEffectPanel.setBorder(BorderFactory.createTitledBorder("Sound Effect Panel"));
      //  soundEffectPanel.setPreferredSize(new Dimension(560, 1));
        
        //music
        ActionListener mListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JRadioButton radio = (JRadioButton) e.getSource();   
				AudioClip music = Applet.newAudioClip(getClass().getResource("background.wav"));
				//AudioClip sound = Applet.newAudioClip(getClass().getResource("wind.wav"));
				//AudioClip win = Applet.newAudioClip(getClass().getResource("win.wav"));
                if (radio == musicOn) {   
            		music.play();   
                } else if (radio == musicOff) {   
            		music.stop();  
                } 
//                else if(radio == soundOn){   
//                	sound.play();
//                	win.play();
//                }else if(radio == soundOff){   
//                    sound.stop();  
//                    win.stop();
//                }
			}
		};
		
        soundEffectPanel.add(musicPanel);
        musicPanel.setLayout(new BoxLayout(musicPanel, BoxLayout.X_AXIS));
       // musicPanel.setPreferredSize(new Dimension(300,100));
        musicPanel.add(musicL);
        musicR.add(musicOn);
        musicOff.setSelected(true);
        musicOn.addActionListener(mListener);
        musicOff.addActionListener(mListener);
        musicR.add(musicOff);
        musicPanel.add(musicOn);
        musicPanel.add(musicOff);
        
        //sound
        soundEffectPanel.add(soundPanel);
        soundPanel.setLayout(new BoxLayout(soundPanel, BoxLayout.X_AXIS));
        soundPanel.add(soundL);
        soundOn.setSelected(true);
        soundR.add(soundOn);
        soundR.add(soundOff);
        soundPanel.add(soundOn);
        soundPanel.add(soundOff);
        
        //start&stop button
        mUserSettingsPanel.add(controlPanel);
        controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
      //  controlPanel.setPreferredSize(new Dimension(560, 20));
        
        controlPanel.add(mStartButton);
        controlPanel.add(mStopButton);
        controlPanel.add(mReStartButton);
        
       
       
        
    }
    

    public void addActionListener(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public AbstractController getParentController() {
        return parentController;
    }

    @Override
    public void addSubController(AbstractController controller) {
        mUserSettingsPanel.add(controller.getComponent());
        
    }

    @Override
    public Component getComponent() {
        return mComponent;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        if (command.equals(Constants.BUTTON_EVENT_START) ||
            command.equals(Constants.BUTTON_EVENT_STOP)||
            command.equals(Constants.BUTTON_EVENT_RESTART)) {
            listener.actionPerformed(actionEvent);
        }
    }
}
