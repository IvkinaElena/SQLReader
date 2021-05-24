import java.util.*;

public class SQLParser {
    private Map<String, Map<String, String>> allTables;

    public SQLParser() {
        this.allTables = new HashMap<>();
    }
    public void addTables(String table, Map<String, String> params) {
        this.allTables.put(table, params);
    }

    public void getAllCreatedTableVWithColumns() {
        for (Map.Entry<String, Map<String, String>> entry : this.allTables.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    public Set<String> getAllTableNames() {
        return this.allTables.keySet();
    }

}
