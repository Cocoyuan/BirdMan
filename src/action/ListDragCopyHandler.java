package action;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

/**
 * Created by HighLengCo on 15/10/30.
 */
public class ListDragCopyHandler extends TransferHandler{

    private final JList list;
    private final DataFlavor dataFlavor;

    public ListDragCopyHandler(JList list, DataFlavor dataFlavor){
        this.list = list;
        this.dataFlavor = dataFlavor;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{ dataFlavor };
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor.equals(dataFlavor);
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                return  new AnimatedImage((ImageIcon)list.getSelectedValue());
            }
        };
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDataFlavorSupported(dataFlavor)) {;
            return false;
        }

        JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
        if (dl.getIndex() == -1) {
            return false;
        } else {
            return true;
        }
    }
}

