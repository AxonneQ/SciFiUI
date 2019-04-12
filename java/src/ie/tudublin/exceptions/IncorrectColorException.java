package ie.tudublin.exceptions;

public class IncorrectColorException extends Exception {
        private static final long serialVersionUID = 1L;

        public IncorrectColorException() {
                super("Incorrect color value");
        }
}