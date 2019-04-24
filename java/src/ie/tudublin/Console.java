package ie.tudublin;

import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;
import static processing.core.PConstants.*;

public class Console {
        private UI ui;
        private PVector position;
        public boolean holoIsActive;
        public boolean isScanned;
        private float width;
        private float height;
        private PGraphics controls;
        private PShape board;
        private float halfW;
        private float halfH;
        private float timer;
        private PVector relMouse;
        private int scanTimer;

        // Button colors
        Color holoCol;
        Color scanCol;
        Color nextPlanetCol;
        Color prevPlanetCol;
        Color zoomCol;

        public Console(UI ui) {
                this.ui = ui;
                holoIsActive = false;
                isScanned = false;
                width = 700;
                height = 700;
                halfW = ui.width / 2;
                halfH = ui.height / 2;
                relMouse = new PVector(ui.mouseX - halfW, ui.mouseY - halfH);
                controls = ui.createGraphics(ui.width, ui.height);
                timer = ui.millis();
                scanTimer = 0;
                holoCol = new Color("#c61801ff");
                scanCol = new Color("#13af15ff");
                nextPlanetCol = new Color("#c61801ff");
                prevPlanetCol = new Color("#c61801ff");
                zoomCol = new Color("#c61801ff");

                createConsole();
        }

        private void createConsole() {
                board = new PShape();
                board = ui.createShape();

                board.beginShape();
                board.fill(0, 16, 27, 255);
                board.stroke(255, 0, 0, 100);
                board.vertex(-230, halfH);
                board.vertex(-200, halfH - 150);
                board.vertex(200, halfH - 150);
                board.vertex(230, halfH);
                board.endShape(CLOSE);

        }

        private void setRelativeMousePos() {
                relMouse.x = (float) ui.mouseX - halfW;
                relMouse.y = (float) ui.mouseY - halfH;
        }

        public void update() {
                setRelativeMousePos();
                ui.pushMatrix();
                ui.translate(ui.width / 2, ui.height / 2);
                //ui.lights();
                ui.shape(board);

                controls.beginDraw();
                controls.translate(ui.width / 2, ui.height / 2);
                controls.fill(255, 255, 255, 100);
                controls.stroke(255);
                scanButton();
                holoStateButton();

                controls.endDraw();

                ui.popMatrix();

        }

        public void render() {

                ui.pushMatrix();
                controls.clear();
                ui.noLights();
                ui.image(controls, 0, 0);

                ui.popMatrix();

        }

        private void holoStateButton() {
                PVector start = new PVector(-150, halfH-50);
                PVector dim = new PVector(50, 30);
                PVector end = new PVector(start.x + dim.x, start.y + dim.y);

                ui.pushMatrix();
                if (ui.millis() - timer >= 300) {
                        if (relMouse.x > start.x && relMouse.x < end.x && relMouse.y > start.y && relMouse.y < end.y
                                        && ui.mousePressed && isScanned) {
                                if (holoIsActive) {
                                        holoCol = new Color("#c61801ff");
                                        ui.planets.get(ui.map.currentPlanet).isActive = false;
                                        holoIsActive = false;
                                } else {
                                        holoCol = new Color("#13af15ff");
                                        ui.planets.get(ui.map.currentPlanet).isActive = true;
                                        holoIsActive = true;
                                }
                                timer = ui.millis();
                        }
                }
                ui.translate(start.x, start.y -5);
                ui.scale(0.2f);
                ui.fill(255,0,0,255);
                ui.text("ON/OFF", -35, 0);
                ui.popMatrix();
                controls.fill(holoCol.r, holoCol.g, holoCol.b, holoCol.a);
                controls.rect(start.x, start.y, dim.x, dim.y);
        }

        private void nextPlanet() {
                PVector start = new PVector(0, 300);
                PVector dim = new PVector(50, 30);
                PVector end = new PVector(start.x + dim.x, start.y + dim.y);

                controls.rect(start.x, start.y, dim.x, dim.y);
        }

        private void prevPlanet() {

        }

        private void scanButton() {
                PVector start = new PVector(-50, halfH - 50);
                PVector dim = new PVector(50, 30);
                PVector end = new PVector(start.x + dim.x, start.y + dim.y);
                ui.pushMatrix();
                
                if (!isScanned) {
                        if (relMouse.x > start.x && relMouse.x < end.x && relMouse.y > start.y && relMouse.y < end.y
                                        && ui.mousePressed) {
                                scanTimer = ui.millis();
                                ui.map.isScanning = true;
                                scanCol = new Color("#c61801ff");
                        }
                        if(ui.millis() - scanTimer > 3000 && ui.map.isScanning){
                                isScanned = true;
                                ui.map.isScanning = false;
                        }
                }
                ui.translate(start.x, start.y -5);
                ui.scale(0.2f);
                ui.fill(255,0,0,255);
                ui.text("SCAN", 0, 0);
                ui.popMatrix();
                controls.fill(scanCol.r, scanCol.g, scanCol.b, scanCol.a);
                controls.rect(start.x, start.y, dim.x, dim.y);
        }
}