package database.lib;

/**
 * Created by kayler on 12/2/15.
 */
public class QueryFailedException extends Exception {
    public QueryFailedException(String message) {
        super(message);
    }
}
