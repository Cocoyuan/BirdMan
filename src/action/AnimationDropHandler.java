package action;

import javax.swing.*;

import controller.CanvasController;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HighLengCo on 15/10/30.
 */
public class AnimationDropHandler extends DropTargetAdapter {
    private DropTarget dropTarget;
    private final JPanel canvasPanel;
    private DataFlavor dataFlover;
    private CanvasController canvasController;
    public int brickX, brickY;

    //private CanvasController cc;
    

    // drop target listener for drag and drop functionality
    public AnimationDropHandler(JPanel canvasPanel, DataFlavor dataFlover, CanvasController canvasController) {
        this.canvasPanel = canvasPanel;
        this.dropTarget  = new DropTarget(canvasPanel, DnDConstants.ACTION_COPY, this, true, null);
        
        this.dataFlover  = dataFlover;
        this.canvasController = canvasController;
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        try {
            Transferable tr = event.getTransferable();
            if (event.isDataFlavorSupported(dataFlover) && event.getDropAction() == DnDConstants.ACTION_COPY) {
                AnimatedImage image = (AnimatedImage) tr.getTransferData(dataFlover);
                image.setDragEnabled(true);
                image.addToContainer(canvasPanel);
                canvasController.addCompoenetToList(image);
                image.setLocation(event.getLocation());
                brickX= event.getLocation().x;
                System.out.println(brickX);
                //cc.brickLocationX(brickX);
                brickY= event.getLocation().y;
                //cc.brickLocationY(brickY);
                this.setBrickX(brickX);
                this.setBrickY(brickY);
                this.setLocal(brickX, brickY);
                image.setSize(new Dimension(150, 50));
                event.acceptDrop(DnDConstants.ACTION_COPY);
                event.dropComplete(true);
                canvasPanel.repaint();
            } else if (event.isDataFlavorSupported(dataFlover) && event.getDropAction() == DnDConstants.ACTION_MOVE) {
                AnimatedImage component = (AnimatedImage) tr.getTransferData(dataFlover);
                canvasPanel.add(component);
                component.setLocation(event.getLocation());
                event.acceptDrop(DnDConstants.ACTION_MOVE);
                event.dropComplete(true);
                canvasPanel.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
            event.rejectDrop();
        }
    }

    public void setLocal(int brkX, int brkY){
    	System.out.println("in " + brickX + " " + brickY);
    	
    	brickX = brkX;
    	brickY = brkY;
    }
    
    public void setBrickX(int bX){
    	System.out.println("cocox" + brickX);
    	bX = brickX;
    }
    
    public void setBrickY(int bY){
    	System.out.println("cocoy" + brickY);
    	bY = brickY;
    }
    
    public int getBrickX(){
    	this.brickX = brickX;
    	System.out.println(brickX);
    	return brickX;
    }
    
    public int getBrickY(){	
    	return brickY;
    }
}



//    private static JPanel createJLabelPanel() throws Exception {
//        JPanel panel = new JPanel();
//        panel.setBorder(new TitledBorder("Drag Image from here to Panel above"));
//
//        JLabel label1 = new JLabel(new ImageIcon(new URL("http://i.stack.imgur.com/gJmeJ.png")));
//        JLabel label2 = new JLabel(new ImageIcon(new URL("http://i.stack.imgur.com/8BGfi.png")));
//        JLabel label3 = new JLabel(new ImageIcon(new URL("http://i.stack.imgur.com/1lgtq.png")));
//
//        AnimatedDragGestureListener dlistener = new AnimatedDragGestureListener();
//        DragSource ds1 = new DragSource();
//        ds1.createDefaultDragGestureRecognizer(label1, DnDConstants.ACTION_COPY, dlistener);
//
//        DragSource ds2 = new DragSource();
//        ds2.createDefaultDragGestureRecognizer(label2, DnDConstants.ACTION_COPY, dlistener);
//
//        DragSource ds3 = new DragSource();
//        ds3.createDefaultDragGestureRecognizer(label3, DnDConstants.ACTION_COPY, dlistener);
//
//        panel.add(label1);
//        panel.add(label2);
//        panel.add(label3);
//        return panel;
//    }

//class AnimatedDragGestureListener implements DragGestureListener {
//
//    @Override
//    public void dragGestureRecognized(DragGestureEvent event) {
//        JLabel label = (JLabel) event.getComponent();
//        final Icon ico = label.getIcon();
//
//        Transferable transferable = new Transferable() {
//            @Override
//            public DataFlavor[] getTransferDataFlavors() {
//                return new DataFlavor[]{DataFlavor.imageFlavor};
//            }
//
//            @Override
//            public boolean isDataFlavorSupported(DataFlavor flavor) {
//                if (!isDataFlavorSupported(flavor)) {
//                    return false;
//                }
//                return true;
//            }
//
//            @Override
//            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
//                return ico;
//            }
//        };
//        event.startDrag(null, transferable);
//    }
//}