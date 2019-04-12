package ie.tudublin.exceptions;

public class NoSuchAnimationException extends Exception{
        private static final long serialVersionUID = 1L;

        public NoSuchAnimationException() {
                super("Unknown Animation.");
        }

        public NoSuchAnimationException(String which){
                super("Unknown Animation: \"" + which +"\"");
        }
}
