package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.StringContent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by HighLengCo on 15/10/28.
 */
public class ImageComponent extends JComponent {

    private ImageIcon image;

    public ImageComponent(String path) {
            image = new ImageIcon(getClass().getClassLoader().getResource(path));
    }

    public ImageComponent(ImageIcon image) {
            this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(),null);
    }

    public void setImage(ImageIcon image) {
        this.image = image;
        repaint();
    }
}
