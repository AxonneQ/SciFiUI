package ie.tudublin;

import processing.core.PVector;
import processing.core.PShape;

public class Sphere extends UIElement {
        private String[] rawData;
        private float size;
        private PVector position;
        private Color stroke;
        private Color fill;
        private float rotation;

        public Sphere(UI ui, String[] rawData) {
                super(ui);
                this.rawData = rawData;
                createSphere();
        }

        private void createSphere() {
                String delimiter = ":";
                String[] rawPos = rawData[4].split(delimiter);

                size = Float.parseFloat(rawData[3]);
                position = new PVector(Float.parseFloat(rawPos[0]) + ui.width / 2,
                                Float.parseFloat(rawPos[1]) + ui.height / 2, Float.parseFloat(rawPos[2]));
                stroke = new Color(rawData[1]);
                fill = new Color(rawData[2]);
                ui.spheres.add(this);
        }

        public void update() {
                
        }

        public void render() {
                //ui.stroke(stroke.r, stroke.g, stroke.b, stroke.a);
                //ui.fill(fill.r, fill.g, fill.b, fill.a);

                ui.pushMatrix();
                ui.translate(position.x, position.y, position.z);
               // Animation.getAnimation("ROTATE", 0.01f);
                ui.sphere(size);
                ui.popMatrix();

        }
}