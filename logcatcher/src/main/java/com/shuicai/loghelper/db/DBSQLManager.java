package com.shuicai.loghelper.db;

class DBSQLManager {


    /**
     * 常用日志数据库
     */
    public static class LogTable {
        public static final String TABLE_NAME = "log";

        public enum Column {
            ID("_id", 0),//默认主键
            TIME("time",1),
            TYPE("type",2),
            CONTENT("content",3);//日志内容

            public final String name;//字段名
            public final int index;//在表中的字段索引,

            /**
             * @param columnName  字段名
             * @param columnIndex 在表中的字段索引,不能错误,修改数据库务必维护该类
             */
            private Column(String columnName, int columnIndex) {
                name = columnName;
                index = columnIndex;
            }

            @Override
            public String toString() {
                return name;
            }


            public static String[] getAllColumns() {
                Column[] columns = Column.values();
                String[] columnsStrs = new String[columns.length];
                for (int i = 0; i < columnsStrs.length; i++) {
                    columnsStrs[i] = columns[i].name;
                }
                return columnsStrs;
            }
        }

        public static String getCreateSQL() {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE IF NOT EXISTS ")
                    .append(TABLE_NAME).append("( ")
                    .append(Column.ID.name).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                    .append(Column.TIME.name).append(" TEXT , ")
                    .append(Column.TYPE.name).append(" TEXT , ")
                    .append(Column.CONTENT.name).append(" TEXT")
                    .append(");");
            return sb.toString();
        }

        public static String getDropSQL() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }
    }

    /**
     * 崩溃日志数据库
     */
    public static class CrashTable {
        public static final String TABLE_NAME = "crash";

        public enum Column {
            ID("_id", 0),//默认主键
            TIME("time",1),
            TYPE("type",2),
            CONTENT("content", 3);

            public final String name;//字段名
            public final int index;//在表中的字段索引,

            /**
             * @param columnName  字段名
             * @param columnIndex 在表中的字段索引,不能错误,修改数据库务必维护该类
             */
            private Column(String columnName, int columnIndex) {
                name = columnName;
                index = columnIndex;
            }

            @Override
            public String toString() {
                return name;
            }


            public static String[] getAllColumns() {
                Column[] columns = Column.values();
                String[] columnsStrs = new String[columns.length];
                for (int i = 0; i < columnsStrs.length; i++) {
                    columnsStrs[i] = columns[i].name;
                }
                return columnsStrs;
            }
        }

        public static String getCreateSQL() {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE IF NOT EXISTS ")
                    .append(TABLE_NAME).append("( ")
                    .append(Column.ID.name).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                    .append(Column.TIME.name).append(" TEXT ,")
                    .append(Column.TYPE.name).append(" TEXT ,")
                    .append(Column.CONTENT.name).append(" TEXT")
                    .append(");");
            return sb.toString();
        }

        public static String getDropSQL() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }
    }

}
