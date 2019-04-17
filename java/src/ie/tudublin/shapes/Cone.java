package ie.tudublin.shapes;

//local
import ie.tudublin.*;
import ie.tudublin.exceptions.*;

//processing
import processing.core.PShape;
import processing.core.PVector;
import processing.core.PApplet;
import static processing.core.PConstants.*;

public class Cone extends UIElement {
        private PShape cone;
        private String[] rawData;
        private float radius;
        private PVector tip;
        private PVector base;
        static final int resolution = 30;
        private PVector[] vertices;
        private Color stroke;
        private Color fill;
        private String animationType;
        private float[] animationVars;
        

        public Cone(UI ui, String[] rawData) {
                super(ui, rawData[0]);
                this.rawData = rawData;
                createCone();
        }

        private void createCone() {
                String delimiter = ":";
                String[] rawTipPos = rawData[4].split(delimiter);
                String[] rawBasePos = rawData[5].split(delimiter);
                radius = Float.parseFloat(rawData[3]);
                float angle = 0;
                stroke = new Color(rawData[1]);
                fill = new Color(rawData[2]);

                tip = new PVector(Float.parseFloat(rawTipPos[0] + ui.width / 2),          //x
                                                    Float.parseFloat(rawTipPos[1]) + ui.height / 2,         //y 
                                                    Float.parseFloat(rawTipPos[2]));                                   //z

                base = new PVector(Float.parseFloat(rawBasePos[0]) + ui.width / 2,
                                                        Float.parseFloat(rawBasePos[1]) + ui.height / 2, 
                                                        Float.parseFloat(rawBasePos[2]));
                
                // Create Base verts using dark magic trigonometry
                vertices = new PVector[resolution];
                for(int i = 0; i < resolution; i++){
                        vertices[i] = new PVector();
                        vertices[i].x = PApplet.cos(PApplet.radians(angle)) * radius;
                        vertices[i].z = PApplet.sin(PApplet.radians(angle)) * radius;
                        vertices[i].y = base.y;
                        angle += 360.0 / resolution;
                }

                cone = new PShape();
                cone = ui.createShape();
                cone.beginShape(QUAD_STRIP);
                        cone.stroke(stroke.r, stroke. g, stroke.b, stroke.a);
                        cone.fill(fill.r, fill.g, fill.b, fill.a);
                        for(PVector v : vertices){
                                cone.vertex(0, tip.y, 0);
                                cone.vertex(v.x, v.y, v.z);
                        }
                cone.endShape(CLOSE);

                 //Load additional Animation arguments
                 if(rawData.length > 6) {
                        animationType = rawData[6];
                        int noOfArgs = rawData.length - 7;
                        animationVars = new float[noOfArgs];
                        for(int i = 0; i < noOfArgs; i++)
                        {
                                animationVars[i] = (Float.parseFloat(rawData[i + 7]));
                        }
                }
        }

        public void update() {

        }

        public void render() {
                ui.pushMatrix();
                ui.translate(tip.x,0,tip.z);
                if(!animationType.isBlank()){
                        try{
                                Animation.getAnimation(animationType, animationVars);
                        } catch (NoSuchAnimationException e){
                                System.out.println(e);
                        }
                }
                ui.shape(cone);
                ui.popMatrix();
        }

}