package com.crave.crave_backend.util;

import java.util.Base64;

public final class CursorUtil {

    public static String encode(Long id) {
        return Base64.getEncoder().encodeToString(id.toString().getBytes());
    }

    public static Long decode(String cursor) {
        return Long.parseLong(new String(Base64.getDecoder().decode(cursor))
        );
    }
}
