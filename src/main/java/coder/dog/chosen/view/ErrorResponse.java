package coder.dog.chosen.view;

/**
 * Created by megrez on 16/2/28.
 */
public class ErrorResponse extends Response {
    public ErrorResponse(int code,String message) {
        this.code = code;
        this.message = message;
    }
}
