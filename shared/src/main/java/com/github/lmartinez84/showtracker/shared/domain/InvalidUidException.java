package com.github.lmartinez84.showtracker.shared.domain;

public class InvalidUidException extends RuntimeException {
    public InvalidUidException(String uid) {
        super("Invalid User id format. Uid=" + uid);
    }
}
