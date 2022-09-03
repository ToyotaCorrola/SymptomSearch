import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Insets;
import java.awt.geom.Area;


class RoundedBorder extends AbstractBorder {
    private Color color;
    private Color bgColor;
    private int thickness;
    private int radii;
    private BasicStroke stroke = null;
    private int strokePad;
    private int width;
    RenderingHints hints;
    Insets insets = null;

    RoundedBorder(Color color, Color bgColor, int thickness, int radii, int width) {
        this.thickness = thickness;
        this.radii = radii;
        this.color = color;
        this.bgColor = bgColor;
        this.width = width;

        stroke = new BasicStroke(thickness);
        strokePad = thickness / 2;

        hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        int bottomLineY = height - thickness;

        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(strokePad, strokePad, width-thickness, bottomLineY, radii, radii);
        Polygon pointer = new Polygon();

        Area area = new Area(bubble);
        area.add(new Area(pointer));

        g2.setRenderingHints(hints);
        
        Rectangle rect = new Rectangle(0,0, width,height);
        Area borderRegion = new Area(rect);
        borderRegion.subtract(area);
        g2.setClip(borderRegion);
        g2.setColor(bgColor);
        g2.fillRect(0, 0, this.width, height);
        g2.setClip(null);
        g2.setColor(color);
        g2.setStroke(stroke);
        g2.draw(area);
    }
}