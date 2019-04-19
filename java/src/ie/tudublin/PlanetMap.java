package ie.tudublin;

import ie.tudublin.Planet.Info;
import processing.core.PFont;
import processing.core.PVector;

public class PlanetMap extends UIElement {
        private int currentPlanet;
        private Info info;
        private String[] infoPieces = {"Name: ", "Habitable: ", "Population: ", "Oxygen (%): ", "Water (%): ", "Radiation: ", "Moon Count: ", "Biome: "};
        private int textSize;
        private int lineSpacing;

        public PlanetMap(UI ui) {
                super(ui, "PLANETMAP");
                currentPlanet = 0;
                info = ui.planets.get(currentPlanet).info;
                textSize = 24*4;
                //ui.textSize(24);
                lineSpacing = textSize*2;
                ui.elements.add(this);
                
        }

        public void getInfo(int i){
                info = ui.planets.get(i).info;
        }

        public void update() {
                //getInfo(0);
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

                ui.translate(0,0,10);
                ui.fill(0,104,224,180);
                ui.scale(0.25f);
                int gap = 0;
                for(int i = 0; i < infoPieces.length; i++){
                        ui.text(infoPieces[i] +  "\t" + info.infoString.get(i), 20*4, 40*4 + gap);
                        gap+=lineSpacing;
                }



                ui.popMatrix();
        }

}