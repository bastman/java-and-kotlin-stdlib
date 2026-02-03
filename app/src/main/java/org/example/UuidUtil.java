package org.example;
import java.util.UUID;
public class UuidUtil {

    public static UUID generateV7() {
        var uuid=kotlin.uuid.Uuid.Companion.generateV7();
        return kotlin.uuid.UuidKt.toJavaUuid(uuid);
    }
}
