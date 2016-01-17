package au.id.rleach.translate.data;

import com.google.common.base.Objects;
import com.memetix.mst.language.Language;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class LanguageData extends AbstractData<LanguageData, ImmutableLanguageData> {

    private String language;
    public static LanguageDataManipulatorBuilder BUILDER = new LanguageDataManipulatorBuilder();

    public LanguageData() {
        this(Language.AUTO_DETECT.toString());
    }

    public LanguageData(String language) {
        this.language = language;
    }

    public Value<String> language() {
        return Sponge.getRegistry().getValueFactory().createValue(TranslateKeys.Language, language, "");
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(TranslateKeys.Language, () -> this.language);
        registerFieldSetter(TranslateKeys.Language, value -> this.language = checkNotNull(value));
        registerKeyValue(TranslateKeys.Language, this::language);

    }

    @Override
    public Optional<LanguageData> fill(DataHolder dataHolder, MergeFunction overlap) {
        final Optional<LanguageData> from = BUILDER.createFrom(dataHolder);
        final LanguageData data = from.orElse(null);
        final LanguageData newData = checkNotNull(overlap.merge(this, data));
        return Optional.of(this.set(TranslateKeys.Language, newData.language));
    }

    @Override
    public Optional<LanguageData> from(DataContainer container) {
        if (!container.contains(TranslateKeys.Language.getQuery())) {
            return Optional.empty();
        }
        final String string = container.getString(TranslateKeys.Language.getQuery()).get();
        this.language = string;

        return Optional.of(this);
    }

    @Override
    public LanguageData copy() {
        return new LanguageData(this.language);
    }

    @Override
    public ImmutableLanguageData asImmutable() {
        return new ImmutableLanguageData(this.language);
    }

    @Override
    public int compareTo(LanguageData o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(TranslateKeys.Language, this.language);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("language", this.language)
                .toString();
    }
}
