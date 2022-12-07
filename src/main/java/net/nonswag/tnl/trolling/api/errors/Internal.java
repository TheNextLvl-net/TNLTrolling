package net.nonswag.tnl.trolling.api.errors;

import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public enum Internal {
    READ_TIMEOUT_EXCEPTION("Internal Exception: io.netty.handler.timeout.ReadTimeoutException"),
    DECODER_EXCEPTION("Internal Exception: io.netty.handler.codec.DecoderException"),
    OUT_OF_MEMORY("Out of memory!"),
    ILLEGAL_STANCE("Illegal stance"),
    DISCONNECTED("Disconnected"),
    END_OF_STREAM("End Of Stream"),
    READ_TIMED_OUT("Read timed out"),
    TIMED_OUT("Timed out"),
    NULL("null"),
    EMPTY("\n".repeat(100)),
    FLYING_IS_NOT_ENABLED("Flying is not enabled on this server"),
    CONNECTION_LOST("Connection lost"),
    NO_FURTHER_INFORMATION("java.net.ConnectException: Connection timed out: no further information:"),
    ;

    @Nonnull
    private final String message;

    Internal(@Nonnull String message) {
        this.message = message;
    }
}
