package ie.tudublin;

public class Camera {
        UI ui;

        public Camera(UI ui) {
                this.ui = ui;
        }

        public void moveEye(float x, float y, float z) {
                ui.camera(ui.width / 2.0f - ((x - ui.width / 2) / 10), ui.height / 2.0f - ((y - ui.height / 2) / 10),
                                (ui.height / 2.0f) / ui.tan((float) (ui.PI * 30.0 / 180.0)) + (z * 25), ui.width / 2.0f,
                                ui.height / 2.0f, 0f, 0f, 1f, 0f);
        }
}