package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Created by HighLengCo on 15/10/29.
 */

public class KeyBoardPress {

    static private HashMap<String, ActionMap>      observersAction= new HashMap<String, ActionMap>();
//    static private HashMap<String, JComponent>     clients   = new HashMap<String, JComponent>();
//    static private HashMap<String, InputMap>       clientsInput   = new HashMap<String, InputMap>();

    static public void addKeyboardAction(String key, JPanel component, final int keyEvent){
//        clients.put(key, component);
        InputMap  im = component.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = component.getActionMap();
        im.put(KeyStroke.getKeyStroke(keyEvent, 0), key);
//        clientsInput.put(key, im);
        observersAction.put(key, am);
    }

    static public void registerAsObserver(String key, AbstractAction action){;
        ActionMap am = observersAction.get(key);
        if (am != null){
            am.put(key, action);
        }
    }

}
