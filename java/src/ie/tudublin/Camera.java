package ie.tudublin;

//local
import ie.tudublin.UI;

//processing
import processing.core.PApplet;
import processing.core.PVector;


public class Camera {
        public UI ui;
        private float width; 
        private float height;
        private float distance;
        public PVector position;

        public Camera(UI ui){
                this.ui = ui;
                width = ui.width/2.0f;
                height = ui.height/2.0f;
                distance = height / PApplet.tan((float) (PApplet.PI * 30.0 / 180.0));
                position = new PVector(width, height, distance);
        }

        public void moveEye(float x, float y, float z) {
                ui.camera(
                        position.x = this.width - ((x - this.width) / 10), 
                        position.y = this.height - ((y - this.height) / 10),
                        position.z = distance + (z * 25),
                        this.width,
                        this.height,
                        0f, 
                        0f, 
                        1f, 
                        0f
                );
                
        }

        public String toString(){
                return "CamPos: X: " + position.x + " Y: " + position.y + " Z: " + position.z;
        }
}