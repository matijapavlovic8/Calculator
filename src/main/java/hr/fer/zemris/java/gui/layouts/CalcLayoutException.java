package hr.fer.zemris.java.gui.layouts;

/**
 * Exception thrown when error regarding {@link CalcLayout} occurs.
 * @author MatijaPav
 */
public class CalcLayoutException extends RuntimeException{
    public CalcLayoutException(){
        super();
    }

    public CalcLayoutException(String message){
        super(message);
    }
}
