package oqm.optimize.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * A JPanel that draws a message with color gradient paint to catch the user's
 * attention.
 */
public class HeaderPanel extends JPanel
{
    private static final int DEFAULT_PANEL_HEIGHT = 50;
    private static final int DEFAULT_PANEL_WIDTH = 400;
    // the width and height of the panel (with default values)
    private int heightOfPanel = DEFAULT_PANEL_HEIGHT;
    private int widthOfPanel = DEFAULT_PANEL_WIDTH;
    // the message that will be displayed
    private String message = "";

    /**
     * Create a panel with the given message and a default height and width.
     * 
     * @param message
     *            The message that will be displayed
     */
    public HeaderPanel(String message)
    {
        this(message, DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT);
    }

    /**
     * Create a panel with the given message and a default height and width.
     * 
     * @param message
     *            The message that will be displayed
     * @param widthOfPanel
     *            The preferred width of the panel.
     * @param heightOfPanel
     *            The preferred height of the panel.
     */
    public HeaderPanel(String message, int widthOfPanel, int heightOfPanel)
    {
        super();

        this.message = message;

        this.setPreferredSize(new Dimension(widthOfPanel, heightOfPanel));
        this.setMinimumSize(new Dimension(widthOfPanel, heightOfPanel));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(new GradientPaint(10, 0, Color.BLUE, 400, 0, Color.BLACK,
                true));

        Fonts fonts = new Fonts();
        g2d.setFont(fonts.getBoldItalicFont(30));

        g2d.drawString(message, 100, heightOfPanel - 20);
    }
}
