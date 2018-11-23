package com.pact.parse.dto.payload;

import com.my.json.compare.JsonCompare;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class JSONObjEx extends JSONObject {


    public JSONObjEx(JSONObject obj){
        super(obj!=null?obj.toMap(): null);
    }

    public JSONObjEx() {
        super();
    }

    public JSONObjEx(JSONObject jo, String[] names) {
        super(jo, names);
    }

    public JSONObjEx(JSONTokener x) throws JSONException {
        super(x);
    }

    public JSONObjEx(Map<?, ?> m) {
        super(m);
    }

    public JSONObjEx(Object object, String[] names) {
        super(object, names);
    }

    public JSONObjEx(String source) throws JSONException {
        super(source);
    }

    public JSONObjEx(String baseName, Locale locale) throws JSONException {
        super(baseName, locale);
    }

    public JSONObjEx(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof JSONObjEx)) return false;
        return JsonCompare.compare(Optional.ofNullable(this.toString()),Optional.ofNullable(o.toString()),null).isEmpty();
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();

    }
}
