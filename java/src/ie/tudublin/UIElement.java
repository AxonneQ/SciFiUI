package ie.tudublin;

import processing.core.PApplet;

abstract public class UIElement {
        UI ui;

        public UIElement(UI ui) {
                this.ui = ui;
                ui.elements.add(this);
        }

        abstract public void update();

        abstract public void render();
}