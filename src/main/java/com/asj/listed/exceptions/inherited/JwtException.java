package com.asj.listed.exceptions.inherited;

import com.asj.listed.security.exceptions.inherited.UnauthorizedException;

public class JwtException extends UnauthorizedException {
    public JwtException(String message) { super(message); }
}
