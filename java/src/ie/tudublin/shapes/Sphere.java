package ie.tudublin.shapes;

//local
import ie.tudublin.*;
import ie.tudublin.exceptions.*;

//processing
import processing.core.PVector;
import processing.core.PApplet;


public class Sphere extends UIElement {
        private String[] rawData;
        private float size;
        private PVector position;
        private Color stroke;
        private Color fill;

        private String animationType;
        private float[] animationVars;

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

                //Load additional Animation arguments
                if(rawData.length > 5) {
                        animationType = rawData[5];
                        int noOfArgs = rawData.length - 6;
                        animationVars = new float[noOfArgs];
                        for(int i = 0; i < noOfArgs; i++)
                        {
                                animationVars[i] = (Float.parseFloat(rawData[i + 6]));
                        }
                }
                ui.spheres.add(this);
                
        }

        public void update() {
                
        }

        public void render() {
                ui.stroke(stroke.r, stroke.g, stroke.b, stroke.a);
                ui.fill(fill.r, fill.g, fill.b, fill.a);                
                
                ui.pushMatrix();
                ui.hint(PApplet.DISABLE_DEPTH_SORT);   
                ui.translate(position.x, position.y, position.z);
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