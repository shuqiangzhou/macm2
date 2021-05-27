package com.mail.comm.base;

/**
 * Created by pc on 2017/3/15.
 */

public class FragmentParam {
    public BaseFrg from;
    public Class<?> cls;
    public Object data;
    public String tag="";
    public String Id="";
    public FragmentParam.TYPE type;
    public boolean addToBackStack;

    public FragmentParam() {
        this.type = FragmentParam.TYPE.ADD;
        this.addToBackStack = true;
    }

    public static enum TYPE {
        ADD,
        REPLACE;
        private TYPE() {
        }
    }
}
