package com.pact.parse.dto;

import com.pact.parse.implementation.constants.MyFunctions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import java.util.Optional;
import java.util.function.BiPredicate;

import static com.pact.parse.implementation.constants.MyFunctions.jsonCompare;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RequestData<J> {

    private Optional<J> body;

    private String url;

    private HeaderObj header;

    private String method;





    public int hashCode() {
        int PRIME = 59;
        //int PRIME2 = 43;
        int PRIME3 = 53;
        int PRIME4 = 63;

        int result=-1;
        result = (method!=null && method.trim().length()>0?method.hashCode():PRIME)
                    //*(query!=null ?query.hashCode():PRIME2)
                    *(url!=null && url.trim().length()>0?url.hashCode():PRIME3)
                    *(body!=null && body.isPresent()?body.get().hashCode():PRIME4);
        //System.out.println(body.isPresent()?body.get().hashCode():88888);

        return result;

    }




    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RequestData)) {
            return false;
        }
        RequestData<J> other = (RequestData) o;

        String thismethod = getMethod();
        String othermethod = other.getMethod();
        if (thismethod == null ? othermethod != null : !thismethod.equals(othermethod)) {
            return false;
        }
        /*Map<String, List<String>> thisquery = getQuery();
        Map<String, List<String>> otherquery = other.getQuery();
        if (thisquery == null ? otherquery != null : !thisquery.equals(otherquery)) {
            return false;
        }*/
        String thispath = getUrl();
        String otherpath = other.getUrl();
        if (thispath == null ? otherpath != null : !thispath.equals(otherpath)) {
            return false;
        }

        boolean bodyResult = compareBody(this.body.orElse(null),other.body.orElse(null));


        if(!bodyResult) return bodyResult;




        HeaderObj thisrequestHeaders = getHeader();
        HeaderObj otherrequestHeaders = other.getHeader();
        return thisrequestHeaders == null ? otherrequestHeaders == null
                : thisrequestHeaders.equals(otherrequestHeaders);
    }

    private boolean compareBody(J superSet, J subSet) {
        if(superSet == null && subSet!=null) return false;
        if(superSet != null && subSet==null) return false;
        if(superSet == null && subSet==null) return true;
        return superSet.equals(subSet);
    }


}
