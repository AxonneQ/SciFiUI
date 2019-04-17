package ie.tudublin.shapes;

//local
import ie.tudublin.*;
import ie.tudublin.exceptions.*;

//processing
import processing.core.PVector;


public class Sphere extends UIElement {
        private String[] rawData;
        private float size;
        private PVector position;
        private Color stroke;
        private Color fill;

        private String animationType;
        private float[] animationVars;
        private float axisAngle;

        public Sphere(UI ui, String[] rawData) {
                super(ui);
                this.rawData = rawData;
                createSphere();
        }

        public Sphere(UI ui, PVector position, float radius, String rotate, float rotationAngle, float axisAngle){
                super(ui);
                this.size = radius;
                this.position = position;
                this.animationType = rotate;
                this.animationVars = new float[]{0.0f, rotationAngle};
                this.axisAngle = axisAngle;
                stroke = new Color((int)ui.random(255), (int)ui.random(255), (int)ui.random(255));
                fill = new Color((int)ui.random(255), (int)ui.random(255), (int)ui.random(255));
        }

        private void createSphere() {
                String delimiter = ":";
                String[] rawPos = rawData[4].split(delimiter);

                size = Float.parseFloat(rawData[3]);
                position = new PVector(Float.parseFloat(rawPos[0]) + ui.width / 2,
                                Float.parseFloat(rawPos[1]) + ui.height / 2, Float.parseFloat(rawPos[2]));
                stroke = new Color(rawData[1]);
                fill = new Color(rawData[2]);
                axisAngle = Float.parseFloat(rawData[5]);

                //Load additional Animation arguments
                if(rawData.length > 6) {
                        animationType = rawData[6];
                        int noOfArgs = rawData.length - 7;
                        animationVars = new float[noOfArgs];
                        for(int i = 0; i < noOfArgs; i++)
                        {
                                animationVars[i] = (Float.parseFloat(rawData[i + 7]));
                        }

                } else {
                        animationType = "";
                }
                
        }

        public void update() {
                ui.lightFalloff(1.0f, 0.00f, 0.0f);
                ui.pointLight(255,255,255,ui.width/2+200, ui.height/2 -300, -50);
        }

        public void render() {
                ui.stroke(stroke.r, stroke.g, stroke.b, stroke.a);
                ui.fill(fill.r, fill.g, fill.b, fill.a);                
                
                ui.pushMatrix();
               // ui.hint(PApplet.DISABLE_DEPTH_SORT);   
               ui.translate(position.x, position.y, position.z);
               ui.rotateZ((float)Math.toRadians(axisAngle));
               ui.rotateX((float)Math.toRadians(axisAngle) * -1);
                
                if(!animationType.isBlank()){
                        try{
                                Animation.getAnimation(animationType, animationVars);
                        } catch (NoSuchAnimationException e){
                                System.out.println(e);
                        }
                }
                
              
                ui.sphere(size);
             
                ui.popMatrix();
                
                

        }
}