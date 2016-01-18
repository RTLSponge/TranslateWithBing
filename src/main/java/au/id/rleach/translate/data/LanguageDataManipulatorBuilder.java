package au.id.rleach.translate.data;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.Optional;

public class LanguageDataManipulatorBuilder implements DataManipulatorBuilder<LanguageData, ImmutableLanguageData> {

    @Override
    public LanguageData create() {
        return new LanguageData();
    }

    @Override
    public Optional<LanguageData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(LanguageData.class).orElse(new LanguageData()));
    }

    @Override
    public Optional<LanguageData> build(DataView container) throws InvalidDataException {
        // Note that this should check the Queries.CONTENT_VERSION, but for the sake of demonstration
        // it's not necessary
        if (container.contains(TranslateKeys.Language.getQuery())) {
            final String language = container.getString(TranslateKeys.Language.getQuery()).get();
            return Optional.of(new LanguageData(language));
        }
        return Optional.empty();
    }
}