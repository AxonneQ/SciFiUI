package ie.tudublin.shapes;

//local
import ie.tudublin.*;

//processing
import processing.core.PShape;
import processing.core.PVector;
import processing.core.PApplet;
import static processing.core.PConstants.*;

public class Orbit extends UIElement {
        private PShape orbit;
        private String[] rawData;
        private float radius;
        private PVector base;
        static final int resolution = 30;
        private PVector[] vertices;
        private Color stroke;
        private float orbitAngle;

        public Orbit(UI ui, String[] rawData) {
                super(ui, rawData[0]);
                this.rawData = rawData;
                createOrbit();
        }

        public Orbit(UI ui, PVector position, float moonRadius, float orbitAngle) {
                super(ui, "ORBIT");
                this.base = position;
                this.radius = moonRadius;
                this.orbitAngle = orbitAngle;
                this.stroke = new Color(255,255,255,30);
                
                float angle = 0;
                vertices = new PVector[resolution];
                for (int i = 0; i < resolution; i++) {
                        vertices[i] = new PVector();
                        vertices[i].x = PApplet.cos(PApplet.radians(angle)) * radius;
                        vertices[i].y = 0;
                        vertices[i].z = PApplet.sin(PApplet.radians(angle)) * radius;
                        angle += 360.0 / resolution;
                }

                orbit = new PShape();
                orbit = ui.createShape();
                orbit.beginShape();
                orbit.stroke(stroke.r, stroke.g, stroke.b, stroke.a);
                orbit.noFill();
                for (PVector v : vertices) {
                        orbit.vertex(v.x, v.y, v.z);
                }
                orbit.endShape(CLOSE);
        }

        private void createOrbit() {
                String delimiter = ":";
                String[] rawBasePos = rawData[2].split(delimiter);
                radius = Float.parseFloat(rawData[3]);
                float angle = 0;
                stroke = new Color(rawData[1]);

                base = new PVector(Float.parseFloat(rawBasePos[0]) + ui.width / 2,
                                Float.parseFloat(rawBasePos[1]) + ui.height / 2, Float.parseFloat(rawBasePos[2]));

                // Create Base verts using dark magic trigonometry
                vertices = new PVector[resolution];
                for (int i = 0; i < resolution; i++) {
                        vertices[i] = new PVector();
                        vertices[i].x = PApplet.cos(PApplet.radians(angle)) * radius;
                        vertices[i].y = 0;
                        vertices[i].z = PApplet.sin(PApplet.radians(angle)) * radius;
                        angle += 360.0 / resolution;
                }

                orbit = new PShape();
                orbit = ui.createShape();
                orbit.beginShape();
                orbit.stroke(stroke.r, stroke.g, stroke.b, stroke.a);
                orbit.noFill();
                for (PVector v : vertices) {
                        orbit.vertex(v.x, v.y, v.z);
                }
                orbit.endShape(CLOSE);

        }

        public void update() {

        }

        public void render() {
                ui.pushMatrix();
                ui.translate(base.x, base.y, base.z);
                ui.rotateZ((float)Math.toRadians(orbitAngle));
                ui.rotateX(-(float)Math.toRadians(orbitAngle));

                ui.shape(orbit);
                ui.popMatrix();
        }
}