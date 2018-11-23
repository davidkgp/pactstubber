package com.pact.parse.interfaces;

import com.pact.parse.exception.PactParseException;

public interface IInteractionParse<J> {

    public <H> H getHeadersRequest(J interaction) throws PactParseException;

    public <B> B getBodyRequest(J interaction) throws PactParseException;

    public <B> B getBodyResponse(J interaction) throws PactParseException;

    public <H> H getHeadersResponse(J interaction) throws PactParseException;

    public int getStatus(J interaction) throws PactParseException;

    public String getUrl(J interaction) throws PactParseException;

    public String getMethod(J interaction) throws PactParseException;
}
