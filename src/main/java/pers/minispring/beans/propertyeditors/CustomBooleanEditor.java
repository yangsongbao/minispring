package pers.minispring.beans.propertyeditors;

import pers.minispring.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * @author songbao.yang
 */
public class CustomBooleanEditor extends PropertyEditorSupport {

    private final boolean allowEmpty;

    private final List<String> TRUE_LIST = new ArrayList<String>(){{
        add("true");
        add("1");
        add("yes");
        add("on");
    }};

    private final List<String> FALSE_LIST = new ArrayList<String>(){{
        add("false");
        add("0");
        add("no");
        add("off");
    }};


    public CustomBooleanEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String input = (text != null ? text.trim() : null);

        if (allowEmpty && !StringUtils.hasLength(text)){
            setValue(null);
        }
        if (TRUE_LIST.contains(input)){
            setValue(Boolean.TRUE);
        } else if (FALSE_LIST.contains(input)){
            setValue(Boolean.FALSE);
        } else {
            throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
        }
    }

    @Override
    public String getAsText() {
        if (Boolean.TRUE.equals(getValue())){
            return "true";
        } else if (Boolean.TRUE.equals(getValue())){
            return "false";
        } else {
            return "";
        }
    }
}
