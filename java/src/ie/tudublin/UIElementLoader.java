package ie.tudublin;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;





public class UIElementLoader {

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

                        while((line = fileReaderBuffered.readLine()) != null) {
                                String[] lineSegments = line.split(delimiter);

                                String shapeDescription = lineSegments[0].trim();
                              
                                System.out.println(shapeDescription);

                                switch(shapeDescription){
                                        case "RADAR":
                                        Radar radar = new Radar(ui, Float.parseFloat(lineSegments[1]), Float.parseFloat(lineSegments[2]), Float.parseFloat(lineSegments[3]), Float.parseFloat(lineSegments[4]));
                                        elements.add(radar);
                                        System.out.println("Adding shape:" + lineSegments[0]);
                                        break;
                                        case "BUTTON":
                                        break;


                                        default:
                                        break;
                                }

                        }

                        fileReaderBuffered.close();
                        fileReader.close();

                        

                } catch (IOException e) {
                        System.out.println("Error! File corrupted or does not exist!");
                        e.printStackTrace();
                }


                return elements;
        }        
}