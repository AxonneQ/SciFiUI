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

        public static Map<String, Integer> animations = Map.of(
                "ROTATE", 1, 
                "PULSE", 2
        );

        public static void getAnimation(String aniName, float... vars) throws NoSuchAnimationException {
                int aniNumber = animations.get(aniName);

                switch (aniNumber) {
                case 1:
                        rotate(vars);
                        break;

                case 2: //Never implemented, was supposed to fade in / out color alpha
                        pulse(vars);
                        break;

                default:
                        throw new NoSuchAnimationException(aniName);

                }
        }

        static private void rotate(float[] angle) {
                ui.rotateY(angle[0] += angle[1]);
        }

        static private void pulse(float[] vars) {
           
        }

}
