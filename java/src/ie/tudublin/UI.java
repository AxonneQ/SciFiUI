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
        // Input vars:
        boolean[] keys = new boolean[1024];
        float currentPos;
        float destinationPos = -5;
        Console console;

        // Utility vars
        Camera cam;
        Animation animation;
        boolean fullscreen = true;              //<--- Change here for fullscreen/windowed mode
        PlanetMap map;

        // Input functions
        public void mouseWheel(MouseEvent event) {
                destinationPos = currentPos + event.getCount() * 5;
                if (fullscreen) {
                        if (destinationPos > -5) {
                                destinationPos = -5;
                        }
                        if (destinationPos < -30) {
                                destinationPos = -30;
                        }
                } else {
                        if (destinationPos > 10) {
                                destinationPos = 10;
                        }
                        if (destinationPos < -20) {
                                destinationPos = -20;
                        }
                }
        }

        // Storing ALL Elements (Custom, spheres, radars etc..)
        public ArrayList<UIElement> elements = new ArrayList<UIElement>();

        // Above array segregated into arrays containing specific shapes.
        public ArrayList<CustomShape> custom = new ArrayList<CustomShape>();

        // Planets generated from star information and sphere shapes.
        public ArrayList<Planet> planets = new ArrayList<Planet>();

        // Processing
        public void settings() {
                if (fullscreen) {
                        fullScreen(P3D);
                } else {
                        size(1920, 1080, P3D);
                }
                smooth(8);
        }

        public void setup() {
                // Load all shapes and planets from csv file
                elements = UIElementLoader.loadUI(this);

                //Initialise all variables
                cam = new Camera(this);
                animation = new Animation(this);
                map = new PlanetMap(this);
                console = new Console(this);

                // Remove strokes from the room
                for (CustomShape c : custom) {
                        c.strokeState(false);
                }
        }

        // Main loop
        public void draw() {
                render3D(); // render room
                render2D(); // render GUI
        }

        public void render3D() {
                pushMatrix();

                hint(ENABLE_DEPTH_TEST);

                // Update camera position
                cam.moveEye(mouseX, mouseY, currentPos = lerp(currentPos, destinationPos, 0.1f));

                // Clear last frame
                background(0);

                // General Lighting
                lightFalloff(5f, 0.00f, 0.0f);
                ambientLight(0, 124, 204);
                specular(0, 124, 224);
                lightFalloff(3f, 0.01f, 0.0f);
                pointLight(255, 255, 255, width / 2, height / 2 + 350, -100);

                // Render all items
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
                // Enable lights so that the GUI is not affected by the 3D lights
                lights();
                console.update();
                console.render();
                popMatrix();
        }
}
