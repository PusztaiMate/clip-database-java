package hu.pumate.clipdb.exception;

public class ClipNotFoundException extends RuntimeException {
    public ClipNotFoundException(Long id) {
        super(String.format("Could not find clip with id: %d", id));
    }
}
