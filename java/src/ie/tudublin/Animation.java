package ie.tudublin;

//local
import ie.tudublin.exceptions.*;

//java
import java.util.Map;

public class Animation {
        private static UI ui;
        public Animation(UI ui) {
                Animation.ui = ui;
        }

        public static Map<String, Integer> animations = Map.of("ROTATE", 1, "PULSE", 2);

        public static void getAnimation(String aniName, float... vars) throws NoSuchAnimationException {
                int aniNumber = animations.get(aniName);

                switch (aniNumber) {
                case 1:
                        rotate(vars);
                        break;

                case 2:
                        pulse(vars);
                        break;

                default:
                        throw new NoSuchAnimationException(aniName);

                }
        }

        static private void rotate(float[] angle) {

                if (angle.length > 2) {
                        /*ui.translate(-150,-50,0);
                        ui.rotateY(angle[0] += angle[2]);
                        ui.translate(150,50,0);*/
 
                } else {
                        ui.rotateY(angle[0] += angle[1]);
                }

        }

        static private void pulse(float[] vars) {
                // float speed = vars[0];
                // if()

        }

}
