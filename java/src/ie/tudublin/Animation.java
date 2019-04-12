package ie.tudublin;

import java.util.Map;

public class Animation{
	private static UI ui;

	public Animation(UI ui){
		this.ui = ui;
	}
	public static Map<String, Integer> animations = Map.of(
		"ROTATE", 1
	);

	public static <Any> Any getAnimation(String aniName, float ... vars){
		int aniNumber = animations.get(aniName);
		

		switch(aniNumber){
			case 1:
			rotate(vars);
			

                        default:
                        Integer err = -1;
                        return  (Any) err;
		}
	}

	static private void rotate(float[] angle){
                
		ui.rotateY(angle[0]+=angle[1]);
	}

}