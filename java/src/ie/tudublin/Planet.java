package ie.tudublin;



//local
import ie.tudublin.shapes.Sphere;

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

        //Sphere vars
        private Sphere mainPlanet;
        private ArrayList<Sphere> moons;
        private PVector position;
        public boolean isActive;

        //Raw data from file
        private String[] rawData;


        public Planet(UI ui, String[] rawData) {
                super(ui);
                this.rawData = rawData;
                createPlanet();
        }

        private void createPlanet(){
                String delimiter = ":";
                
                name = rawData[1];
                radius = Float.parseFloat(rawData[2]);
                mapPosition = new PVector(Float.parseFloat(rawData[3].split(delimiter)[0]), Float.parseFloat(rawData[3].split(delimiter)[1]));
                rotationAngle = Float.parseFloat(rawData[4]);
                axisAngle = Float.parseFloat(rawData[5]);
                moonCount = Integer.parseInt(rawData[6]);
                habitable = rawData[7];
                population = Long.parseLong(rawData[8]);
                oxygen = Float.parseFloat(rawData[9]);
                radiation = rawData[10];
                water = Float.parseFloat(rawData[11]);

                //Sphere 
                position = new PVector(ui.width/2, ui.height/2, -200);   //position the planet in the middle of hologram projection
                isActive = false; // Turn off projection by default
                moons = new ArrayList<Sphere>();

                mainPlanet = new Sphere(ui, position, radius, "ROTATE", rotationAngle, axisAngle);

                for(int i = 0; i < moonCount; i++){
                        Random r = new Random();
                        float min = 0.0005f;
                        float max = 0.005f;
                        float randomAngle =  min + r.nextFloat() * (max-min);
                        moons.add(new Sphere(ui, position, radius, "ROTATE", randomAngle, axisAngle));
                }

                ui.planets.add(this);

        }





        public void update() {

        }

        public void render() {
                mainPlanet.render();
                for(Sphere m : moons) {
                        m.render();
                }
        }

}
