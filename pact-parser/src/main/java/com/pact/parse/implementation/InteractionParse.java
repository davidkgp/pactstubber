package com.pact.parse.implementation;

import com.pact.parse.dto.HeaderObj;
import com.pact.parse.dto.payload.JSONObjEx;
import com.pact.parse.exception.PactParseException;
import com.pact.parse.implementation.constants.MyFunctions;
import com.pact.parse.interfaces.IInteractionParse;

public class InteractionParse implements IInteractionParse<JSONObjEx> {


    @Override
    public HeaderObj getHeadersRequest(JSONObjEx interaction) throws PactParseException {
        return MyFunctions.getRequestHeader.apply(interaction);
    }

    @Override
    public JSONObjEx getBodyRequest(JSONObjEx interaction) throws PactParseException {
        return MyFunctions.getRequestBody.apply(interaction);
    }

    @Override
    public JSONObjEx getBodyResponse(JSONObjEx interaction) throws PactParseException {
        return MyFunctions.getResponseBody.apply(interaction);
    }

    @Override
    public HeaderObj getHeadersResponse(JSONObjEx interaction) throws PactParseException {
        return MyFunctions.getResponseHeader.apply(interaction);
    }

    @Override
    public int getStatus(JSONObjEx interaction) throws PactParseException {
        return MyFunctions.getStatus.apply(interaction);
    }

    @Override
    public String getUrl(JSONObjEx interaction) throws PactParseException {
        return MyFunctions.getUrl.apply(interaction);
    }

    @Override
    public String getMethod(JSONObjEx interaction) throws PactParseException {
        return MyFunctions.getMethod.apply(interaction);
    }


}
