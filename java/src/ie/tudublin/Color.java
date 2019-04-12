package ie.tudublin;

import ie.tudublin.exceptions.*;

public class Color {
        public int r;
        public int g;
        public int b;
        public int a;
        private String rgbaPattern = "^([0-9]{3}\\s?){4}$"; // RGB Pattern 255 255 255 255 (R G B A)
        private String rgbPattern = "^([0-9]{3}\\s?){3}$";  // RGB Pattern 255 255 255 (R G B)
        private String hexPattern = "^#[A-Fa-f0-9]{6,8}$"; // Hex Pattern #FFFFFFFF (R G B A)
        private String rawColorData;

        public Color(String rawColor) {
                try {
                        rawColorData = rawColor.trim();
                        stringToColor(rawColorData);
                } catch (IncorrectColorException e) {
                        System.out.println("Incorrect color value read from file.");
                }
        }

        private void stringToColor(String rawColor) throws IncorrectColorException {
                if (rawColor.matches(hexPattern)) {
                        r = Integer.parseInt(rawColor.substring(1, 3), 16);
                        g = Integer.parseInt(rawColor.substring(3, 5), 16);
                        b = Integer.parseInt(rawColor.substring(5, 7), 16);
                        if (rawColor.length() > 7) {
                                a = Integer.parseInt(rawColor.substring(7, 9), 16);
                        } else {
                                a = 255;
                        }
                        
                } else if (rawColor.matches(rgbaPattern) || rawColor.matches(rgbPattern)) {
                        r = Integer.parseInt(rawColor.substring(0, 3));
                        g = Integer.parseInt(rawColor.substring(4, 7));
                        b = Integer.parseInt(rawColor.substring(8, 11));
                        if (rawColor.matches(rgbaPattern)) {
                                a = Integer.parseInt(rawColor.substring(12, 15));
                        } else {
                                a = 255;
                        }
                } else if (rawColor.isBlank()) {
                        r = g = b = a = 255;
                } else {
                        throw new IncorrectColorException();
                }
                checkRange();
        }

        private void checkRange() {
                if(r > 255) r = 255;
                if(g > 255) g = 255;
                if(b > 255) b = 255;
                if (a > 255) a = 255;
                
                if(r < 0) r = 0;
                if(g < 0) g = 0;
                if(b < 0) b = 0;
                if(a < 0) a = 0;
        }
}