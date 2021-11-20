package hu.pumate.clipdb;

public class ClipApiException extends RuntimeException{
    public ClipApiException(String message) {
        super(message);
    }

    public ClipApiException() {
        super();
    }
}
