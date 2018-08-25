package pers.minispring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import pers.minispring.beans.propertyeditors.CustomBooleanEditor;
import pers.minispring.beans.propertyeditors.CustomNumberEditor;


public class CustomEditorTest {

    @Test
    public void testConvertString() {

        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer) value).intValue());

        editor.setAsText("");
        Assert.assertNull(editor.getValue());

        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConvertBoolean() {

        CustomBooleanEditor editor = new CustomBooleanEditor(true);

        editor.setAsText("true");
        Assert.assertEquals(true, editor.getValue());
        editor.setAsText("false");
        Assert.assertEquals(false, editor.getValue());

        editor.setAsText("on");
        Assert.assertEquals(true, editor.getValue());
        editor.setAsText("off");
        Assert.assertEquals(false, editor.getValue());

        editor.setAsText("yes");
        Assert.assertEquals(true, editor.getValue());
        editor.setAsText("no");
        Assert.assertEquals(false, editor.getValue());

        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
