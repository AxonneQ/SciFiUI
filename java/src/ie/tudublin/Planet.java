package ie.tudublin;

//local
import ie.tudublin.shapes.Sphere;
import ie.tudublin.shapes.Orbit;

//java
import java.util.Random;
import java.util.ArrayList;

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
        private PVector lightPos;

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


                        for (int i = 0; i < moonCount; i++) {
                                moonRadius = radius / (ui.random(15) + 5);
                                randomAngle = min + r.nextFloat() * (max - min);

                                // space out the moons every 40 px
                                moonPos.x += 40;

                                moons.add(new Sphere(ui, new PVector(moonPos.x, moonPos.y, moonPos.z), moonRadius,
                                                "ROTATE", randomAngle, axisAngle));
                                orbitRings.add(new Orbit(ui, position, (position.x - moonPos.x), axisAngle));

                        }
                }
                initMoonPos();
                lightPos = generateLightSource();
                ui.planets.add(this);
        }

        private void initMoonPos() {
                //Set initial position of moons to be on a random vertex of the orbit (between 0 - 29)
                for (int i = 0; i < moonCount; i++) {
                        int rand = (int)ui.random(0, orbitRings.get(i).getVerts().length);
                        PVector randPosOnOrbit = orbitRings.get(i).getVert(rand);
                        moons.get(i).setPos(randPosOnOrbit);
                        current[i] = rand;
                }
        }

        private PVector generateLightSource(){
                PVector randLightSource;
                if(moonCount > 2){
                        // if there are more than 2 moons, select random vertex of the last orbit as light source then adjust height to be random.
                        int rand = (int)ui.random(0, orbitRings.get(moonCount-1).getVerts().length);
                        randLightSource = orbitRings.get(moonCount-1).getVert(rand);
                        randLightSource.y = ui.random(-200, 200) + ui.height/2;
                        randLightSource.x += ui.width/2; 
                } else {
                        //else generate a default light source
                        randLightSource = new PVector(ui.random(-200, 200) + ui.width/2, ui.random(-200, 200) + ui.height/2, ui.random(-50, 50));
                }

                return randLightSource;
        }

        public void update() {
                
                for (int i = 0; i < moonCount; i++) {
                        //Decrement to go anti-clockwise around the orbit
                        if(current[i] <= 0){
                                current[i] = orbitRings.get(i).getVerts().length - 1;
                        }
                        PVector toNext = PVector.sub(orbitRings.get(i).getVert(current[i]), moons.get(i).getPos());
                        toNext.x += ui.width/2;
                        toNext.y += ui.height/2;
                        toNext.z += -200;
                        float dist = toNext.mag();
                        
                        if(ui.frameCount % (i + 1 ) <= 1){
                                toNext.normalize();
                                moons.get(i).getPos().add(toNext);
                        }
                        
                        if (dist < 1) {                      
                                        current[i]--;
                        }
                }
        }

        public void render() {

                ui.pushMatrix();

                //Render main planet
                ui.lightFalloff(0.9f,0f,0f);
                ui.pointLight(255,255,255, lightPos.x, lightPos.y, lightPos.z);
                mainPlanet.render();
               // ui.pointLight(255,255,255, lightPos.x, lightPos.y, lightPos.z);

                //Render its moons if any
                for (Sphere m : moons) {
                        m.renderRotated();
                }

                //Render planet orbit if moons present
                for (Orbit o : orbitRings) {         
                        o.render();
                }

                ui.popMatrix();
        }
}
