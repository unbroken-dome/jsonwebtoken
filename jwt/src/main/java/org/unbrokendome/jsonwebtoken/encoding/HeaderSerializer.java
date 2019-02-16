package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;
import org.unbrokendome.jsonwebtoken.JoseHeader;

import javax.annotation.Nonnull;


public interface HeaderSerializer {

    @Nonnull
    BinaryData serialize(JoseHeader header);
}
