package com.pact.stubber.impl.handler;

import com.pact.parse.dto.HeaderObj;
import com.pact.parse.dto.RequestData;
import com.pact.parse.dto.ResponseData;
import com.pact.stubber.MyFunctions;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class MyPactHandler implements HttpHandler {

    private Map<RequestData, ResponseData> interactions = null;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        ResponseData data = null;

        if("/echo".equals(httpExchange.getRequestURI().getPath())){
            data = new ResponseData(200,Optional.ofNullable("This is an echo response"),new HeaderObj(Collections.singletonMap("Content-Type",Collections.singletonList("text/plain"))));

        }else if(!MyFunctions.mapNotEmpty.test(interactions)){
            data = new ResponseData(404,Optional.ofNullable("Pacts not present in the folder or folder not found"),new HeaderObj(Collections.singletonMap("Content-Type",Collections.singletonList("text/plain"))));

        }else{

            data = interactions.getOrDefault(MyFunctions.convertIncomingRequest.apply(httpExchange),
                    new ResponseData(404, Optional.ofNullable("Matching Response not found"),new HeaderObj(Collections.singletonMap("Content-Type",Collections.singletonList("text/plain")))));

        }

        MyFunctions.formResponse.accept(data,httpExchange);
    }
}
