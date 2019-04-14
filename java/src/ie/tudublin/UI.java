/*
        Student Name: Igor Bolek
        Student Number: C17487376
*/
package ie.tudublin;

//local
import ie.tudublin.shapes.*;

//java
import java.util.ArrayList;

//processing
import processing.core.PApplet;
import processing.event.MouseEvent;

public class UI extends PApplet {
        // Input:
        boolean[] keys = new boolean[1024];
        float currentPos;
        float destinationPos;
        Camera cam = new Camera(this);
        Animation animation = new Animation(this);

        public void keyPressed() {
                keys[keyCode] = true;
        }

        public void keyReleased() {
                keys[keyCode] = false;
        }

        public boolean checkKey(int c) {
                return keys[c] || keys[Character.toUpperCase(c)];
        }

        public void mouseWheel(MouseEvent event) {
                destinationPos = currentPos + event.getCount() * 5;
                if (destinationPos > 10) {
                        destinationPos = 10;
                }
                if (destinationPos < -20) {
                        destinationPos = -20;
                }
        }

        // Storing ALL Elements (Custom, spheres, radars etc..)
        public ArrayList<UIElement> elements = new ArrayList<UIElement>();

        // Above array segregated into arrays containing specific shapes.
        public ArrayList<CustomShape> custom = new ArrayList<CustomShape>();
        public ArrayList<Sphere> spheres = new ArrayList<Sphere>();

        public void settings() {
                size(1000, 1000, P3D);
                // fullScreen();
                smooth(8);

        }


        // vertices
        

        public void setup() {
               
                // Load all shapes from csv file
                elements = UIElementLoader.loadUI(this);

                // Fix alpha transparency for 3D objects
                //hint(ENABLE_DEPTH_TEST);
               // hint(ENABLE_DEPTH_SORT);
                //hint(DISABLE_DEPTH_TEST); 
                //hint(DISABLE_DEPTH_SORT); 
           
              

        }

        // Main loop
        public void draw() {
                // Move camera using mouse
                cam.moveEye(mouseX, mouseY, currentPos = lerp(currentPos, destinationPos, 0.1f));

                // Clear canvas
                background(0);

                // stroke(255);
                //lights();
                specular(0,124,224);
                lightFalloff(1.0f, 0.03f, 0.0f);
                pointLight(255,255,255,width/2, height/2 + 350, -100);
                pointLight(255,255,255,width,height/2,0);
                pointLight(255,255,255,0,height/2,0);
                
                
                //directionalLight(255, 255, 255, 0, -1, 0);
                
                
                if (checkKey('s')) {
                        for (CustomShape e : custom) {
                                e.strokeState(true);
                        }
                } else {
                        for (CustomShape e : custom) {
                                e.strokeState(false);
                        }
                }
                
                
               //spotLight(255, 255, 255, mouseX, mouseY, 0, 0, 1, 0, 30, 10000);
                //spotLight(255, 255, 255, width/2, height/2, -200, 0, 1, 0, PI/16, 2);
                
                for (UIElement e : elements) {

                        e.update();
                        e.render();
                        
                }
                fill(0,0,0,255);
                noStroke();
                translate(0,0,200);
                rect(0,0,width,height/2-100);
                
/*
                line(0,0,width,height);
                line(width,0,0,height);
   */             

        }
}
