package ie.tudublin;

//local
import ie.tudublin.shapes.*;

//java
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.io.BufferedReader;

public class UIElementLoader {

        static private String checkBOM(String s) throws UnsupportedEncodingException {
                // Convert string to byte array
                byte[] lineBytes = s.getBytes();

                // Check if byte array contains Byte Order Mark
                if ((lineBytes[0] & 0xFF) == 0xEF && (lineBytes[1] & 0xFF) == 0xBB && (lineBytes[2] & 0xFF) == 0xBF) {
                        // if yes, create new byte array, copy all bytes excluding first 3 and convert
                        // back to String
                        byte[] lineBytesNoBOM = new byte[lineBytes.length - 3];
                        System.arraycopy(lineBytes, 3, lineBytesNoBOM, 0, lineBytesNoBOM.length);
                        String temp = new String(lineBytesNoBOM, "ISO-8859-1"); // 8-bit ASCII
                        return temp;
                } else {
                        return s;
                }
        }

        static public ArrayList<UIElement> loadUI(UI ui) {
                ArrayList<UIElement> elements = new ArrayList<UIElement>();

                // Not using processing loadTable() because it doesn't work outside
                // preload/setup functions of the main sketch class (UI.java).
                // And I wanted the Element loader to be separated from the main sketch
                // (UI.java) class.

                String[] file = { "java/data/Shapes.csv", "java/data/Stars.csv" };
                String delimiter = ",";
                String line;

                try {
                        for (int i = 0; i < Array.getLength(file); i++) {
                                FileReader fileReader = new FileReader(file[i]);
                                BufferedReader fileReaderBuffered = new BufferedReader(fileReader);

                                while ((line = fileReaderBuffered.readLine()) != null) {
                                        line = checkBOM(line);

                                        // If line is not a comment or is not empty...
                                        if (!line.startsWith("#") && !line.startsWith("\"#") && !line.startsWith(",")) {
                                                // Split line into multiple entries
                                                String[] lineSegments = line.split(delimiter);

                                                // Cut out all whitespaces
                                                String shapeDescription = lineSegments[0].trim();

                                                switch (shapeDescription) {
                                                        
                                                case "SPHERE":
                                                        UIElement sphere = new Sphere(ui, lineSegments);
                                                        elements.add(sphere);
                                                        System.out.println("Adding shape: " + lineSegments[0]);
                                                        break;

                                                case "CONE":
                                                        UIElement cone = new Cone(ui, lineSegments);
                                                        elements.add(cone);
                                                        System.out.println("Adding shape: " + lineSegments[0]);
                                                        break;

                                                case "ORBIT":
                                                        UIElement orbit = new Orbit(ui, lineSegments);
                                                        elements.add(orbit);
                                                        System.out.println("Adding shape: " + lineSegments[0]);
                                                        break;

                                                case "CUSTOM2D":
                                                        UIElement custom2d = new CustomShape(ui, lineSegments);
                                                        elements.add(custom2d);
                                                        System.out.println("Adding shape: " + lineSegments[0]);
                                                        break;

                                                case "CUSTOM3D":
                                                        UIElement custom3d = new CustomShape(ui, lineSegments);
                                                        elements.add(custom3d);
                                                        System.out.println("Adding shape: " + lineSegments[0]);
                                                        break;

                                                case "PLANET":
                                                        UIElement planet = new Planet(ui, lineSegments);
                                                        elements.add(planet);
                                                        System.out.println("Adding planet: " + lineSegments[1]);
                                                        break;
                                                
                                                case "LIGHT":
                                                        UIElement light = new Light(ui, lineSegments);
                                                        elements.add(light);
                                                        System.out.println("Adding Light at pos (X:Y:Z): " + lineSegments[2]);
                                                        break;
                                                        
                                                case "":
                                                        break;

                                                default:
                                                        break;
                                                }
                                        }
                                }
                                // Close Readers
                                fileReaderBuffered.close();
                                fileReader.close();
                        }

                } catch (IOException e) {
                        System.out.println("Error! File corrupted or does not exist!");
                        e.printStackTrace();
                }

                return elements;
        }
}