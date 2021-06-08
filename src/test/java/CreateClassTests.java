import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CreateClassTests {

    @Test
    public void classNameTest() {
        ClassesProducer classesProducer = new ClassesProducer();
        assertEquals("Table", classesProducer.className("TABLE"));
        assertEquals("Table", classesProducer.className("table"));
        assertEquals("Table", classesProducer.className("Table"));
        assertEquals("TableName", classesProducer.className("Table_name"));
        assertEquals("TableName", classesProducer.className("TABLE_NAME"));
        assertEquals("TableNameFirst", classesProducer.className("Table_name_FIRST"));
        assertNotEquals("TableName", classesProducer.className("Tablename"));
    }

    @Test
    public void fieldNameTest() {
        ClassesProducer classesProducer = new ClassesProducer();
        assertEquals("field", classesProducer.fieldName("FIELD"));
        assertEquals("field", classesProducer.fieldName("field"));
        assertEquals("field", classesProducer.fieldName("Field"));
        assertEquals("fieldName", classesProducer.fieldName("Field_name"));
        assertEquals("fieldName", classesProducer.fieldName("FIELD_NAME"));
        assertEquals("fieldNameFirst", classesProducer.fieldName("field_name_FIRST"));
        assertNotEquals("fieldName", classesProducer.fieldName("fieldname"));
    }

}
