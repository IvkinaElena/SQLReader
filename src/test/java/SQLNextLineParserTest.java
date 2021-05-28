import org.junit.jupiter.api.Test;

public class SQLNextLineParserTest {
    @Test
    public void containsCreateTableTest() {
        assert(SQLNextLineParser.statementContainsCreateTable("CREATE TABLE TEST"));
        assert(SQLNextLineParser.statementContainsCreateTable("CREATE    TABLE     TEST"));
        assert(SQLNextLineParser.statementContainsCreateTable("CREATE" + "/n"+ "TABLE TEST"));
        assert(SQLNextLineParser.statementContainsCreateTable("create TABLE TEST"));
        assert(SQLNextLineParser.statementContainsCreateTable("CREATE table TEST"));
        assert(!SQLNextLineParser.statementContainsCreateTable("CREATE button TEST"));
        assert(!SQLNextLineParser.statementContainsCreateTable("test test TEST"));

    }
}
