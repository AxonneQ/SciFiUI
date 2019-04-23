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
import processing.core.PGraphics;
import processing.core.PShape;
import processing.event.MouseEvent;

public class UI extends PApplet {
        // Input vars:
        boolean[] keys = new boolean[1024];
        float currentPos;
        float destinationPos;
        Console console;

        // Utility vars
        Camera cam;
        Animation animation;
        MousePicker cursor;

        // Input functions
        public void keyPressed() {
                keys[keyCode] = true;
                map.keyPress(keyCode);
        }

        public void keyReleased() {
                keys[keyCode] = false;
        }

        public boolean checkKey(int c) {
                return keys[c] || keys[Character.toUpperCase(c)];
        }

        public void mouseWheel(MouseEvent event) {
                destinationPos = currentPos + event.getCount() * 5;
                /*
                 * if (destinationPos > 10) { destinationPos = 10; } if (destinationPos < -20) {
                 * destinationPos = -20; }
                 */
        }

        public void mousePressed() {
              
        }

        // Storing ALL Elements (Custom, spheres, radars etc..)
        public ArrayList<UIElement> elements = new ArrayList<UIElement>();

        // Above array segregated into arrays containing specific shapes.
        public ArrayList<CustomShape> custom = new ArrayList<CustomShape>();
        public ArrayList<Sphere> spheres = new ArrayList<Sphere>();

        // Planets generated from star information and sphere shapes.
        public ArrayList<Planet> planets = new ArrayList<Planet>();

        // Processing
        public void settings() {
                size(1920, 1080, P3D);
                // fullScreen();
                smooth(8);
        }

        PShape ray;
        PlanetMap map;

        public void setup() {
                // Load all shapes from csv file
                elements = UIElementLoader.loadUI(this);
                Matrix4f projectionMatrix = MousePicker.createProjectionMatrix();

                // Fix alpha transparency for 3D objects
                // hint(ENABLE_DEPTH_TEST);
                // hint(ENABLE_DEPTH_SORT);
                // hint(DISABLE_DEPTH_TEST);
                // hint(DISABLE_DEPTH_SORT);

                cam = new Camera(this);
                animation = new Animation(this);
                cursor = new MousePicker(this, cam, projectionMatrix);
                map = new PlanetMap(this);
                console = new Console(this);

                ray = new PShape();
                ray = createShape();
                ray.beginShape(LINES);
                ray.stroke(255);
                ray.fill(255);
                ray.vertex(width / 2, height / 2, 600);
                ray.vertex(width / 2, height / 2, -1000);
                ray.endShape(CLOSE);
        }

        // Main loop
        public void draw() {
                render3D(); // render room
                render2D(); // render GUI
        }

        public void render3D() {
                pushMatrix();

                hint(ENABLE_DEPTH_TEST);
                cam.moveEye(mouseX, mouseY, currentPos = lerp(currentPos, destinationPos, 0.1f));

                background(0);

                //General Lighting
                lightFalloff(5f, 0.00f, 0.0f);
                ambientLight(0, 124, 204);
                specular(0, 124, 224);
                lightFalloff(3f, 0.01f, 0.0f);
                pointLight(255, 255, 255, width / 2, height / 2 + 350, -100);

                String prev = "";
                for (UIElement e : elements) {
                        // Render an active planet before hologram cones get rendered.
                        // To prevent overlapping
                        if (e.type.equals("CONE") && !prev.equals("CONE")) {

                                map.update();
                                map.render();
                                lightFalloff(0f, 0f, 0f);
                                for (Planet p : planets) {
                                        if (p.isActive) {
                                                p.update();
                                                p.render();
                                        }
                                }
                        }
                        // Render cones (hologram illusion only if they are activated)
                        if (e.type.equals("CONE")) {
                                if (console.holoIsActive) {
                                        e.update();
                                        e.render();
                                }
                        }

                        // Render everything else
                        if (!e.type.equals("PLANET") && !e.type.equals("PLANETMAP") && !e.type.equals("CONE")) {
                                e.update();
                                e.render();
                        }
                        prev = e.type;
                }
                popMatrix();
        }

        public void render2D() {
                pushMatrix();
                hint(DISABLE_DEPTH_TEST);
                lights();
                console.update();
                console.render();
                popMatrix();

        }
}
