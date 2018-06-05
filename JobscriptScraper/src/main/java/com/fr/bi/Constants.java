package com.fr.bi;

public abstract class Constants {

    public static final String KEY_PROC_ID = "PROCESS_ID";
    public static final String KEY_SFC_ID = "SFC_ID";
    public static final String KEY_OP_DATE = "OP_DATE";
    public static final String KEY_SQL_FILEPATH = "CONTEXT_SQL_FILE_PATH";
    
    public static final String REGEX_ARGS = "[\\w\\.]{1,}\\(([\\w\\W\\s\\\"\\(\\)]{1,})\\)[\\n\\r\\s]{0,}";
    public static final String REGEX_ITEM = "[\\w\\,\\.]{1,}\\.item";
    
}
