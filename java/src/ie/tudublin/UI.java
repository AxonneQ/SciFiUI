/*
        Student Name: Igor Bolek
        Student Number: C17487376
*/

package ie.tudublin;

import java.util.ArrayList;
import processing.core.PApplet;

public class UI extends PApplet {
        // Input:
        boolean[] keys = new boolean[1024];

        public void keyPressed() {
                keys[keyCode] = true;
        }

        public void keyReleased() {
                keys[keyCode] = false;
        }

        public boolean checkKey(int c) {
                return keys[c] || keys[Character.toUpperCase(c)];
        }

        // Initial
       ArrayList<UIElement> elements = new ArrayList<UIElement>();
       

        public void settings() {
                size(1200, 1200, P3D);
        }
       
        public void setup() {
                
                elements = UIElementLoader.loadUI(this);


        }
        // Main loop
        public void draw() {

                background(0);
                stroke(255);
                for(UIElement e : elements){
                        e.update();
                        e.render();
                }


        }
}
