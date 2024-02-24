package kutilities.domain.enums.wfh;


public enum LessonEnum {
    BEGINNING_BLENDS("BEGINNING_BLENDS", "Beginning Blends"),
    COMPLEX_VOWEL("COMPLEX_VOWEL", "Complex Vowel /ô/ (au, aw, alk, alt, all, augh)"),
    DIGRAPH_CH_TCH("DIGRAPH_CH_TCH", "Digraph ch/tch"),
    DIGRAPH_NG_BLEND_NK("DIGRAPH_NG_BLEND_NK", "Digraph ng and Blend nk"),
    DIGRAPH_SH("DIGRAPH_SH", "Digraph sh"),
    DIGRAPH_TH("DIGRAPH_TH", "Digraph th"),
    DIGRAPH_WH("DIGRAPH_WH", "Digraph wh"),
    DIPHTHONG_OI_OY("DIPHTHONG_OI_OY", "Diphthong (oi,oy)"),
    DIPHTHONG_OU_OW("DIPHTHONG_OU_OW", "Diphthong (ou,ow)"),
    ENDING_BLENDS("ENDING_BLENDS", "Ending Blends"),
    FINAL_E("FINAL_E", "Final e"),
    FINAL_E_WITH_SOFT_C_G("FINAL_E_WITH_SOFT_C_G", "Final e (with soft c and g)"),
    LONG_A("LONG_A", "Long a (ai, ay)"),
    LONG_E("LONG_E", "Long e (ee, ea)"),
    LONG_I("LONG_I", "Long i (y, igh)"),
    LONG_O("LONG_O", "Long o (oa, ow)"),
    LONG_OO("LONG_OO", "Long oo"),
    LONG_U("LONG_U", "Long u (u, ew, ue)"),
    MORE_LONG_A_LONG_I("MORE_LONG_A_LONG_I", "More Long a, Long i"),
    MORE_LONG_O_LONG_E("MORE_LONG_O_LONG_E", "More Long o, Long e"),
    R_CONTROLLED_ARE_AIR_EAR("R_CONTROLLED_ARE_AIR_EAR", "r-Controlled are, air, ear"),
    R_CONTROLLED_VOWEL_AR("R_CONTROLLED_VOWEL_AR", "r-Controlled Vowel ar"),
    R_CONTROLLED_VOWEL_OR_ORE_OAR("R_CONTROLLED_VOWEL_OR_ORE_OAR", "r-Controlled Vowel or, ore, oar"),
    R_CONTROLLED_VOWEL_UR("R_CONTROLLED_VOWEL_UR", "r-Controlled Vowel ûr (er, ir, ur)"),
    SHORT_A("SHORT_A", "Short a"),
    SHORT_E("SHORT_E", "Short e"),
    SHORT_I("SHORT_I", "Short i"),
    SHORT_O("SHORT_O", "Short o"),
    SHORT_OO("SHORT_OO", "Short oo"),
    SHORT_U("SHORT_U", "Short u"),
    GENERAL("GENERAL", "General"),
    SILENT_LETTERS("SILENT_LETTERS", "Silent Letters"),
    ;

    private String value;
    private String name;

    LessonEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameFromValue(String value) {
        try {
            for (LessonEnum e : LessonEnum.values()) {
                if (e.value.toLowerCase().equals(value.toLowerCase())) {
                    return e.name;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public static String getValueFromName(String name) {
        try {
            for (LessonEnum e : LessonEnum.values()) {
                if (e.name.toLowerCase().equals(name.toLowerCase())) {
                    return e.value;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
