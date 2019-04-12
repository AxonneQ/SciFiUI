/*
        Student Name: Igor Bolek
        Student Number: C17487376
*/

package ie.tudublin;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;
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

        PShape cone = new PShape();
        float angle = 0;
        float radius = 75;
        int cylinderLength;

        // vertices
        PVector vertices[][];
        boolean isPyramid = false;

        public void setup() {
                cylinderLength = height / 2;
                // Load all shapes from csv file
                elements = UIElementLoader.loadUI(this);

                // Fix alpha transparency for 3D objects
                hint(ENABLE_DEPTH_TEST);
                hint(ENABLE_DEPTH_SORT);
                //hint(DISABLE_DEPTH_TEST); 
                //hint(DISABLE_DEPTH_SORT); 

                vertices = new PVector[2][30 + 1];
                for (int i = 0; i < 2; i++) {
                        angle = 0;
                        for (int j = 0; j <= 30; j++) {
                                vertices[i][j] = new PVector();
                                // converged point
                                if (i == 1) {
                                        vertices[i][j].x = width / 2;
                                        vertices[i][j].z = -200;
                                        vertices[i][j].y = cylinderLength + 400;
                                } else { // cone base
                                        vertices[i][j].x = cos(radians(angle)) * radius + (width / 2);
                                        vertices[i][j].z = sin(radians(angle)) * radius - 200;
                                        vertices[i][j].y = cylinderLength+70;
                                }

                                angle += 360.0 / 30;
                        }
                }
                //println(cylinderLength);
                cone = createShape();

                cone.beginShape(QUAD_STRIP);
                cone.fill(0,122,204,100);
                //cone.stroke(255,255,255,255);
                cone.noStroke();
                        for (int j = 0; j <= 30; j++) {
                                cone.vertex(vertices[0][j].x, vertices[0][j].y, vertices[0][j].z);
                                cone.vertex(vertices[1][j].x, vertices[1][j].y, vertices[1][j].z);
                        }                        
                cone.endShape(CLOSE);

        }

        // Main loop
        public void draw() {
                // Move camera using mouse
                cam.moveEye(mouseX, mouseY, currentPos = lerp(currentPos, destinationPos, 0.1f));

                // Clear canvas
                background(0);

                // stroke(255);
                point(0, 0);
                lights();

                pointLight(51, 102, 126, width / 2, height / 2 + 300, -200);
                if (checkKey('s')) {
                        for (CustomShape e : custom) {
                                e.strokeState(true);
                        }
                } else {
                        for (CustomShape e : custom) {
                                e.strokeState(false);
                        }
                }


                
                
                cone.draw(g);
                for (UIElement e : elements) {

                        e.update();
                        e.render();
                }
                


        }
}
