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
                                String[] rawPosition = rawData[i + 4].split(delimiter);

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
                s = new PShape();
                s = ui.createShape();
                s.beginShape();
                s.stroke(255);
                s.fill(255);
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