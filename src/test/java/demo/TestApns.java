package demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class TestApns {
	String token ="3eaa5eae3663036c34f155939ae84c8d8a3a1194d50f57f1e73e5c8c94ededf9";
	String badtoken = "3eaa5eae3663036c34f155939ae84c8d8a3a1194d50f57f1e73e5c8c94ededf6";
	long count =0;
	long goodCount =0;
	long badCount =0;
	Map<Long,Boolean> messageStatusMap=new HashMap<Long, Boolean>();
	
	
	@Test
	public void testApns() {
		for(int i=1;i<1000;i++){
		String tokentoUse=token;
		boolean good = sendGood();
		if(!good){
			tokentoUse =badtoken;
			badCount++;
		}else{
			goodCount++;
		}
		count++;
		messageStatusMap.put(count, good);
		RestTemplate restTemplate=new RestTemplate();
		restTemplate.getForObject("http://localhost:2080/demo/send?message={}&deviceId={}", Map.class, "Hello"+count, tokentoUse);
		}
		
		for(Long e:messageStatusMap.keySet()){
			System.out.println(e+"="+messageStatusMap.get(e));
		}
	}
	
	boolean sendGood(){
		if((Math.random()*1000)>990){
			return false;
		}
		return true;
	}
}
