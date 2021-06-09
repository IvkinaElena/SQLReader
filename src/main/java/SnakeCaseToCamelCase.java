import java.util.Locale;

public class SnakeCaseToCamelCase {

    //Название класса форматируем под CamelCase
    public static String className(String tableName) {
        String newClassName = tableName.toLowerCase();
        newClassName = capitalize(newClassName);
        if(newClassName.indexOf("_") > 0) {
            String wordArr[] = newClassName.split("_");
            newClassName = "";
            for(String word: wordArr) {
                newClassName += capitalize(word);
            }
        }
        return newClassName;
    }

    //Форматируем названия полей класса под CamelCase
    public static String fieldName(String columnName) {
        String newFieldName = className(columnName);
        newFieldName = newFieldName.substring(0,1).toLowerCase() + newFieldName.substring(1);
        return newFieldName;
    }

    //метод изменяет первую букву слова в верхний регистр
    private static String capitalize(String str) {
        str = str.substring(0,1).toUpperCase() + str.substring(1);
        return str;
    }
}
