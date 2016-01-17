package au.id.rleach.translate;

import com.google.common.collect.ImmutableMap;
import com.memetix.mst.language.Language;
import org.spongepowered.api.text.translation.locale.Locales;

import java.util.Locale;

public class LocaleToLanguage {

    public final ImmutableMap<Locale, Language> map;

    public LocaleToLanguage(){
        ImmutableMap.Builder<Locale, Language> mapping = new ImmutableMap.Builder<>();
        mapping.put(Locales.AR_SA,Language.ARABIC);
        mapping.put(Locales.BG_BG, Language.BULGARIAN);
        mapping.put(Locales.CA_ES, Language.CATALAN);
        mapping.put(Locales.ZH_CN, Language.CHINESE_SIMPLIFIED);
        mapping.put(Locales.ZH_TW, Language.CHINESE_TRADITIONAL);
        mapping.put(Locales.HR_HR, Language.AUTO_DETECT);// hr Croatian
        mapping.put(Locales.CS_CZ, Language.CZECH);
        mapping.put(Locales.DA_DK, Language.DANISH);
        mapping.put(Locales.NL_NL, Language.DUTCH);
        mapping.put(Locales.EN_AU, Language.ENGLISH);
        mapping.put(Locales.EN_US, Language.ENGLISH);
        mapping.put(Locales.EN_CA, Language.ENGLISH);
        mapping.put(Locales.EN_GB, Language.ENGLISH);
        mapping.put(Locales.EN_PT, Language.ENGLISH);
        mapping.put(Locales.ET_EE, Language.ESTONIAN);
        mapping.put(Locales.FI_FI, Language.FINNISH);
        mapping.put(Locales.FR_CA, Language.FRENCH);
        mapping.put(Locales.FR_FR, Language.FRENCH);
        mapping.put(Locales.DE_DE, Language.GERMAN);
        mapping.put(Locales.EL_GR, Language.GREEK);
        mapping.put(Locales.HE_IL, Language.HEBREW);
        mapping.put(Locales.HI_IN, Language.HINDI);
        mapping.put(Locales.HU_HU, Language.HUNGARIAN);
        mapping.put(Locales.ID_ID, Language.INDONESIAN);
        mapping.put(Locales.IT_IT, Language.ITALIAN);
        mapping.put(Locales.JA_JP, Language.JAPANESE);
        mapping.put(Locales.TLH_AA, Language.AUTO_DETECT);//tlh
        mapping.put(Locales.KO_KR, Language.KOREAN);
        mapping.put(Locales.LV_LV, Language.LATVIAN);
        mapping.put(Locales.LT_LT, Language.LITHUANIAN);
        mapping.put(Locales.MS_MY, Language.MALAY);
        mapping.put(Locales.MT_MT, Language.AUTO_DETECT);//mt maltese
        mapping.put(Locales.NO_NO, Language.NORWEGIAN);
        mapping.put(Locales.FA_IR, Language.PERSIAN);
        mapping.put(Locales.PL_PL, Language.POLISH);
        mapping.put(Locales.PT_BR, Language.PORTUGUESE);
        mapping.put(Locales.PT_PT, Language.PORTUGUESE);
        mapping.put(Locales.RO_RO, Language.ROMANIAN);
        mapping.put(Locales.RU_RU, Language.RUSSIAN);
        mapping.put(Locales.SR_SP, Language.AUTO_DETECT); //sr-Cyrl serbian cyrillic
        mapping.put(Locales.SK_SK, Language.SLOVAK);
        mapping.put(Locales.SL_SI, Language.SLOVENIAN);
        mapping.put(Locales.ES_ES, Language.SPANISH);
        mapping.put(Locales.ES_AR, Language.SPANISH);
        mapping.put(Locales.ES_MX, Language.SPANISH);
        mapping.put(Locales.ES_UY, Language.SPANISH);
        mapping.put(Locales.ES_VE, Language.SPANISH);
        mapping.put(Locales.SV_SE, Language.SWEDISH);
        mapping.put(Locales.TH_TH, Language.THAI);
        mapping.put(Locales.TR_TR, Language.TURKISH);
        mapping.put(Locales.UK_UA, Language.UKRAINIAN);
        mapping.put(Locales.VI_VN, Language.VIETNAMESE);
        mapping.put(Locales.CY_GB, Language.AUTO_DETECT); //cy Welsh
        mapping.put(Locales.FIL_PH, Language.ENGLISH);
        mapping.put(Locales.GA_IE, Language.ENGLISH);
        mapping.put(Locales.GV_IM, Language.ENGLISH);
        mapping.put(Locales.HY_AM, Language.ENGLISH);
        mapping.put(Locales.IS_IS, Language.ENGLISH);
        mapping.put(Locales.KW_GB, Language.ENGLISH);
        mapping.put(Locales.MI_NZ, Language.ENGLISH);
        mapping.put(Locales.LB_LU, Language.GERMAN);
        mapping.put(Locales.NDS_DE, Language.GERMAN);
        mapping.put(Locales.AF_ZA, Language.DUTCH);
        mapping.put(Locales.AST_ES, Language.SPANISH);
        mapping.put(Locales.EU_ES, Language.SPANISH);
        mapping.put(Locales.GL_ES, Language.SPANISH);
        mapping.put(Locales.VAL_ES, Language.CATALAN);
        mapping.put(Locales.OC_FR, Language.CATALAN);
        mapping.put(Locales.AZ_AZ, Language.TURKISH);
        mapping.put(Locales.KA_GE, Language.RUSSIAN);
        mapping.put(Locales.LA_LA, Language.ITALIAN);
        mapping.put(Locales.NN_NO, Language.NORWEGIAN);
        mapping.put(Locales.SE_NO, Language.NORWEGIAN);
        map = mapping.build();
    }
}
