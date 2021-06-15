import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Map;

public class ClassesProducer {
    private final String path = "elena.ivkina.directory";
    private final Path packagePath = Paths.get("src/main/java/" + winPathWithPackage(path));

    public void createNewClass(String table, Map<String, String> params) {
        String className = SnakeCaseToCamelCase.className(table);
        String absPath = this.packagePath.toAbsolutePath().normalize().toString();
        try{
            Files.createDirectories(Paths.get(absPath));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(absPath + "/" + className + ".java"))) {
                String classDoc = MessageFormat.format("package {0};\n" +
                        addImportInstant(params) +
                        "import lombok.Getter;\n" +
                        "import lombok.Setter;\n" +
                        "\n" +
                        "public class {1} '{'\n", path, className);
                writer.write(classDoc);
                params.forEach((k,v) -> {
                    try {
                        writer.write(MessageFormat.format("@Getter @Setter private {0} {1};\n",
                                typeField(v), SnakeCaseToCamelCase.fieldName(k)));
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
    private String typeField(String dateType){
        String dateTypeLowerCase = dateType.toLowerCase();
        if (dateTypeLowerCase.contains("char") ||
                dateTypeLowerCase.contains("varchar") ||
                dateTypeLowerCase.contains("text")) {
            return "String";
        } else if(dateTypeLowerCase.contains("int")) {
            return "Integer";
        } else if(dateTypeLowerCase.contains("date") ||
                dateTypeLowerCase.contains("time")) {
            return "Instant";
        } else if(dateTypeLowerCase.contains("double")) {
            return "Double";
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

    private String winPathWithPackage(String packageDirectory) {
        return packageDirectory.replaceAll("\\.", "/");
    }

}
