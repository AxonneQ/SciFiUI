package ie.tudublin;

//local
import ie.tudublin.shapes.Sphere;
import ie.tudublin.shapes.Orbit;

//java
import java.util.Random;
import java.util.ArrayList;

import processing.core.PApplet;
//processing
import processing.core.PVector;

public class Planet extends UIElement {
        private String name;
        private float radius;
        private PVector mapPosition;
        private float rotationAngle;
        private float axisAngle;
        private int moonCount;
        private String habitable;
        private long population;
        private float oxygen;
        private String radiation;
        private float water;
        private int[] current;
        private float[] rotation;

        // Sphere vars
        private Sphere mainPlanet;
        private ArrayList<Sphere> moons;
        private ArrayList<Orbit> orbitRings;
        private PVector position;
        public boolean isActive;

        // Raw data from file
        private String[] rawData;

        public Planet(UI ui, String[] rawData) {
                super(ui, rawData[0]);
                this.rawData = rawData;
                createPlanet();
        }

        private void createPlanet() {
                String delimiter = ":";

                name = rawData[1];
                radius = Float.parseFloat(rawData[2]);
                mapPosition = new PVector(Float.parseFloat(rawData[3].split(delimiter)[0]),
                                Float.parseFloat(rawData[3].split(delimiter)[1]));
                rotationAngle = Float.parseFloat(rawData[4]);
                axisAngle = Float.parseFloat(rawData[5]);
                moonCount = Integer.parseInt(rawData[6]);
                habitable = rawData[7];
                population = Long.parseLong(rawData[8]);
                oxygen = Float.parseFloat(rawData[9]);
                radiation = rawData[10];
                water = Float.parseFloat(rawData[11]);

                // Sphere
                position = new PVector(ui.width / 2, ui.height / 2, -200); // position the planet in the middle of
                                                                           // hologram projection
                PVector moonPos = new PVector(position.x + radius, position.y, position.z);
                isActive = true; // Turn off projection by default
                moons = new ArrayList<Sphere>();
                orbitRings = new ArrayList<Orbit>();

                mainPlanet = new Sphere(ui, position, radius, "ROTATE", rotationAngle, axisAngle);

                if (moonCount > 0) {
                        Random r = new Random();
                        float min = 0.0005f;
                        float max = 0.005f;
                        float moonRadius;
                        float randomAngle;
                        current = new int[moonCount];
                        rotation = new float[moonCount];

                        for (int i = 0; i < moonCount; i++) {
                                moonRadius = radius / (ui.random(15) + 5);
                                randomAngle = min + r.nextFloat() * (max - min);

                                // space out the moons every 30 px
                                moonPos.x += 30;

                                moons.add(new Sphere(ui, new PVector(moonPos.x, moonPos.y, moonPos.z), moonRadius,
                                                "ROTATE", randomAngle, axisAngle));
                                orbitRings.add(new Orbit(ui, position, (position.x - moonPos.x), axisAngle));

                        }
                }

                ui.planets.add(this);
        }

        private void moveMoon() {

        }

        public void update() {
                for (int i = 0; i < moonCount; i++) {
                        ui.pushMatrix();
                        if(current[i] <= 0){
                                current[i] = orbitRings.get(i).getVerts().length - 1;
                        }
                        PVector toNext = PVector.sub(orbitRings.get(i).getVert(current[i]), moons.get(i).getPos());
                        toNext.x += ui.width/2;
                        toNext.y += ui.height/2;
                        toNext.z += -200;
                        float dist = toNext.mag();
                        toNext.normalize();
                        moons.get(i).getPos().add(toNext);
                        if (dist < 1) {                      
                                        current[i]--;
                        }
                        ui.popMatrix();
                }

        }

        public void render() {

                ui.pushMatrix();
                mainPlanet.render();
 
                for (Sphere m : moons) {
                        m.renderRotated();
                }
                
                for (Orbit o : orbitRings) {         
                        o.render();
                }

                ui.popMatrix();
        }
}
