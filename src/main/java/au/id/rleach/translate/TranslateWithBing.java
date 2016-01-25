package au.id.rleach.translate;

import au.id.rleach.translate.data.ImmutableLanguageData;
import au.id.rleach.translate.data.LanguageData;
import au.id.rleach.translate.data.TranslateKeys;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.permission.PermissionDescription;
import org.spongepowered.api.service.permission.PermissionDescription.Builder;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.*;


@Plugin(id="TranslateWithBing", name="TranslateWithBing", version="1.1.0")
public class TranslateWithBing {

    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configMan;

    @Inject
    private Logger logger;
    private LocaleToLanguage l2l;

    private final URL jarConfigFile = this.getClass().getResource("default.conf");
    private ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setURL(jarConfigFile).build();

    @Listener
    public void onPreInit(GamePreInitializationEvent event) {
        Sponge.getDataManager().register(LanguageData.class, ImmutableLanguageData.class, LanguageData.BUILDER);
    }

    Text commandKey = Text.of(Sponge.getRegistry().getTranslationById("options.language").get());
    Text languageWarning = Text.of(Sponge.getRegistry().getTranslationById("options.languageWarning").get());


    public final String languageOverridePermission = "translate.command.languageoverride";
    public final String configReloadPermission = "translate.command.reload";
    Map<String, Language> languageChoices = new HashMap<>(20);
    public void initMap(){
        {
            for(Language l:Language.values()){
                if(l.equals(Language.AUTO_DETECT))
                    continue;
                try {
                    languageChoices.put(l.getName(l).replace(' ', '_'),l);
                } catch (Exception e){
                    languageChoices.put(l.toString().replace(' ', '_'),l);
                }
            }
        }
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event){
        if(Sponge.getServer().getOnlinePlayers().stream().anyMatch(p->!p.getLocale().equals(event.getTargetEntity().getLocale()))) {
            Sponge.getServer().getBroadcastChannel().send(languageWarning);
        }
    }

    @Listener
    public void onCommandInitTime(GameInitializationEvent event) {
        initMap();
        CommandSpec overrideLanguageSpec = CommandSpec.builder()
                .arguments(GenericArguments.playerOrSource(Text.of("player")), GenericArguments.optional(GenericArguments.choices(commandKey, languageChoices)))
                .permission(languageOverridePermission)
                .executor((src, context) -> {
                    Optional<Player> p = context.getOne("player");
                    Optional<Language> opt = context.getOne("options.language");
                    if (!p.isPresent()) return CommandResult.empty();
                    if (opt.isPresent()) {
                        DataTransactionResult result = p.get().offer(new LanguageData(opt.get().toString()));
                        if (result.isSuccessful()) {
                            try {
                                src.sendMessage(Text.of("Players language set to :", opt.get().getName(opt.get())));
                            } catch (Exception e) {
                            }
                            return CommandResult.success();
                        } else {
                            src.sendMessage(Text.of("Invalid Language"));
                            return CommandResult.empty();
                        }
                    } else {
                        LanguageData data = p.get().get(LanguageData.class).orElse(new LanguageData());
                        Language lang = Language.fromString(data.language().get());
                        try {
                            src.sendMessage(Text.of(commandKey, " ", lang.getName(lang)));
                        } catch (Exception e) {
                            src.sendMessage(Text.of(commandKey, " ", lang.toString()));
                        }
                        return CommandResult.success();
                    }
                })
                .build();
        CommandSpec reloadConfigSpec = CommandSpec.builder()
                .permission(configReloadPermission)
                .executor((src,args)->{setupPlugin();return CommandResult.success();})
                .build();

        Sponge.getCommandManager().register(this, overrideLanguageSpec, "language");
        Sponge.getCommandManager().register(this, reloadConfigSpec, "reloadTranslate");
        Optional<PermissionService> permissionService = Sponge.getGame().getServiceManager().provide(PermissionService.class);
        permissionService.ifPresent(ps->{
            Optional<Builder> builder = ps.newDescriptionBuilder(this);
            builder.ifPresent(descBuilder -> {
                descBuilder.assign(PermissionDescription.ROLE_USER, true)
                        .id(languageOverridePermission)
                        .description(Text.of("For command /langauge for overriding TranslateWithBing language."))
                        .register();
            });
            Optional<Builder> builder2 = ps.newDescriptionBuilder(this);
            builder2.ifPresent(descBuilder->{
                descBuilder.assign(PermissionDescription.ROLE_ADMIN, true)
                        .id(configReloadPermission)
                        .description(Text.of("Reloads the Translate configuration"))
                        .register();
            });
        });

    }

    @Listener
    public final void serverStarted(final GamePreInitializationEvent event){
        setupPlugin();
    }

    private void setupPlugin(){
        l2l = new LocaleToLanguage();
        CommentedConfigurationNode rootNode = null;
        CommentedConfigurationNode defNode = null;
        try {
            rootNode = configMan.load(ConfigurationOptions.defaults().setShouldCopyDefaults(true));
            defNode = loader.load();
            rootNode = rootNode.mergeValuesFrom(defNode);
            configMan.save(rootNode);
        } catch (final IOException e) {
            logger.error("Unable to read config ",e);
        }

        final CommentedConfigurationNode id = rootNode.getNode("ClientID");
        final CommentedConfigurationNode secret = rootNode.getNode("ClientSecret");
        final String sID = id.getString(defNode.getNode("ClientID").getString());
        final String sSecret = secret.getString(defNode.getNode("ClientSecret").getString());
        if("UNSET".equals(sSecret)) throw new RuntimeException("You need to register a ClientID & Client Secret to use this plugin, see https://msdn.microsoft.com/en-us/library/mt146806.aspx and fill in the config");
        try {
            Translate.setClientId(Preconditions.checkNotNull(sID));
            Translate.setClientSecret(Preconditions.checkNotNull(sSecret));
        } catch (final RuntimeException e){
            throw new RuntimeException("You need to register a ClientID & Client Secret to use this plugin, see https://msdn.microsoft.com/en-us/library/mt146806.aspx and fill in the config");
        }

        //Translate.setContentType("text/html");
        Translate.setContentType("text/plain");
    }

    @Listener(order = Order.LAST)
    public void chatEvent(final MessageChannelEvent.Chat chat, @First final Player player){
        final Iterator<Player> playerI = chat.getChannel().get()
                                 .getMembers().stream()
                                 .filter(messageReceiver -> messageReceiver instanceof Player)
                                 .map(p -> (Player) p)
                                 .iterator();
        final ImmutableListMultimap<Language, Player> multiMap = Multimaps.index(playerI, this::languageFromPlayer);
        final Optional<Text> optMessage = chat.getMessage();
        optMessage.ifPresent(
                message -> sendTranslatedMessages(player, multiMap, message)
        );
    }

    private Language languageFromPlayer(final Player p){
        final String dataLang = p.get(TranslateKeys.Language).orElse("");
        if(dataLang.isEmpty()){
            return l2l.map.getOrDefault(p.getLocale(), Language.AUTO_DETECT);
        } else {
            return Language.fromString(dataLang);
        }
    }

    private void sendTranslatedMessages(final Player from, final ImmutableListMultimap<Language, Player> multiMap, final Text message){
        final Language fromLang = languageFromPlayer(from);
        final Task submit = Sponge.getScheduler().createTaskBuilder()
                .async()
                .name("Chat Translate Task")
                .execute(() -> {
                    multiMap.keys().stream()
                        .distinct()
                        .filter(to-> Language.AUTO_DETECT != to)
                        .filter(to-> to != fromLang).forEach(
                        to -> {
                            String html = "";
                            String out2 = "";
                            try {
                                html = TextSerializers.LEGACY_FORMATTING_CODE.serialize(message);
                                String out = Translate.execute(html, fromLang, to);
                                out2 = out;
                                multiMap.get(to).stream().forEach(p->p.sendMessage(ChatTypes.CHAT, Text.of(TextColors.GRAY,"⚑", TextSerializers.LEGACY_FORMATTING_CODE.deserialize(out))));

                            } catch (Exception e) {
                                this.logger.error("threw an exception while parsing response", e);
                                this.logger.error("\n\n{}->{}\nbefore:\n{}\n\nafter: \n{}", from, to, html, out2);
                            }
                        }
                    );

                })
                .submit(this);
    }
}
