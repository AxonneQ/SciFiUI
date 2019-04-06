package ie.tudublin;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

                String file = "java/data/Shapes.csv";
                String delimiter = ",";
                String line;

                try {
                        FileReader fileReader = new FileReader(file);
                        BufferedReader fileReaderBuffered = new BufferedReader(fileReader);

                        while ((line = fileReaderBuffered.readLine()) != null) {
                                line = checkBOM(line);

                                // If line is not a comment...
                                if (!line.startsWith("#") && !line.startsWith("\"#")) {
                                        // Split line into multiple entries
                                        String[] lineSegments = line.split(delimiter);

                                        // Cut out all whitespaces
                                        String shapeDescription = lineSegments[0].trim();

                                        switch (shapeDescription) {
                                        case "RADAR":
                                                UIElement radar = new Radar(ui, Float.parseFloat(lineSegments[1]),
                                                                Float.parseFloat(lineSegments[2]),
                                                                Float.parseFloat(lineSegments[3]),
                                                                Float.parseFloat(lineSegments[4]));
                                                elements.add(radar);
                                                System.out.println("Adding shape: " + lineSegments[0]);
                                                break;

                                        case "BUTTON":
                                                break;

                                        case "CUSTOM2D":
                                                break;

                                        case "CUSTOM3D":
                                                UIElement custom3d = new CustomShape(ui, lineSegments);
                                                elements.add(custom3d);
                                                System.out.println("Adding shape: " + lineSegments[0]);
                                                break;

                                        default:
                                                break;
                                        }
                                }
                        }

                        // Close Readers
                        fileReaderBuffered.close();
                        fileReader.close();

                } catch (IOException e) {
                        System.out.println("Error! File corrupted or does not exist!");
                        e.printStackTrace();
                }

                return elements;
        }
}