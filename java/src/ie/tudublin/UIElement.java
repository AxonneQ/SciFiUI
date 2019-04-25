package ie.tudublin;

//Abstract class
abstract public class UIElement {
        public UI ui;
        public String type;

        public UIElement(UI ui, String type) {
                this.ui = ui;
                this.type = type;
        }

        abstract public void update();

        abstract public void render();
}