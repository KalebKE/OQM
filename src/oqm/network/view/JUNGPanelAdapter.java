/*
JUNGPanelAdapter -- a class within the OQM(Open Queuing Model).
Copyright (C) 2012, Kaleb Kircher.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package oqm.network.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotationControls;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

/**
 * JUNG Panel Adaptor class uses an Adaptor Pattern to take
 * the MANV Network's and render the them with the JUNG Framework's Graphical Network.
 */
public class JUNGPanelAdapter extends JPanel implements Printable
{

    private ArrayList verticies = new ArrayList();
    private double[][] system;
    private DecimalFormat decimalFormatter;
    private String decFormat = "0.0000";
    private DirectedGraph<MyNode, MyLink> graph;
    private AbstractLayout<MyNode, MyLink> layout;
    //the visual component and renderer for the graph
    private VisualizationViewer<MyNode, MyLink> vv;
    private String instructions =
            "<html>"
            + "<h3>All Modes:</h3>"
            + "<ul>"
            + "<li>Right-click an empty area for <b>Create Vertex</b> popup"
            + "<li>Right-click on a Vertex for <b>Delete Vertex</b> popup"
            + "<li>Right-click on a Vertex for <b>Add Edge</b> menus <br>(if there are selected Vertices)"
            + "<li>Right-click on an Edge for <b>Delete Edge</b> popup"
            + "<li>Mousewheel scales with a crossover value of 1.0.<p>"
            + "     - scales the graph layout when the combined scale is greater than 1<p>"
            + "     - scales the graph view when the combined scale is less than 1"
            + "</ul>"
            + "<h3>Editing Mode:</h3>"
            + "<ul>"
            + "<li>Left-click an empty area to create a new Vertex"
            + "<li>Left-click on a Vertex and drag to another Vertex to create an Undirected Edge"
            + "<li>Shift+Left-click on a Vertex and drag to another Vertex to create a Directed Edge"
            + "</ul>"
            + "<h3>Picking Mode:</h3>"
            + "<ul>"
            + "<li>Mouse1 on a Vertex selects the vertex"
            + "<li>Mouse1 elsewhere unselects all Vertices"
            + "<li>Mouse1+Shift on a Vertex adds/removes Vertex selection"
            + "<li>Mouse1+drag on a Vertex moves all selected Vertices"
            + "<li>Mouse1+drag elsewhere selects Vertices in a region"
            + "<li>Mouse1+Shift+drag adds selection of Vertices in a new region"
            + "<li>Mouse1+CTRL on a Vertex selects the vertex and centers the display on it"
            + "<li>Mouse1 double-click on a vertex or edge allows you to edit the label"
            + "</ul>"
            + "<h3>Transforming Mode:</h3>"
            + "<ul>"
            + "<li>Mouse1+drag pans the graph"
            + "<li>Mouse1+Shift+drag rotates the graph"
            + "<li>Mouse1+CTRL(or Command)+drag shears the graph"
            + "<li>Mouse1 double-click on a vertex or edge allows you to edit the label"
            + "</ul>"
            + "<h3>Annotation Mode:</h3>"
            + "<ul>"
            + "<li>Mouse1 begins drawing of a Rectangle"
            + "<li>Mouse1+drag defines the Rectangle shape"
            + "<li>Mouse1 release adds the Rectangle as an annotation"
            + "<li>Mouse1+Shift begins drawing of an Ellipse"
            + "<li>Mouse1+Shift+drag defines the Ellipse shape"
            + "<li>Mouse1+Shift release adds the Ellipse as an annotation"
            + "<li>Mouse3 shows a popup to input text, which will become"
            + "<li>a text annotation on the graph at the mouse location"
            + "</ul>"
            + "</html>";
    Factory<MyNode> vertexFactory = new VertexFactory();
    Factory<MyLink> edgeFactory = new EdgeFactory();
    // count the number of verticies
    int nodeCount = 0;
    // count the number of edges
    int edgeCount = 0;

    /**
     * Create an instance of a simple graph with popup controls to
     * create a graph.
     *
     */
    public JUNGPanelAdapter(double[][] system)
    {
        this.system = system;
        this.decimalFormatter = new DecimalFormat(decFormat);
        this.setPreferredSize(new Dimension(600, 400));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // create a simple graph for the demo
        graph = new DirectedSparseMultigraph<MyNode, MyLink>();

        this.layout = new StaticLayout<MyNode, MyLink>(graph,
                new Dimension(600, 600));

        if (this.system.length > 0)
        {
            createVerticies();
            drawEdges();
            drawVerticies();
        }

        vv = new VisualizationViewer<MyNode, MyLink>(layout);

        vv.setBackground(Color.white);

        vv.getRenderContext().setVertexLabelTransformer(MapTransformer.<MyNode, String>getInstance(
                LazyMap.<MyNode, String>decorate(new HashMap<MyNode, String>(), new ToStringLabeller<MyNode>())));

        vv.getRenderContext().setEdgeLabelTransformer(
                MapTransformer.<MyLink, String>getInstance(LazyMap.<MyLink, String>decorate(
                new HashMap<MyLink, String>(),
                new ToStringLabeller<MyLink>())));

        vv.setVertexToolTipTransformer(vv.getRenderContext().getVertexLabelTransformer());

        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.N);

        //vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());

        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        this.add(panel);


        final EditingModalGraphMouse<MyNode, MyLink> graphMouse =
                new EditingModalGraphMouse<MyNode, MyLink>(vv.getRenderContext(), vertexFactory, edgeFactory);

        // the EditingGraphMouse will pass mouse event coordinates to the
        // vertexLocations function to set the locations of the vertices as
        // they are created
        // graphMouse.setVertexLocations(vertexLocations);
        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());

        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);

        final ScalingControl scaler = new CrossoverScalingControl();
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                scaler.scale(vv, 1 / 1.1f, vv.getCenter());
            }
        });

        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(vv, instructions);
            }
        });

        AnnotationControls<MyNode, MyLink> annotationControls =
                new AnnotationControls<MyNode, MyLink>(graphMouse.getAnnotatingPlugin());
        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(modeBox);
        controls.add(annotationControls.getAnnotationsToolBar());
        controls.add(help);
        this.add(controls);
        this.setVisible(true);
    }

    public void createVerticies()
    {
        for (int i = 0; i < system.length; i++)
        {
            graph.addVertex(vertexFactory.create());
        }

        Iterator iterate = graph.getVertices().iterator();
        while (iterate.hasNext())
        {
            verticies.add((MyNode) iterate.next());
        }

        Collections.sort(verticies);
    }

    public void drawEdges()
    {
        Iterator iterator = verticies.iterator();

        int count = 0;
        // draw the input layer edges
        while (iterator.hasNext())
        {
            MyNode v = (MyNode) iterator.next();
            for (int i = 0; i < system[count].length; i++)
            {
                if (system[count][i] > 0)
                {
                    graph.addEdge(new MyLink(system[count][i]), (MyNode) this.verticies.get(i), v, EdgeType.DIRECTED);
                    edgeCount++;
                }
            }
            count++;
        }
    }

    public void drawVerticies()
    {
        Iterator iterateVerticies = verticies.iterator();

        int count = 0;
        int x = -50;
        int y = -50;
        while (iterateVerticies.hasNext())
        {
            MyNode v = (MyNode) iterateVerticies.next();

            x = x + 100;

            if (count % 2 == 0)
            {
                y = y + 150;
            }

            if (count % 4 == 0)
            {
                y = y + 200;
                x = 100;
            } else
            {
                y = y - 100;
            }

            layout.setLocation(v, x, y);
            layout.lock(v, true);
            count++;
        }
    }

    public void fireNodes(final double[] result)
    {
        Transformer<MyNode, Paint> vertexPaint = new Transformer<MyNode, Paint>()
        {

            @Override
            public Paint transform(MyNode i)
            {
                return Color.BLUE;
            }
        };

        int count = 0;

        for (double x : result)
        {
            ((MyNode) this.verticies.get(count)).weight = x;

            count++;
        }

        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        vv.getRenderContext().setVertexLabelTransformer(new Transformer<MyNode, String>()
        {

            public String transform(MyNode e)
            {
                return String.valueOf(decimalFormatter.format(e.weight));
            }
        });

        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.N);
        this.repaint();
    }

    @Override
    public int print(java.awt.Graphics graphics,
            java.awt.print.PageFormat pageFormat, int pageIndex)
            throws java.awt.print.PrinterException
    {
        if (pageIndex > 0)
        {
            return (Printable.NO_SUCH_PAGE);
        } else
        {
            java.awt.Graphics2D g2d = (java.awt.Graphics2D) graphics;
            vv.setDoubleBuffered(false);
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            vv.paint(g2d);
            vv.setDoubleBuffered(true);

            return (Printable.PAGE_EXISTS);
        }
    }

    /**
     * copy the visible part of the graph to a file as a jpeg image
     * @param file
     */
    public void writeJPEGImage(File file)
    {
        int width = vv.getWidth();
        int height = vv.getHeight();

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        vv.paint(graphics);
        graphics.dispose();

        try
        {
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class VertexFactory implements Factory<MyNode>
    {

        @Override
        public MyNode create()
        {
            return new MyNode(0);
        }
    }

    class EdgeFactory implements Factory<MyLink>
    {

        @Override
        public MyLink create()
        {
            return new MyLink(0);
        }
    }

    class MyLink
    {

        double weight;   // should be private for good practice
        int id;

        public MyLink(double weight)
        {
            this.id = edgeCount++; // This is defined in the outer class.
            this.weight = weight;
        }

        @Override
        public String toString()
        { // Always good for debugging
            return String.valueOf(weight);
        }
    }

    class MyNode implements Comparable<MyNode>
    {

        double id; // good coding practice would have this as private
        double weight;

        public MyNode(double weight)
        {
            nodeCount++;
            this.weight = weight;
        }

        @Override
        public String toString()
        {
            // Always a good idea for debuging
            return String.valueOf(this.weight);
        }

        public int compareTo(MyNode o)
        {
            return (o.id > this.id ? -1 : (o.id == this.id ? 0 : 1));
        }
    }
}
