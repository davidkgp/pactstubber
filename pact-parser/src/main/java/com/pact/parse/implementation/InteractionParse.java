package com.pact.parse.implementation;

import com.pact.parse.dto.HeaderObj;
import com.pact.parse.exception.PactParseException;
import com.pact.parse.implementation.constants.MyFunctions;
import com.pact.parse.interfaces.IInteractionParse;
import org.json.JSONObject;

public class InteractionParse implements IInteractionParse<JSONObject> {


    @Override
    public HeaderObj getHeadersRequest(JSONObject interaction) throws PactParseException {
        return MyFunctions.getRequestHeader.apply(interaction);
    }

    @Override
    public JSONObject getBodyRequest(JSONObject interaction) throws PactParseException {
        return MyFunctions.getRequestBody.apply(interaction);
    }

    @Override
    public JSONObject getBodyResponse(JSONObject interaction) throws PactParseException {
        return MyFunctions.getResponseBody.apply(interaction);
    }

    @Override
    public HeaderObj getHeadersResponse(JSONObject interaction) throws PactParseException {
        return MyFunctions.getResponseHeader.apply(interaction);
    }

    @Override
    public int getStatus(JSONObject interaction) throws PactParseException {
        return MyFunctions.getStatus.apply(interaction);
    }

    @Override
    public String getUrl(JSONObject interaction) throws PactParseException {
        return MyFunctions.getUrl.apply(interaction);
    }

    @Override
    public String getMethod(JSONObject interaction) throws PactParseException {
        return MyFunctions.getMethod.apply(interaction);
    }


}
