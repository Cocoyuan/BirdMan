package action;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by HighLengCo on 15/10/30.
 */
public class PanelDragMoveHandler implements DragGestureListener, Serializable{

    private final DataFlavor dataFlavor;
    static final long serialVersionUID = 1234903838583928L;
    public PanelDragMoveHandler(DataFlavor dataFlavor) {
        this.dataFlavor = dataFlavor;
    }

    @Override
    public void dragGestureRecognized(DragGestureEvent event) {
        final AnimatedImage component = (AnimatedImage) event.getComponent();

        Transferable transferable = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{dataFlavor};
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor.equals(dataFlavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                return component;
            }
        };

        event.startDrag(null, transferable);
    }



}
