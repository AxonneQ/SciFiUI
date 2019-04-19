/*
        Student Name: Igor Bolek
        Student Number: C17487376
*/
package ie.tudublin;

//local
import ie.tudublin.shapes.*;

//java
import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;

//processing
import processing.core.PApplet;
import processing.core.PShape;
import processing.event.MouseEvent;


public class UI extends PApplet {
        // Input vars:
        boolean[] keys = new boolean[1024];
        float currentPos;
        float destinationPos;

        //Utility vars
        Camera cam;
        Animation animation;
        MousePicker cursor;

        //Input functions
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
              /* if (destinationPos > 10) {
                        destinationPos = 10;
                }
                if (destinationPos < -20) {
                        destinationPos = -20;
                }*/
        }

        public void mousePressed(){
                println(cursor.getCurrentRay());
        }


        
        // Storing ALL Elements (Custom, spheres, radars etc..)
        public ArrayList<UIElement> elements = new ArrayList<UIElement>();

        // Above array segregated into arrays containing specific shapes.
        public ArrayList<CustomShape> custom = new ArrayList<CustomShape>();
        public ArrayList<Sphere> spheres = new ArrayList<Sphere>();
       
        // Planets generated from star information and sphere shapes.
        public ArrayList<Planet> planets = new ArrayList<Planet>();




        //Processing
        public void settings() {
                size(1920, 1080, P3D);
                //fullScreen();
                smooth(8);

                

        }
        PShape ray;
        PlanetMap map;
        public void setup() {
               
                // Load all shapes from csv file
                elements = UIElementLoader.loadUI(this);
                Matrix4f projectionMatrix = MousePicker.createProjectionMatrix();

                // Fix alpha transparency for 3D objects
                //hint(ENABLE_DEPTH_TEST);
                // hint(ENABLE_DEPTH_SORT);
                //hint(DISABLE_DEPTH_TEST); 
                //hint(DISABLE_DEPTH_SORT); 
           
                textMode(SHAPE);

                cam = new Camera(this);
                animation = new Animation(this);
                cursor = new MousePicker(this, cam, projectionMatrix);
                map = new PlanetMap(this);
                

                ray = new PShape();
                ray = createShape();
                ray.beginShape(LINES);
                ray.stroke(255);
                ray.fill(255);
                ray.vertex(width/2,height/2, 600);
                ray.vertex(width/2,height/2, -1000);
                ray.endShape(CLOSE);
        }

        // Main loop
        public void draw() {
                // Move camera using mouse
                cam.moveEye(mouseX, mouseY, currentPos = lerp(currentPos, destinationPos, 0.1f));
                //println(cam.toString());
                cursor.update();
               
                

                // Clear canvas
                background(0);


                //lights();
                specular(0,124,224);
                lightFalloff(1.0f, 0.03f, 0.0f);
                pointLight(255,255,255,width/2, height/2 + 350, -100);
                pointLight(255,255,255,width,height/2,0);
                pointLight(255,255,255,0,height/2,0);
                

                lightFalloff(5f, 0.0f, 0.0f);
                pointLight(255, 255, 255, mouseX, mouseY, 200);
                
                
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
                String prev = "";
                for (UIElement e : elements) {
                        //Render an active planet before hologram cones get rendered.
                       
                        if(e.type.equals("CONE") && !prev.equals("CONE")){
                                for(Planet p : planets){
                                        if(p.isActive){
                                                p.update();
                                                p.render();
                                        }
                                }
                        }
                        if(!e.type.equals("PLANET")){
                                e.update();
                                e.render();
                        }
                        prev = e.type;
                }

                fill(0,0,0,255);
                noStroke();
                translate(0,0,200);
                //rect(0,0,width,height/2-100);
                
                stroke(255);
                fill(255);
                cursor.drawRay();


             

        }
}
