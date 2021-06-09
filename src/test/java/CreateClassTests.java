import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CreateClassTests {

    @Test
    public void classNameTest() {
        assertEquals("Table", SnakeCaseToCamelCase.className("TABLE"));
        assertEquals("Table", SnakeCaseToCamelCase.className("table"));
        assertEquals("Table", SnakeCaseToCamelCase.className("Table"));
        assertEquals("TableName", SnakeCaseToCamelCase.className("Table_name"));
        assertEquals("TableName", SnakeCaseToCamelCase.className("TABLE_NAME"));
        assertEquals("TableNameFirst", SnakeCaseToCamelCase.className("Table_name_FIRST"));
        assertNotEquals("TableName", SnakeCaseToCamelCase.className("Tablename"));
    }

    @Test
    public void fieldNameTest() {
        assertEquals("field", SnakeCaseToCamelCase.fieldName("FIELD"));
        assertEquals("field", SnakeCaseToCamelCase.fieldName("field"));
        assertEquals("field", SnakeCaseToCamelCase.fieldName("Field"));
        assertEquals("fieldName", SnakeCaseToCamelCase.fieldName("Field_name"));
        assertEquals("fieldName", SnakeCaseToCamelCase.fieldName("FIELD_NAME"));
        assertEquals("fieldNameFirst", SnakeCaseToCamelCase.fieldName("field_name_FIRST"));
        assertNotEquals("fieldName", SnakeCaseToCamelCase.fieldName("fieldname"));
    }

}
