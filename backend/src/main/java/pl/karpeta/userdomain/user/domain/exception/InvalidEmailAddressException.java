package pl.karpeta.userdomain.user.domain.exception;

public class InvalidEmailAddressException extends RuntimeException {

    public InvalidEmailAddressException(String email) {
        super("Invalid email address: " + email);
    }
}
