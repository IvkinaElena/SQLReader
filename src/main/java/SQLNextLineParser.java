import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SQLNextLineParser {
    private String tableName;
    private final LinkedHashMap<String, String> mapOfNameAndType;
    private final Set<String> constraint = Stream.of("CONSTRAINT",
            "CHECK",
            "UNIQUE",
            "PRIMARY",
            "EXCLUDE",
            "FOREIGN",
            "MATCH",
            "DEFERRABLE",
            "NOT",
            "ON")
            .collect(Collectors.toCollection(HashSet::new));

    public SQLNextLineParser(String statement) {
        this.mapOfNameAndType = new LinkedHashMap<>();
        setTableName(statement);
        setMapOfNameAndType(statement);
    }

    private void setTableName(String statement) {
        statement = statement.replaceAll("CREATE TABLE", "").trim();
        this.tableName = statement.substring(0, statement.indexOf("("));
    }

    private void setMapOfNameAndType(String statement) {
        //берем подстроку между первой открывающейся скобкой и последней закрывающеся скобкой
        String paramsTable = statement.substring(statement.indexOf("(")+1, statement.lastIndexOf(")")+1).trim();
        //проверям строку пока она не станет пустая, после обработки данных они будут удаляться из исходной строки
        while (!paramsTable.equals("")) {
            //первое слово - название столбца
            String columnName = paramsTable.split(" ")[0];
            //проверяем, не является ли название столбца служебным словом
            if (!this.constraint.contains(columnName.toUpperCase())) {
                //удаляем название столбца из строки с параметрами таблицы
                paramsTable = paramsTable.replaceAll(columnName, "").trim();
                //второе слово - название типа данных, берем значение до запятой или до конца строки
                String dataType;
                if (paramsTable.indexOf(',') != -1) {
                    dataType = paramsTable.substring(0, paramsTable.indexOf(','));
                } else {
                    dataType = paramsTable.substring(0, paramsTable.lastIndexOf(')'));
                }
                //если тип данных decimal или numeric нужно дополнить данные
                if (dataType.toLowerCase().contains("decimal") | dataType.toLowerCase().contains("numeric")) {
                    dataType += paramsTable.substring(paramsTable.indexOf(','), paramsTable.indexOf(")") + 1);
                }
                //удаляем подстроку с типом данных из строки с параметрами таблицы
                paramsTable = paramsTable.substring(dataType.length() + 1).trim();
                //записываем пару название и тип данных в поле объекта
                this.mapOfNameAndType.put(columnName, dataType);
            } else
                paramsTable = "";
        }

    }

    public String getTableName() {
        return this.tableName;
    }

    public HashMap<String, String> getMapOfNameAndType() {
        return this.mapOfNameAndType;
    }

    public static boolean statementContainsCreateTable(String statements) {
        statements = statements.toLowerCase();
        statements = statements.trim();
        statements = statements.replaceAll("/n", "");
        return statements.matches("^create(\\s*)table(.*)");
    }

}
