package ie.tudublin.shapes;

//local
import ie.tudublin.*;

//java
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

//processing
import processing.core.PVector;
import processing.core.PShape;
import static processing.core.PConstants.*;

public class CustomShape extends UIElement {
        private ArrayList<PVector> vertices;
        private ArrayList<PVector> contours; // if passed as argument, create a hole in shape.
        private String[] rawData;
        private PShape s;
        private Color stroke;
        private Color fill;

        //Static unmutable map:
        static final Map<String, Integer> PConstantsMap = Map.of(
                //beginShape params:
                "",20,
                "POINTS",3,
                "LINES",5,
                "TRIANGLES",9,
                "TRIANGLE_FAN",11,
                "TRIANGLE_STRIP",10,
                "QUADS",16,
                "QUAD_STRIP",18
        );
                

        public CustomShape(UI ui, String[] rawData) {
                super(ui, rawData[0]);
                this.rawData = rawData;
                vertices = new ArrayList<PVector>(0);
                contours = new ArrayList<PVector>(0);
                createVertices();
                makeShape();
                ui.custom.add(this);
                
        }

        private void createVertices() {
                // 3D shape
                String delimiter = ":";
                int dataSize = Array.getLength(rawData);

                if (rawData[0].contains("CUSTOM3D")) {
                        int totalVerts = Integer.parseInt(rawData[4]);
                        for (int i = 0; i < totalVerts; i++) {
                                String[] rawPosition = rawData[i + 5].split(delimiter); // offset position of first vertex (e.g. 0 + 4 = 1st vertex column)

                                Float x = Float.parseFloat(rawPosition[0]) + (ui.width / 2);
                                Float y = Float.parseFloat(rawPosition[1]) + (ui.height / 2);
                                Float z = Float.parseFloat(rawPosition[2]);

                                PVector vertex = new PVector(x, y, z);

                                vertices.add(vertex);
                        }
                        if (dataSize > (5 + totalVerts)) {
                                if (rawData[5 + totalVerts].contains("CONTOUR")) {
                                        int totalCVerts = Integer.parseInt(rawData[5 + totalVerts+1]);
                                        int offset = 7+totalVerts;      //starting position for contour vertices. 4 + 2 + totalVerts

                                        for (int i = offset+totalCVerts -1; i >= offset; i--) {
                                                String[] rawPosition = rawData[i].split(delimiter);

                                                Float x = Float.parseFloat(rawPosition[0]) + (ui.width / 2);
                                                Float y = Float.parseFloat(rawPosition[1]) + (ui.height / 2);
                                                Float z = Float.parseFloat(rawPosition[2]);

                                                PVector vertex = new PVector(x, y, z);

                                                contours.add(vertex);
                                        }
                                }
                        }
                }

                // 2D shape
                if (rawData[0].contains("CUSTOM2D")) {
                        int totalVerts = Integer.parseInt(rawData[4]);
                        for (int i = 0; i < totalVerts; i++) {
                                String[] rawPosition = rawData[i + 5].split(delimiter); 

                                Float x = Float.parseFloat(rawPosition[0]) + (ui.width / 2);
                                Float y = Float.parseFloat(rawPosition[1]) + (ui.height / 2);

                                PVector vertex = new PVector(x, y);

                                vertices.add(vertex);
                        }
                        if (dataSize > (5 + totalVerts)) {
                                if (rawData[5 + totalVerts].contains("CONTOUR")) {
                                        int totalCVerts = Integer.parseInt(rawData[5 + totalVerts+1]);
                                        int offset = 7+totalVerts;    

                                        for (int i = offset+totalCVerts -1; i >= offset; i--) {
                                                String[] rawPosition = rawData[i].split(delimiter);

                                                Float x = Float.parseFloat(rawPosition[0]) + (ui.width / 2);
                                                Float y = Float.parseFloat(rawPosition[1]) + (ui.height / 2);

                                                PVector vertex = new PVector(x, y);

                                                contours.add(vertex);
                                        }
                                }
                        }
                }
        }

        private void makeShape() {
                stroke = new Color(rawData[1]);
                fill = new Color(rawData[2]);

                s = new PShape();
                s = ui.createShape();
                s.beginShape(PConstantsMap.get(rawData[3]));
                s.stroke(stroke.r, stroke.g, stroke.b, stroke.a);
                s.fill(fill.r, fill.g, fill.b, fill.a);
                for (PVector v : vertices) {
                        if(rawData[0].equals("CUSTOM3D")){
                                s.vertex(v.x, v.y, v.z);
                        } 
                        if(rawData[0].equals("CUSTOM2D")){
                                s.vertex(v.x,v.y);
                        }
                }
                if (contours.size() > 0) {
                        s.beginContour();
                        for (PVector v : contours) {
                                if(rawData[0].equals("CUSTOM3D")){
                                        s.vertex(v.x, v.y, v.z);
                                }
                                if(rawData[0].equals("CUSTOM2D")){
                                        s.vertex(v.x,v.y);
                                        System.out.println("TEST");
                                }
                        }       
                        s.endContour();
                }
                s.endShape(CLOSE);
                
        }

        public void strokeState(boolean b){
                if(b == false){
                        s.setStroke(false);    
                } else {
                        s.setStroke(true);
                }
        }

        public void update() {

        }

        public void render() {
                s.draw(ui.g);
        }




               
        

}