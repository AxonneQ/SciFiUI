package ie.tudublin;

import java.lang.Exception;

public class Color {
        public int r;
        public int g;
        public int b;
        public int a;
        private String rgbaPattern = "[0-9]{0,3}\\s{0,}[0-9]{0,3}\\s{0,}[0-9]{0,3}\\s[0-9]{0,3}";       //RGB Pattern 255 255 255 255 (R G B A)
        private String rgbPattern = "[0-9]{0,3}\\s{0,}[0-9]{0,3}\\s{0,}[0-9]{0,3}";                                //RGB Pattern 255 255 255 (R G B)

        public Color(String rawColor){
                try {
                        stringToColor(rawColor);
                } catch (IncorrectColorException e) {
                        System.out.println("Incorrect color value read from file.");
                }
        }

        private void stringToColor(String rawColor) throws IncorrectColorException{

                if(rawColor.startsWith("#")) {

                        r = Integer.parseInt(rawColor.substring(1,3), 16);
                        g = Integer.parseInt(rawColor.substring(3,5), 16);
                        b = Integer.parseInt(rawColor.substring(5,7), 16);
                        if(rawColor.length() > 7) {
                                a = Integer.parseInt(rawColor.substring(7,9), 16);
                        } else {
                                a = 255;
                        }


                } else if(rawColor.isBlank()) {
                        r = g = b = a = 255;
                } /*else if (){
                        //Implement RGB format
                }*/
                else {
                        throw new IncorrectColorException();
                }
                        


        }

        public class IncorrectColorException extends Exception {
                IncorrectColorException(String error) {
                       super(error);
                }

                IncorrectColorException() {
                        
                }
        }
}