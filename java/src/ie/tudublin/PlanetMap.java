package ie.tudublin;

//local
import ie.tudublin.Planet.Info;

//java
import java.util.ArrayList;

//processing
import processing.core.PFont;
import processing.core.PShape;
import processing.core.PVector;
import static processing.core.PConstants.*;


public class PlanetMap extends UIElement {
        private int currentPlanet;
        private Info info;
        private String[] infoPieces = { "Name: ", "Habitable: ", "Population: ", "Oxygen (%): ", "Water (%): ",
                        "Radiation: ", "Moon Count: ", "Biome: " };
        private int textSize;
        private int lineSpacing;
        private PFont font;
        private PShape displayFrame;

        public PlanetMap(UI ui) {
                super(ui, "PLANETMAP");
                currentPlanet = 0;
                info = ui.planets.get(currentPlanet).info;
                createFrame();

                textSize = 24 * 4;
                font = ui.createFont("Potra.ttf", textSize);
                ui.textFont(font);
                lineSpacing = textSize * 2;

                ui.elements.add(this);
        }

        public void getInfo(int i) {
                info = ui.planets.get(i).info;
        }

        public void update() {
                if(ui.mousePressed){
                        
                        ui.delay(100);
                        ui.planets.get(currentPlanet).isActive = false;
                        currentPlanet = (currentPlanet + 1) % ui.planets.size();
                        ui.planets.get(currentPlanet).isActive = true;
                        info = ui.planets.get(currentPlanet).info;
                }
                // getInfo(0);
        }

        public void render() {

                ui.pushMatrix();

                drawMap();


                drawInfo();
                ui.popMatrix();

        }

        private void drawMap() {
                // Render map
                ui.pushMatrix();
                ui.translate(-800 + ui.width / 2, -200 + ui.height / 2, 50);
                ui.rotateY((float) Math.toRadians(30));

                // load from file:
                ui.fill(0, 0, 0, 120);
                ui.noStroke();
                ui.rect(0, 0, 500, 410);

                for(Planet p : ui.planets) {
                        PVector v = new PVector(p.info.mapPosition.x, p.info.mapPosition.y);
                        ui.pushMatrix();
                        ui.translate(v.x*10, v.y*10, 2);
                        ui.fill(255);
                        ui.sphere(5);

                        if(p.isActive){
                                ui.ellipse(0,0,20,20);
                        }

                        ui.popMatrix();

                }

                drawFrame();
                ui.popMatrix();
        }

        private void drawInfo() {
                // Render Info screen
                ui.pushMatrix();

                ui.translate(375 + ui.width / 2, -200 + ui.height / 2, -200);

                ui.rotateY((float) Math.toRadians(-30));
                ui.fill(0, 0, 0, 80);
                ui.noStroke();
                ui.rect(0, 0, 500, 410);

                drawFrame();

                ui.translate(0, 0, 5);
                ui.lightFalloff(1f, 0, 0);
                ui.pointLight(255,0,0,250,200,100);

                ui.fill(255, 0, 0, 255);
                ui.scale(0.25f);
                int gap = 0;
                for (int i = 0; i < infoPieces.length; i++) {
                        ui.text(infoPieces[i], 60 * 4, 40 * 4 + gap);
                        ui.text(info.infoString.get(i), 100 * 12, 40 * 4 + gap);
                        gap += lineSpacing;
                }


                ui.popMatrix();
        }

        private void createFrame() {
                displayFrame = new PShape();

                ArrayList<PVector> verts = new ArrayList<PVector>();
                verts.add(new PVector(0, 410, 0));
                verts.add(new PVector(-20, 0, 0));
                verts.add(new PVector(-10, -20, 0));
                verts.add(new PVector(240, -30, 0));
                verts.add(new PVector(240, -1000, 0));
                verts.add(new PVector(260, -1000, 0));
                verts.add(new PVector(260, -30, 0));
                verts.add(new PVector(510, -20, 0));
                verts.add(new PVector(520, 0, 0));
                verts.add(new PVector(500, 410, 0));
                verts.add(new PVector(500, 0, 0));
                verts.add(new PVector(0,0,0));


                displayFrame = ui.createShape();
                displayFrame.beginShape();
                displayFrame.fill(0,122,204,255);
                displayFrame.noStroke();
                for (PVector v : verts) {
                        displayFrame.vertex(v.x, v.y, v.z);
                }
                displayFrame.endShape(CLOSE);
        }

        private void drawFrame() {
                ui.shape(displayFrame);

        }

}