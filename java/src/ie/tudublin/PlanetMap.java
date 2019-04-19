package ie.tudublin;

import ie.tudublin.Planet.Info;
import processing.core.PVector;

public class PlanetMap extends UIElement {
        private PVector position;
        private int currentPlanet;
        private Info info;

        public PlanetMap(UI ui) {
                super(ui, "PLANETMAP");
                ui.elements.add(this);
        }

        public void getInfo(int i){
                info = ui.planets.get(i).info;
        }

        public void update() {
                getInfo(0);
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
                ui.translate(-650 + ui.width/2, -200 + ui.height/2, 550);
                ui.rotateY((float)Math.toRadians(55));


                //load from file:
                        ui.fill(0,0,0,120);
                        ui.noStroke();
                        ui.rect(0,0,500,510);
                ui.popMatrix();
        }

        private void drawInfo() {
                // Render Info screen
                ui.pushMatrix();
                ui.translate(375 + ui.width/2, -200 + ui.height/2, 150);
                ui.rotateY((float)Math.toRadians(-55));
                ui.fill(0,0,0,120);
                ui.noStroke();
                ui.rect(0,0,500,510);

                ui.translate(0,0,2);
                ui.fill(0,104,224,180);
               // ui.fill(255,255,255,255);
               ui.textSize(24);

                ui.text("Name: " + info.name, 10, 5 + 24);


                ui.popMatrix();
        }

}