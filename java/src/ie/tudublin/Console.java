package ie.tudublin;

import processing.core.PGraphics;
import processing.core.PVector;

public class Console {
        private UI ui;
        private PVector position;
        public boolean holoIsActive;
        private float width;
        private float height;
        PGraphics controls;
        
        public Console(UI ui){
                this.ui = ui;
                holoIsActive = false;
                width = 700;
                height = 700;
                controls = ui.createGraphics(ui.width, ui.height);
                createConsole();
        }

        private void createConsole(){
                ui.pushMatrix();
                
                controls.beginDraw();
                
                controls.fill(255,255,255,255);
                controls.stroke(255);
                controls.rect(0,0,100,100);
                controls.endDraw();

                ui.popMatrix();
                
        }


        public void update(){
                position = ui.cam.getPosition();
                //position.sub(new PVector(ui.width/2, ui.height/2));
                ui.println(position);

                ui.pushMatrix();
                ui.translate(500, 500);
                ui.image(controls,0, 0);
                ui.popMatrix();
               
        }

        public void render(){
                
        
     
                
        }
}