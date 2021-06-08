import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

public class ClassesProducer {

    private Path packagePath = Paths.get("src/main/java/generatedClasses");

    public void createNewClass(String table, Map<String, String> params) {
        table = className(table);
        String absPath = this.packagePath.toAbsolutePath().normalize().toString();
        File p = new File(absPath);
        try{
            p.mkdir();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(absPath + "/" + table + ".java"))) {
                String classDoc = MessageFormat.format("package generatedClasses;\n" +
                        addImportInstant(params) +
                        "import lombok.Getter;\n" +
                        "import lombok.Setter;\n" +
                        "\n" +
                        "public class {0} '{'\n", table);
                writer.write(classDoc);
                params.forEach((k,v) -> {
                    try {
                        writer.write("@Getter @Setter private " + typeField(v) + " " + fieldName(k) + ";\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.write("}");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //Название класса форматируем под CamelCase
    public String className(String tableName) {
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
    public String fieldName(String columnName) {
        String newFieldName = className(columnName).toLowerCase();
        return newFieldName;
    }

    //Определение типа
    public String typeField(String dataType){
        if (dataType.toLowerCase().contains("char") ||
                dataType.toLowerCase().contains("varchar") ||
                dataType.toLowerCase().contains("text")) {
            return "String";
        } else if(dataType.toLowerCase().contains("int")) {
            return "int";
        } else if(dataType.toLowerCase().contains("date") ||
                dataType.toLowerCase().contains("time")) {
            return "Instant";
        } else if(dataType.toLowerCase().contains("doable")) {
            return "double";
        }
        return "Object";
    }

    //метод изменяет первую букву слова в верхний регистр
    private String capitalize(String str) {
        str = str.substring(0,1).toUpperCase() + str.substring(1);
        return str;
    }

    private String addImportInstant(Map<String, String> params) {
        for (String dataType: params.values()) {
            if(dataType.toLowerCase().contains("date") || dataType.contains("time")) {
                return "import java.time.Instant;\n";
            }
        }
        return "";
    }



}
