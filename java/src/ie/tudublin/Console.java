package ie.tudublin;

//processing
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PVector;
import static processing.core.PConstants.*;

public class Console {
        private UI ui;

        //bools
        public boolean holoIsActive;
        public boolean isScanned;
        public boolean isPowered;
        public boolean isZoomed;

        private PGraphics controls;
        private PShape board;
        private float halfW;
        private float halfH;
        private float timer;
        private PVector relMouse;
        private int scanTimer;

        // Button colors
        private static final String RED = "#c61801ff";
        private static final String GREEN = "#13af15ff";
        private static final String INACTIVE = "#c6180144";

        private Color holoCol;
        private Color scanCol;
        private Color nextPlanetCol;
        private Color prevPlanetCol;
        private Color zoomCol;
        private Color powerCol;

        public Console(UI ui) {
                this.ui = ui;
                holoIsActive = false;
                isScanned = false;
                isPowered = false;
                halfW = ui.width / 2;
                halfH = ui.height / 2;
                relMouse = new PVector(ui.mouseX - halfW, ui.mouseY - halfH);
                controls = ui.createGraphics(ui.width, ui.height);
                timer = ui.millis();
                scanTimer = 0;
                holoCol = new Color(INACTIVE);
                scanCol = new Color(INACTIVE);
                nextPlanetCol = new Color(INACTIVE);
                prevPlanetCol = new Color(INACTIVE);
                zoomCol = new Color(INACTIVE);
                powerCol = new Color(RED);

                createConsole();
        }

        // Create main board without buttons
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
                ui.textAlign(LEFT);
                setRelativeMousePos();

                ui.pushMatrix();
                ui.translate(ui.width / 2, ui.height / 2);
                ui.shape(board);
                controls.beginDraw();
                controls.translate(ui.width / 2, ui.height / 2);
                controls.fill(255, 255, 255, 100);
                controls.noStroke();

                //Update all buttons
                powerStateButton();
                scanButton();
                holoStateButton();
                nextPlanet();
                prevPlanet();
                zoom();

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

        private void powerStateButton() {
                PVector start = new PVector(-150, halfH - 125);
                PVector dim = new PVector(30, 25);
                PVector end = new PVector(start.x + dim.x / 2, start.y + dim.y / 2);

                ui.pushMatrix();
                if (ui.millis() - timer >= 300) {
                        if (relMouse.x > start.x - dim.x / 2 && relMouse.x < end.x && relMouse.y > start.y - dim.y / 2
                                        && relMouse.y < end.y && ui.mousePressed) {
                                if (isPowered) {
                                        powerCol = new Color(RED);
                                        isPowered = false;
                                        isScanned = false;
                                        holoIsActive = false;
                                        ui.planets.get(ui.map.currentPlanet).isActive = false;

                                        holoCol = new Color(INACTIVE);
                                        scanCol = new Color(INACTIVE);

                                } else {
                                        powerCol = new Color(GREEN);
                                        scanCol = new Color(RED);
                                        isPowered = true;
                                        ui.map.currentPlanet = 0;
                                }
                                timer = ui.millis();
                        }
                }
                ui.translate(start.x, start.y - 5);
                ui.scale(0.2f);
                ui.fill(255, 0, 0, 255);
                ui.text("POWER", 120, 60);
                ui.popMatrix();
                controls.fill(powerCol.r, powerCol.g, powerCol.b, powerCol.a);
                controls.ellipse(start.x, start.y, dim.x, dim.y);
        }

        private void holoStateButton() {
                PVector start = new PVector(-160, halfH - 35);
                PVector dim = new PVector(30, 25);
                PVector end = new PVector(start.x + dim.x / 2, start.y + dim.y / 2);

                ui.pushMatrix();
                if (ui.millis() - timer >= 300 && isPowered) {
                        if (relMouse.x > start.x - dim.x / 2 && relMouse.x < end.x && relMouse.y > start.y - dim.y / 2
                                        && relMouse.y < end.y && ui.mousePressed && isScanned) {
                                if (holoIsActive) {
                                        holoCol = new Color(RED);
                                        zoomCol = new Color(INACTIVE);
                                        ui.planets.get(ui.map.currentPlanet).isActive = false;
                                        holoIsActive = false;
                                } else {
                                        holoCol = new Color(GREEN);
                                        zoomCol = new Color(RED);
                                        ui.planets.get(ui.map.currentPlanet).isActive = true;
                                        holoIsActive = true;
                                }
                                timer = ui.millis();
                        } else if (isScanned && !holoIsActive) {
                                holoCol = new Color(RED);
                        }
                } else {

                }
                ui.translate(start.x, start.y - 5);
                ui.scale(0.2f);
                ui.fill(255, 0, 0, 255);
                ui.text("HOLOGRAM", 120, 60);
                ui.popMatrix();
                controls.fill(holoCol.r, holoCol.g, holoCol.b, holoCol.a);
                controls.ellipse(start.x, start.y, dim.x, dim.y);
        }

        private void nextPlanet() {
                PVector start = new PVector(130, halfH - 115);
                PVector dim = new PVector(30, 25);
                PVector end = new PVector(start.x + dim.x / 2, start.y + dim.y / 2);

                ui.pushMatrix();

                if (isScanned && isPowered && holoIsActive) {
                        nextPlanetCol = new Color(GREEN);
                        if (relMouse.x > start.x - dim.x / 2 && relMouse.x < end.x && relMouse.y > start.y - dim.y / 2
                                        && relMouse.y < end.y && ui.mousePressed) {
                                if (ui.millis() - timer >= 300) {
                                        ui.map.next();
                                        timer = ui.millis();
                                }
                        }
                } else {
                        nextPlanetCol = new Color(INACTIVE);
                }
                ui.translate(start.x, start.y - 5);
                ui.scale(0.2f);
                ui.fill(255, 0, 0, 255);
                ui.text("->", 120, 60);
                ui.popMatrix();
                controls.fill(nextPlanetCol.r, nextPlanetCol.g, nextPlanetCol.b, nextPlanetCol.a);
                controls.ellipse(start.x, start.y, dim.x, dim.y);
        }

        private void prevPlanet() {
                PVector start = new PVector(80, halfH - 115);
                PVector dim = new PVector(30, 25);
                PVector end = new PVector(start.x + dim.x / 2, start.y + dim.y / 2);

                ui.pushMatrix();

                if (isScanned && isPowered && holoIsActive) {
                        prevPlanetCol = new Color(GREEN);
                        if (relMouse.x > start.x - dim.x / 2 && relMouse.x < end.x && relMouse.y > start.y - dim.y / 2
                                        && relMouse.y < end.y && ui.mousePressed) {
                                if (ui.millis() - timer >= 300) {
                                        ui.map.prev();
                                        timer = ui.millis();
                                }
                        }
                } else {
                        prevPlanetCol = new Color(INACTIVE);
                }
                ui.translate(start.x, start.y - 5);
                ui.scale(0.2f);
                ui.fill(255, 0, 0, 255);
                ui.text("<-", -200, 60);
                ui.popMatrix();
                controls.fill(prevPlanetCol.r, prevPlanetCol.g, prevPlanetCol.b, prevPlanetCol.a);
                controls.ellipse(start.x, start.y, dim.x, dim.y);
        }

        private void zoom() {
                PVector start = new PVector(160, halfH - 35);
                PVector dim = new PVector(30, 25);
                PVector end = new PVector(start.x + dim.x / 2, start.y + dim.y / 2);

                ui.pushMatrix();

                if (holoIsActive) {
                        if (relMouse.x > start.x - dim.x / 2 && relMouse.x < end.x && relMouse.y > start.y - dim.y / 2
                                        && relMouse.y < end.y && ui.mousePressed) {
                                if (ui.millis() - timer >= 300) {
                                        if (isZoomed) {
                                                if (ui.fullscreen) {
                                                        ui.destinationPos = -5;
                                                } else {
                                                        ui.destinationPos = 10;
                                                }

                                                zoomCol = new Color(RED);
                                                isZoomed = false;
                                        } else {
                                                if (ui.fullscreen) {
                                                        ui.destinationPos = -30;
                                                } else {
                                                        ui.destinationPos = -20;
                                                }

                                                zoomCol = new Color(GREEN);
                                                isZoomed = true;
                                        }
                                        timer = ui.millis();

                                }
                        }
                } else {
                        zoomCol = new Color(INACTIVE);
                }
                ui.translate(start.x, start.y - 5);
                ui.scale(0.2f);
                ui.fill(255, 0, 0, 255);
                ui.text("ZOOM", -400, 60);
                ui.popMatrix();
                controls.fill(zoomCol.r, zoomCol.g, zoomCol.b, zoomCol.a);
                controls.ellipse(start.x, start.y, dim.x, dim.y);
        }

        private void scanButton() {
                PVector start = new PVector(-155, halfH - 80);
                PVector dim = new PVector(30, 25);
                PVector end = new PVector(start.x + dim.x / 2, start.y + dim.y / 2);

                ui.pushMatrix();

                if (!isScanned && isPowered) {
                        if (relMouse.x > start.x - dim.x / 2 && relMouse.x < end.x && relMouse.y > start.y - dim.y / 2
                                        && relMouse.y < end.y && ui.mousePressed) {
                                scanTimer = ui.millis();
                                ui.map.isScanning = true;
                                scanCol = new Color(GREEN);
                        }
                        if (ui.millis() - scanTimer > 3000 && ui.map.isScanning) {
                                isScanned = true;
                                ui.map.isScanning = false;
                                scanCol = new Color(INACTIVE);
                        }
                }
                ui.translate(start.x, start.y - 5);
                ui.scale(0.2f);
                ui.fill(255, 0, 0, 255);
                ui.text("SCAN", 120, 60);
                ui.popMatrix();
                controls.fill(scanCol.r, scanCol.g, scanCol.b, scanCol.a);
                controls.ellipse(start.x, start.y, dim.x, dim.y);
        }
}