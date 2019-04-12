/*
        Student Name: Igor Bolek
        Student Number: C17487376
*/

package ie.tudublin;

import java.util.ArrayList;
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
                if (destinationPos < -10) {
                        destinationPos = -10;
                }
        }

 
        // Storing ALL Elements (Custom, spheres, radars etc..)
        public static ArrayList<UIElement> elements = new ArrayList<UIElement>();

        // Above array segregated into arrays containing specific shapes.
        public static ArrayList<CustomShape> custom = new ArrayList<CustomShape>();
        public static ArrayList<Sphere> spheres = new ArrayList<Sphere>();



       

        public void settings() {
                size(1000, 1000, P3D);
                // fullScreen();
                smooth(8);

        }

        public void setup() {

                // Load all shapes from csv file
                elements = UIElementLoader.loadUI(this);

                // Fix alpha transparency for 3D objects
                hint(ENABLE_DEPTH_TEST);
                hint(ENABLE_DEPTH_SORT); 
        }

        // Main loop
        public void draw() {
                // Move camera using mouse
                cam.moveEye(mouseX, mouseY, currentPos = lerp(currentPos, destinationPos, 0.1f));

                // Clear canvas
                background(0);

                lights();

                pointLight(51, 102, 126, width/2, height/2+300, -200);
                if(checkKey('s')) {
                        for(CustomShape e: custom){
                                e.strokeState(true);
                        }
                } else {
                        for(CustomShape e: custom){
                                e.strokeState(false);
                        }
                }
              
                
                for (UIElement e : elements) {
                        
                        e.update();
                        e.render();
                }

        }
}
