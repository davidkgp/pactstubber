package com.pact.parse.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class HeaderObj {

    private Map<String, List> maps;

    @Override
    public boolean equals(Object o) {

        if(o==null) return false;
        else if(!(o instanceof HeaderObj)) return false;
        else{
            HeaderObj subSet = (HeaderObj) o;

            if (!this.maps.keySet().containsAll(subSet.maps.keySet())) return false;
            return checkValues(this.maps,subSet.maps);
        }


    }

    public boolean checkValues(Map<String, List> superSet, Map<String, List> subSet){

        if(checkKeys(superSet.keySet(),subSet.keySet())){

            if(subSet.keySet().size()>superSet.keySet().size()){

                for (String key:superSet.keySet()) {
                    if(!checkLists(subSet.get(key),superSet.get(key))) return false;
                }

            }else{

                for (String key:subSet.keySet()) {
                    if(!checkLists(superSet.get(key),subSet.get(key))) return false;
                }

            }
        }

        return true;

    }

    public boolean checkKeys(Set<String> superKeys,Set<String> subKeys){
        if(emptyorNull(superKeys) && emptyorNull(subKeys)) return true;
        else if((emptyorNull(superKeys) && !emptyorNull((subKeys))) || (!emptyorNull(superKeys) && emptyorNull((subKeys)))) return false;
        else{
            if (subKeys.size() > superKeys.size()) return subKeys.containsAll(superKeys);
            else if (subKeys.size() <= superKeys.size()) return superKeys.containsAll(subKeys);

        }

        return false;
    }

    public boolean checkLists(List<String> superSet,List<String> subSet){
        if(emptyorNull(superSet) && emptyorNull(subSet)) return true;
        else if((emptyorNull(superSet) && !emptyorNull((subSet))) || (!emptyorNull(superSet) && emptyorNull((subSet)))) return false;
        else{
            if (subSet.size() > superSet.size()) return subSet.containsAll(superSet);
            else if (subSet.size() <= superSet.size()) return superSet.containsAll(subSet);

        }

        return false;

    }

    public <T extends Collection> boolean emptyorNull(T obj){
        return obj==null || (obj!=null && obj.isEmpty());
    }
}
