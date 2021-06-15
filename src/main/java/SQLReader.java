import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SQLReader {
    public static void main(String[] args) {
        System.out.println("Input absolute path file separated by commas:");
        Scanner input = new Scanner(System.in);
        String inputPath = input.nextLine();
        String[] pathArray = inputPath.split(",");
        SQLParser newSQLParser = new SQLParser();

        for (String path : pathArray) {
            try (BufferedReader br =
                         new BufferedReader(new FileReader(path.trim()))) {
                String statement = br.readLine();
                while (statement != null) {
                    if (statement.trim().endsWith(";")) {
                        if (SQLNextLineParser.statementContainsCreateTable(statement)) {
                            SQLNextLineParser newTable = new SQLNextLineParser(statement);
                            if (!newSQLParser.getAllTableNames().contains(newTable.getTableName())) {
                                newSQLParser.addTables(newTable.getTableName(), newTable.getMapOfNameAndType());
                            }
                        }
                        statement = br.readLine();
                    } else
                        statement += br.readLine() + " ";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        newSQLParser.printAllCreatedTableVWithColumns();

        ClassesProducer classesProducer = new ClassesProducer();
        newSQLParser.getAllTables().forEach((k,v) -> classesProducer.createNewClass(k, v));
    }
}
