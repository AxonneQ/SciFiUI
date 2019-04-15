package ie.tudublin;

//processing
import processing.core.PVector;


public class MousePicker{
        UI ui;
        PVector viewportCoords;
        PVector normalisedCoords;
        PVector homogeneousCoords;



        public MousePicker(UI ui) {
                this.ui = ui;
                viewportCoords = new PVector();
                normalisedCoords = new PVector();
        }

        void update(){
                viewportCoords.set(ui.mouseX, ui.mouseY);
                normalisedCoords.x = (2.0f * viewportCoords.x) / ui.width - 1.0f;
                normalisedCoords.y = 1.0f - (2.0f * viewportCoords.y) / ui.height;
                normalisedCoords.z = 1.0f;     //placeholder value
              //  homogeneousCoords =  
                



                //[ 0.70710677, 0.70710677, 0.0 ] - normalize();
                System.out.println(normalisedCoords);
        }
}