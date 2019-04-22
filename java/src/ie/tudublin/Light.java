package ie.tudublin;

//processing
import processing.core.PVector;

public class Light extends UIElement {
        private String[] rawData;
        private PVector position;
        private float intensity;
        private Color color;

        public Light(UI ui, String[] rawData) {
                super(ui, "LIGHT");
                this.rawData = rawData;
                createLight();
        }

        private void createLight() {
                String delimiter = ":";
                String[] rawPos = rawData[2].split(delimiter);
                position = new PVector(Float.parseFloat(rawPos[0]), 
                                                               Float.parseFloat(rawPos[1]),
                                                               Float.parseFloat(rawPos[2]));
                color = new Color(rawData[1]);
                intensity = Float.parseFloat(rawData[3]);
        }

        public void update() {

        }

        public void render() {
                ui.pushMatrix();
                ui.translate(ui.width/2, ui.height/2);
                ui.lightFalloff(intensity, 0.0f ,0.0f);
                ui.pointLight(color.r, color.g, color.b, position.x, position.y, position.z);
                //ui.directionalLight(color.r, color.g, color.b, 0, 0, -1);
                //ui.spotLight(color.r, color.g, color.b, position.x, position.y, position.z, 0, 0, -1, 40, 1000);

                ui.popMatrix();
        }
}