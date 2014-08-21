package com.app.services;

public interface ApnsClient {

	public abstract void send(String hexToken, String message);

}