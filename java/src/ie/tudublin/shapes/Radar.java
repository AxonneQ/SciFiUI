package ie.tudublin.shapes;

//local
import ie.tudublin.*;

//processing
import processing.core.PApplet;
import processing.core.PVector;

public class Radar {
        private UI ui;
        private float radius;
        private PVector pos;
        private float frequency;
        private float theta = 0;
        private float timeDelta = 1.0f / 60.0f;
        private PVector[] lines;

        public Radar(UI ui, float frequency, float x, float y, float radius) {
                this.ui = ui;
                this.frequency = frequency;
                pos = new PVector(x, y);
                this.radius = radius;
                this.lines = new PVector[60];
        }

        public void render() {
                

                for(int i = 0; i < lines.length; i++){
                        theta += (PApplet.TWO_PI * timeDelta * frequency)/60;
                        lines[i] = new PVector(pos.x + (float) Math.sin(theta) * radius, 
                                                        pos.y - (float) Math.cos(theta) * radius);
                }

                ui.noFill();
                ui.ellipse(pos.x, pos.y, radius * 2, radius * 2);
                float alpha = 0;
                for(PVector p : lines){
                        ui.line(pos.x, pos.y, p.x, p.y);
                        ui.stroke(255,0,0,alpha);
                        alpha+=4;
                }       
                
        }
}