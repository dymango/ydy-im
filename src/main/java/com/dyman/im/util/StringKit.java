package com.dyman.im.util;

import java.util.Optional;

public interface StringKit {
    static Optional<String> trim(String s) {
        return Optional.ofNullable(s).map(String::trim).filter(x -> !x.isEmpty());
    }
}
