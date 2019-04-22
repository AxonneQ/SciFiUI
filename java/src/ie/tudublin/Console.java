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
        }

        public void update(){
                position = ui.cam.getPosition();
                //position.sub(new PVector(ui.width/2, ui.height/2));
                ui.println(position);


                ui.pushMatrix();
                ui.translate(-position.x, -position.y, position.z);
                controls.beginDraw();
                
                controls.fill(255,255,255,255);
                controls.stroke(255);
                controls.rect(0,0,10000,10000);
                controls.endDraw();
                ui.image(controls,0, 0);
                ui.popMatrix();
        }

        public void render(){
                
                
                
                
        }
}