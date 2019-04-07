package ie.tudublin;

import static processing.core.PConstants.*;

import java.util.ArrayList;
import processing.core.*;

public class CustomShape extends UIElement {
        private ArrayList<PVector> vertices;
        private String[] rawData;
        private PShape s;

        CustomShape(UI ui, String[] rawData) {
                super(ui);
                this.rawData = rawData;
                vertices = new ArrayList<PVector>();
                createVertices();
                makeShape();
        }

        private void createVertices() {
                // 3D shape
                if (rawData[0].contains("CUSTOM3D")) {
                        int totalVerts = Integer.parseInt(rawData[3]);
                        for (int i = 0; i < totalVerts; i++) {
                                String delimiter = ":";
                                String[] rawPosition = rawData[i + 4].split(delimiter); // offset position of first vertex (e.g. 0 + 4 = 1st vertex column)

                                Float x = Float.parseFloat(rawPosition[0]) + (ui.width / 2);
                                Float y = Float.parseFloat(rawPosition[1]) + (ui.height / 2);
                                Float z = Float.parseFloat(rawPosition[2]);

                                PVector vertex = new PVector(x, y, z);

                                vertices.add(vertex);
                        }
                }

                // 2D shape
                if (rawData[0].contains("CUSTOM2D")) {
                        int totalVerts = Integer.parseInt(rawData[1]);
                        for (int i = 0; i < totalVerts; i++) {
                                String delimiter = ":";
                                String[] rawPosition = rawData[i + 2].split(delimiter);
                                PVector vertex = new PVector(Float.parseFloat(rawPosition[0]) + (ui.width / 2),
                                                Float.parseFloat(rawPosition[1]) + (ui.height / 2));
                                vertices.add(vertex);
                        }
                }
        }

        private void makeShape() {
                Color stroke = new Color(rawData[1]);
                Color fill = new Color(rawData[2]);
                

                s = new PShape();
                s = ui.createShape();
                s.beginShape();
                s.stroke(stroke.r,stroke.g, stroke.b, stroke.a);
                s.fill(fill.r, fill.g, fill.b, fill.a);
                for (PVector v : vertices) {
                        s.vertex(v.x, v.y, v.z);
                }
                s.endShape();
        }

        public void update() {

        }

        public void render() {
                s.draw(ui.g);
        }

}