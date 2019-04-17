package ie.tudublin.shapes;

//local
import ie.tudublin.*;

//processing
import processing.core.PApplet;

public class Button extends UIElement {
        private float x;
        private float y;
        private float width;
        private float height;
        private String text;

        public Button(UI ui, float x, float y, float width, float height, String text) {
                super(ui, "BUTTON");
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                this.text = text;
        }

        public void update() {

        }

        public void render() {
                ui.noFill();
                ui.stroke(255);
                ui.rect(x, y, width, height);
                ui.textAlign(PApplet.CENTER, PApplet.CENTER);
                ui.text(text, x + width * 0.5f, y + height * 0.5f);
        }
}