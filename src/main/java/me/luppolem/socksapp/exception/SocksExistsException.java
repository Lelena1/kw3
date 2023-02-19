package me.luppolem.socksapp.exception;

import me.luppolem.socksapp.model.Socks;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SocksExistsException extends RuntimeException {


    public SocksExistsException() {
        super("Мапа носков должна содержать носки");
    }

    public SocksExistsException(String message) {
        super(message);
    }

    public SocksExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocksExistsException(Throwable cause) {
        super(cause);
    }

    protected SocksExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
