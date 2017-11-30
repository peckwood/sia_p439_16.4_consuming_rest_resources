package consumer;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import consumer.entity.User;

public class Poster {
	public static void main(String[] args) throws JsonProcessingException {
		System.out.println("Please start project sia_p427_16.2.2_REST_http_message_converter first");
		String url = "http://localhost:8080/sia_p427_16.2.2_REST_http_message_converter/usersRestfulController";
		String url_WithResponseHeaders = "http://localhost:8080/sia_p427_16.2.2_REST_http_message_converter/users/withResponseHeader";
		String url_post_form_emulation = "http://localhost:8080/sia_p427_16.2.2_REST_http_message_converter/users/post-form-emulation";
		RestTemplate rest = new RestTemplate();
		User newUser = new User("newGuy", 22);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Have to convert java object to jason manually, why can't Spring do it for us like in Rest API?");
		String userInJson = mapper.writeValueAsString(newUser);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> entity = new HttpEntity<>(userInJson, requestHeaders);
		
		//We need to post an httpEntity with the Content-Type or we will get 415: Unsupported Media Type 
		//thanks to "consumes" in controller method
		System.out.println("\n postForObject======================================================================");
		//HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String,Object>>(map, headers);
		User userPosted1 = rest.postForObject(url, entity, User.class);
		System.out.println(userPosted1);
		
		System.out.println("\n postForEntity======================================================================");
		ResponseEntity<User> responseEntity= rest.postForEntity(url_WithResponseHeaders, entity, User.class);
		System.out.println("userPosted2.getBody() " + responseEntity.getBody());
		HttpHeaders responseHeaders = responseEntity.getHeaders();
		System.out.println("responseHeaders.getContentType() " + responseHeaders.getContentType());
		System.out.println("responseHeaders.getDate() " + responseHeaders.getDate());
		System.out.println("responseHeaders.getLocation() " + responseHeaders.getLocation());
		System.out.println("responseHeaders.getFirst(HttpHeaders.TRANSFER_ENCODING) " + responseHeaders.getFirst(HttpHeaders.TRANSFER_ENCODING));
		
		System.out.println("\n postForLocation======================================================================");
		URI location = rest.postForLocation(url_WithResponseHeaders, entity);
		System.out.println(location);
		
		System.out.println("\n postForObject= emulate form submission=====================================================================");
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("name", "Raiden");
		body.add("age", "11");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> entity1 = new HttpEntity<MultiValueMap<String,String>>(body, headers);
		User user1 = rest.postForObject(url_post_form_emulation, entity1, User.class);
		System.out.println("user: "+user1);
	}
}
