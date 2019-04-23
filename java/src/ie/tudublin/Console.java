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
        PGraphics controls;
        PShape[] board = new PShape[2];
        float halfW;
        float halfH;
        float timer;
        PVector relMouse;
        

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
                createConsole();
        }

        private void createConsole() {
                board[0] = new PShape();
                board[0] = ui.createShape();
                board[1] = new PShape();
                board[1] = ui.createShape();
                

                board[0].beginShape();
                board[0].fill(0, 16, 27, 255);
                board[0].stroke(27,0,0,255);
                board[0].vertex(-400, halfH);
                board[0].vertex(-200, halfH - 150);
                board[0].vertex(200, halfH - 150);
                board[0].vertex(400, halfH);
                board[0].endShape(CLOSE);

                board[1].beginShape();
                board[1].vertex(-250,halfH - 30);
                board[1].vertex(-250, halfH - 100);
                board[1].vertex(250, halfH - 100);
                board[1].vertex(250, halfH - 30);
                board[1].endShape(CLOSE);
        }

        



        private void setRelativeMousePos() {
                relMouse.x = (float) ui.mouseX - halfW;
                relMouse.y = (float) ui.mouseY - halfH;
        }

        public void update() {
                setRelativeMousePos();
                ui.pushMatrix();
                ui.translate(ui.width / 2, ui.height / 2);
                for(PShape p : board){
                        ui.shape(p);
                }
              



                controls.beginDraw();
                controls.translate(ui.width / 2, ui.height / 2);
                controls.fill(255, 255, 255, 100);
                controls.stroke(255);
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
                PVector start = new PVector(0, 300);
                PVector dim = new PVector(50, 30);
                PVector end = new PVector(start.x + dim.x, start.y + dim.y);

                controls.rect(start.x, start.y, dim.x, dim.y);

                if (ui.millis() - timer >= 300) {
                        if (relMouse.x > start.x && relMouse.x < end.x && relMouse.y > start.y && relMouse.y < end.y
                                        && ui.mousePressed && isScanned) {
                                if (holoIsActive) {
                                        ui.planets.get(ui.map.currentPlanet).isActive = false;
                                        holoIsActive = false;
                                } else {
                                        ui.planets.get(ui.map.currentPlanet).isActive = true;
                                        holoIsActive = true;
                                }
                                timer = ui.millis();
                        }
                }
        }

        private void nextPlanet() {

        }

        private void prevPlanet() {

        }

        private void scanButton() {

        }
}