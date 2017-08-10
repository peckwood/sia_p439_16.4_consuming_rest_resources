package consumer;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import consumer.entity.User;

public class Exchanger {
	public static void main(String[] args) throws JsonProcessingException {
		System.out.println("Please start project sia_p427_16.2.2_REST_http_message_converter first");
		System.out.println("exchange is good for ");
		String postUrl = "http://localhost:8080/sia_p427_16.2.2_REST_http_message_converter/usersRestfulController";
		RestTemplate rest = new RestTemplate();
		User newUser = new User("newGuy", 22);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Have to convert java object to jason manually, why can't Spring do it for us like in Rest API?");
		String userInJson = mapper.writeValueAsString(newUser);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		MediaType[] mediaTypeArray = {MediaType.APPLICATION_JSON_UTF8};
		requestHeaders.setAccept(Arrays.asList(mediaTypeArray));
		HttpEntity<String> requestEntity = new HttpEntity<>(userInJson, requestHeaders);
		
		System.out.println("\n POST======================================================================");
		ResponseEntity<User> responseEntity = rest.exchange(postUrl, HttpMethod.POST, requestEntity, User.class);
		System.out.println(responseEntity);
		
		System.out.println("\n GET======================================================================");
		String getURL = "http://localhost:8080/sia_p427_16.2.2_REST_http_message_converter/usersRestfulController/{id}";
		ResponseEntity<User> responseEntity1 = rest.exchange(getURL, HttpMethod.GET, null, User.class, 1);
		System.out.println(responseEntity1);
		
		
	}
}
