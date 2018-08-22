package pers.minispring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.beans.TypeMismatchException;
import pers.minispring.beans.SimpleTypeConverter;

import static org.junit.Assert.fail;

public class TypeConverterTest {

    @Test
    public void testConvertStringToInt(){
        SimpleTypeConverter converter = new SimpleTypeConverter();

        Integer i = converter.convertIfNecessary("3", Integer.class);

        Assert.assertEquals(3, i.intValue());

        try {
           converter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException e){
            return;
        }
        fail();
    }

    @Test
    public void testConvertStringToBoolean(){
        SimpleTypeConverter converter = new SimpleTypeConverter();

        Boolean b = converter.convertIfNecessary("true", Boolean.class);

        Assert.assertTrue(b);

        try {
            converter.convertIfNecessary("33434", Boolean.class);
        } catch (TypeMismatchException e){
            return;
        }
        fail();
    }
}
