package ie.tudublin;


abstract public class UIElement {
        public UI ui;
        public String type;

        public UIElement(UI ui, String type) {
                this.ui = ui;
                this.type = type;
                ui.elements.add(this);
        }

        abstract public void update();

        abstract public void render();
}