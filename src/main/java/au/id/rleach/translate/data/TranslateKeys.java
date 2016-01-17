package au.id.rleach.translate.data;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;

import static org.spongepowered.api.data.DataQuery.of;
import static org.spongepowered.api.data.key.KeyFactory.makeSingleKey;

public class TranslateKeys {
    public static final Key<Value<String>> Language = makeSingleKey(String.class, Value.class, of("Language"));
}
