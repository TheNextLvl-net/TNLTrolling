package net.nonswag.tnl.trolling.api.errors;

import javax.annotation.Nonnull;

public enum OpenGL {
    STATUS_36061("[Shaders] Error creating framebuffer: dfb, status 36061"),
    INVALID_FRAMEBUFFER_OPERATION_18("§eOpenGL Error§r: 18 (GL_INVALID_FRAMEBUFFER_OPERATION in glClear(incomplete framebuffer))"),
    INVALID_FRAMEBUFFER_OPERATION_1286("§eOpenGL Error§r: 1286 (Invalid framebuffer operation)"),
    INVALID_VALUE_3("§eOpenGL Error§r: 3 (GL_INVALID_VALUE in glCopyBufferSubData(readOffset 55541632 + size 98176 > src_buffer_size 0))"),
    INVALID_VALUE_18("§eOpenGL Error§r: 18 (GL_INVALID_VALUE in glCopyBufferSubData(readOffset 1761408 + size 1280 > src_buffer_size 0))"),
    ;

    @Nonnull
    private final String message;

    OpenGL(@Nonnull String message) {
        this.message = message;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }
}
