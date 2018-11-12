package com.pact.stubber.interfaces;

import com.pact.stubber.exceptions.StubberServerException;

public interface IServer {

    public void loadPacts() throws StubberServerException;

    public void startServer() throws StubberServerException;

    public void stopServer() throws StubberServerException;

    //public void killServer() throws StubberServerException;
}
