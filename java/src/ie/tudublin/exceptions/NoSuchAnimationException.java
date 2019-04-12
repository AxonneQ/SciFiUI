package ie.tudublin.exceptions;

public class NoSuchAnimationException extends Exception{
        public NoSuchAnimationException(){
                super("Unknown Animation.");
        }

        public NoSuchAnimationException(String which){
                super("Unknown Animation: \"" + which +"\"");
        }
}
