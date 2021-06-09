import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Map;

public class ClassesProducer {

    private Path packagePath = Paths.get("src/main/java/generatedClasses");

    public void createNewClass(String table, Map<String, String> params) {
        table = SnakeCaseToCamelCase.className(table);
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
                        writer.write("@Getter @Setter private " + typeField(v) + " " + SnakeCaseToCamelCase.fieldName(k) + ";\n");
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


    //Определение типа
    private String typeField(String dataType){
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

    private String addImportInstant(Map<String, String> params) {
        for (String dataType: params.values()) {
            if(dataType.toLowerCase().contains("date") || dataType.contains("time")) {
                return "import java.time.Instant;\n";
            }
        }
        return "";
    }



}
