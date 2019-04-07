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
        float mouseWheelState;

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
                mouseWheelState += event.getCount();
                if (mouseWheelState > 10) {
                        mouseWheelState = 10;
                }
                if (mouseWheelState < -10) {
                        mouseWheelState = -10;
                }
        }

        // Initial
        ArrayList<UIElement> elements = new ArrayList<UIElement>();
        Camera cam = new Camera(this);

        public void settings() {
                size(1200, 1200, P3D);
                // fullScreen();
                smooth(8);

        }

        public void setup() {

                //Load all shapes from csv file
                elements = UIElementLoader.loadUI(this);

                //Fix alpha transparency for 3D objects
                hint(DISABLE_DEPTH_TEST);

        }

        // Main loop
        public void draw() {
                //Move camera using mouse
                cam.moveEye(mouseX,mouseY,mouseWheelState);
                //Clear canvas
                background(0);

                stroke(255);
                lights();


                for (UIElement e : elements) {
                        e.update();

                        e.render();
                }

        }
}
