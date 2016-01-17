package au.id.rleach.translate.data;

import com.google.common.collect.ComparisonChain;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import java.util.Optional;

public class ImmutableLanguageData extends AbstractImmutableData<ImmutableLanguageData, LanguageData> {

    private final String language;

    public ImmutableLanguageData() {
        this("");
    }

    public ImmutableLanguageData(String language) {
        this.language = language;
    }

    public ImmutableValue<String> language() {
        return Sponge.getRegistry().getValueFactory().createValue(TranslateKeys.Language, this.language, "").asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(TranslateKeys.Language, this::getLanguage);
        registerKeyValue(TranslateKeys.Language, this::language);
    }

    @Override
    public <E> Optional<ImmutableLanguageData> with(Key<? extends BaseValue<E>> key, E value) {
        return Optional.empty();
    }

    @Override
    public LanguageData asMutable() {
        return new LanguageData(this.language);
    }

    @Override
    public int compareTo(ImmutableLanguageData o) {
        return ComparisonChain.start()
                .compare(this.language, o.language)
                .result();
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(TranslateKeys.Language, this.language);
    }

    private String getLanguage() {
        return this.language;
    }

}