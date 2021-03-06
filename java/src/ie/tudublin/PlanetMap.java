package ie.tudublin;

//local
import ie.tudublin.Planet.Info;
import ie.tudublin.shapes.Radar;

//java
import java.util.ArrayList;

//processing
import processing.core.PFont;
import processing.core.PShape;
import processing.core.PVector;
import static processing.core.PConstants.*;

public class PlanetMap extends UIElement {
        public int currentPlanet;
        private Info info;
        private String[] infoPieces = { "Name: ", "Habitable: ", "Population: ", "Oxygen (%): ", "Water (%): ",
                        "Radiation: ", "Moon Count: ", "Biome: " };
        private int textSize;
        private int lineSpacing;
        private PFont font;
        private PShape displayFrame;
        private Radar radar = new Radar(ui, 0.5f, 250f, 205f, 200f);

        public boolean isScanning = false;

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

        public void next() {
                ui.planets.get(currentPlanet).isActive = false;
                currentPlanet = (currentPlanet + 1) % ui.planets.size();
                ui.planets.get(currentPlanet).isActive = true;
                info = ui.planets.get(currentPlanet).info;
        }

        public void prev() {
                ui.planets.get(currentPlanet).isActive = false;
                currentPlanet = currentPlanet - 1;
                if (currentPlanet < 0) {
                        currentPlanet = ui.planets.size() - 1;
                }
                ui.planets.get(currentPlanet).isActive = true;
                info = ui.planets.get(currentPlanet).info;
        }

        public void update() {

        }

        public void render() {
                ui.pushMatrix();
                drawMap();
                drawInfo();
                ui.popMatrix();
        }

        private void drawMap() {
                ui.pushMatrix();
                ui.translate(-800 + ui.width / 2, -200 + ui.height / 2, 50);
                ui.rotateY((float) Math.toRadians(30));
                ui.fill(0, 0, 0, 80);
                ui.noStroke();
                ui.rect(0, 0, 500, 410);
                ui.stroke(255, 0, 0, 50);


                //If the system is powered on, display the left display range meter (circles)
                if (ui.console.isPowered) {
                        ui.line(250, 0, 250, 410);
                        ui.line(0, 205, 500, 205);
                        ui.noFill();
                        for (int i = 0; i < 5; i++) {
                                ui.ellipse(250, 205, 100 * i, 100 * i);
                        }
                }

                // If scan is currently running, display a radar animation
                if (isScanning) {
                        radar.render();
                }

                // If scan is complete, display all planets on the left screen
                if (ui.console.isScanned) {
                        for (Planet p : ui.planets) {
                                PVector v = new PVector(p.info.mapPosition.x, p.info.mapPosition.y);
                                ui.pushMatrix();
                                ui.translate(v.x * 10, v.y * 10, 2);
                                ui.stroke(150, 0, 0, 150);
                                ui.noFill();
                                ui.sphere(5);

                                if (p.isActive) {
                                        ui.stroke(255);
                                        ui.fill(255);
                                        ui.ellipse(0, 0, 25, 25);
                                }
                                ui.popMatrix();
                        }

                }
                drawFrame();
                ui.popMatrix();
        }

        private void drawInfo() {
                ui.pushMatrix();
                ui.translate(375 + ui.width / 2, -200 + ui.height / 2, -200);
                ui.rotateY((float) Math.toRadians(-30));
                ui.fill(0, 0, 0, 80);
                ui.noStroke();
                ui.rect(0, 0, 500, 410);

                drawFrame();

                ui.lights();

                //Display different messages depending on the current system state
                if (ui.console.isScanned && ui.console.holoIsActive) {
                        ui.textAlign(LEFT);
                        ui.translate(0, 0, 5);
                        ui.fill(255, 0, 0, 255);
                        ui.scale(0.25f);        //Scale down big font into small one while keeping the detail 

                        int gap = 0;
                        for (int i = 0; i < infoPieces.length; i++) {
                                ui.text(infoPieces[i], 45 * 4, 40 * 4 + gap);
                                ui.text(info.infoString.get(i), 90 * 12, 40 * 4 + gap);
                                gap += lineSpacing;
                        }

                } else if (isScanning) {
                        ui.textAlign(CENTER);
                        ui.translate(0, 0, 5);
                        ui.fill(255, 0, 0, 255);
                        ui.scale(0.5f);
                        ui.text("Scanning...", 500, 400);
                } else if (ui.console.isPowered && !ui.console.isScanned) {
                        ui.textAlign(CENTER);
                        ui.translate(0, 0, 5);
                        ui.fill(255, 0, 0, 255);
                        ui.scale(0.5f);
                        ui.text("System Ready...", 500, 400);
                        ui.text("Initialize scan...", 500, 500);
                } else if (ui.console.isPowered && ui.console.isScanned) {
                        ui.textAlign(CENTER);
                        ui.translate(0, 0, 5);
                        ui.fill(255, 0, 0, 255);
                        ui.scale(0.5f);
                        ui.text("Scanning \nComplete.", 500, 400);
                }

                ui.noLights();
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
                verts.add(new PVector(0, 0, 0));

                displayFrame = ui.createShape();
                displayFrame.beginShape();
                displayFrame.fill(0, 122, 204, 255);
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