package view;

import javax.swing.*;
import java.awt.*;

public class BackGround extends JPanel {
    private Image img;

    
    public BackGround(String path) {
        img = new ImageIcon(getClass().getResource(path)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (img != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            int imgWidth = img.getWidth(this);
            int imgHeight = img.getHeight(this);

            
            double scaleX = (double) panelWidth / imgWidth;
            double scaleY = (double) panelHeight / imgHeight;
            double scale = Math.max(scaleX, scaleY);

            int newWidth = (int) (imgWidth * scale);
            int newHeight = (int) (imgHeight * scale);

         
            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            g.drawImage(img, x, y, newWidth, newHeight, this);
        }
    }
}