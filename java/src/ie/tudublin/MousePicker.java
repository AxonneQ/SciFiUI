package ie.tudublin;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

//processing
import processing.core.PVector;

public class MousePicker {
        UI ui;

        private Vector3f currentRay;
        private Matrix4f projectionMatrix;
        private Matrix4f viewMatrix;
        private Camera camera;

        private static final float FOV = 90; // field of view angle
        private static final float NEAR_PLANE = 0.1f;
        private static final float FAR_PLANE = 1500;

        public MousePicker(UI ui, Camera cam, Matrix4f projection) {
                this.ui = ui;

                this.camera = cam;
                this.projectionMatrix = projection;
                this.viewMatrix = createViewMatrix(cam);
        }

        public void update() {
                viewMatrix = createViewMatrix(camera);
                currentRay = calculateMouseRay();

                // [ 0.70710677, 0.70710677, 0.0 ] - normalize();
                // System.out.println();
        }

        public void drawRay() {
                Vector3f s = getPointOnRay(currentRay, 0);
                Vector3f p = getPointOnRay(currentRay, 5000);
              /*  ui.stroke(255);
                ui.pushMatrix();
                ui.translate(s.x,s.y,s.z);
                ui.sphere(20);
                ui.popMatrix();
                ui.line(s.x,s.y,2000,p.x,p.y,p.z);
                ui.stroke(255);
                ui.pushMatrix();
                ui.translate(p.x, p.y, p.z);
                ui.sphere(20);
                ui.popMatrix();
                */
        }

        public Vector3f getCurrentRay() {
                return currentRay;
        }

        private Vector3f calculateMouseRay() {
                float mouseX = ui.mouseX;
                float mouseY = ui.mouseY;
                Vector2f normalizedCoords = getNormalisedCoords(mouseX, mouseY);
                Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
                Vector4f eyeCoords = toEyeCoords(clipCoords);
                Vector3f worldRay = toWorldCoords(eyeCoords);
                return worldRay;
        }

        private Vector2f getNormalisedCoords(float mouseX, float mouseY) {
                float x = (2f * mouseX) / ui.width - 1;
                float y = (2f * mouseY) / ui.height - 1f;
                return new Vector2f(x, y);
        }

        private Vector4f toEyeCoords(Vector4f clipCoords) {
                Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
                Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
                return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
        }

        private Vector3f toWorldCoords(Vector4f eyeCoords) {
                Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
                Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
                Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
                //mouseRay.normalise();
                return mouseRay;
        }

        private Vector3f getPointOnRay(Vector3f ray, float distance) {
                PVector camPos = camera.getPosition();
                Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
                Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
                return Vector3f.add(start, scaledRay, null);
        }

        public static Matrix4f createViewMatrix(Camera camera) {
                Matrix4f viewMatrix = new Matrix4f();
                viewMatrix.setIdentity();
                PVector cameraPos = camera.getPosition();
                Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
                Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
                return viewMatrix;
        }

        public static Matrix4f createProjectionMatrix() {
                Matrix4f projectionMatrix = new Matrix4f();

                float aspectRatio = 1000 / 1000;
                float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
                float x_scale = y_scale / aspectRatio;
                float frustum_length = FAR_PLANE - NEAR_PLANE;

                System.out.println(y_scale + " " + y_scale);

                projectionMatrix = new Matrix4f();
                projectionMatrix.m00 = x_scale;
                projectionMatrix.m11 = y_scale;
                projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
                projectionMatrix.m23 = -1;
                projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
                projectionMatrix.m33 = 0;

                return projectionMatrix;
        }
}