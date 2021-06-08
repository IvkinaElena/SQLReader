import java.util.*;

public class SQLParser {
    private Map<String, Map<String, String>> allTables;

    public SQLParser() {
        this.allTables = new HashMap<>();
    }

    public void addTables(String table, Map<String, String> params) {
        this.allTables.put(table.trim(), params);
    }

    public void getAllCreatedTableVWithColumns() {
        this.allTables.forEach((k, v) -> System.out.println(k + " : " + v));
    }

    public Set<String> getAllTableNames() {
        return this.allTables.keySet();
    }

    public Map<String, Map<String, String>> getAllTables() {
        return allTables;
    }
}
