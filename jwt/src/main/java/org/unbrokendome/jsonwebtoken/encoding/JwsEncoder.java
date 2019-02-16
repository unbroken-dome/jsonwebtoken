package org.unbrokendome.jsonwebtoken.encoding;

import org.unbrokendome.jsonwebtoken.BinaryData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public interface JwsEncoder {

    @Nonnull
    String encode(BinaryData header, BinaryData payload, @Nullable BinaryData signature);
}
