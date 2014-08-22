package com.app.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.app.services.ApnsClient;
import com.relayrides.pushy.apns.ApnsEnvironment;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.PushManagerFactory;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;

@Service("apnsClient")
public class ApnsClientImpl implements ApnsClient {
	
	PushManager<SimpleApnsPushNotification> pushManager;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public ApnsClientImpl() {
		
	}
	
	@PostConstruct
	public void init(){
		PushManagerFactory<SimpleApnsPushNotification> pushManagerFactory;
		try {
			
			String keystoreFile="com.ankitkhanal.ch.dev.p12";//"iPadBooks-public-production.p12";
			String keystorePassword="12345";
			
			InputStream keystoreInputStream = ApnsClientImpl.class.getClassLoader().getResourceAsStream(keystoreFile);
			
			final KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(keystoreInputStream, keystorePassword != null ? keystorePassword.toCharArray() : null);
			SSLContext sslContext = PushManagerFactory.createDefaultSSLContext(keyStore, keystorePassword != null ? keystorePassword.toCharArray() : null);
			
			pushManagerFactory = new PushManagerFactory<SimpleApnsPushNotification>(
			        ApnsEnvironment.getSandboxEnvironment(),
			        sslContext);
			pushManager =
			        pushManagerFactory.buildPushManager();

			pushManager.start();
		} catch (UnrecoverableKeyException | KeyManagementException
				| KeyStoreException | NoSuchAlgorithmException
				| CertificateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void destroy(){
		try {
			pushManager.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);//let pushy drain its in-memory buffer
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see com.app.services.IApnsClient#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String hexToken, String message){
		final byte[] token = TokenUtil.tokenStringToByteArray(hexToken);

			final ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
			payloadBuilder.setAlertBody(message);
			//payloadBuilder.setSoundFileName("ring-ring.aiff");
			final String payload = payloadBuilder.buildWithDefaultMaximumLength();

			try {
				pushManager.getQueue().put(new SimpleApnsPushNotification(token, payload));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
