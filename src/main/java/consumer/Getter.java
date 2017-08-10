package consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import consumer.entity.User;

public class Getter {
	public static void main(String[] args) {
		String url = "http://localhost:8080/sia_p427_16.2.2_REST_http_message_converter/usersRestfulController/{id}";
		String notFoundUrl = "http://localhost:8080/sia_p427_16.2.2_REST_http_message_converter/users/2";
		System.out.println("Please start project sia_p427_16.2.2_REST_http_message_converter first");
		System.out.println("======================================================================");
		
		
		RestTemplate rest = new RestTemplate();
		//{id} is a placeholder that will be replaced by the 3rd method parameter(1)
		User user = rest.getForObject(url, User.class, 1);
		System.out.println(user);
		
		
		System.out.println("======================================================================");
		//store parameters in a map
		//map's key matches the placeholder
		Map<String, String> urlVariables= new HashMap<>();
		urlVariables.put("id", "1");
		User user1 = rest.getForObject(url, User.class, urlVariables);
		System.out.println(user1);
		
		
		System.out.println("======================================================================");
		ResponseEntity<User> responseEntity = rest.getForEntity(url, User.class, urlVariables);
		showResponseEntityContent(responseEntity);
		
		
		System.out.println("======================================================================");
		ResponseEntity<User> notFoundResponseEntity = null;
		try {
			notFoundResponseEntity = rest.getForEntity(notFoundUrl,User.class);
			showResponseEntityContent(notFoundResponseEntity);
		} catch (HttpClientErrorException e) {
			//Unfortunately you cannot get a ResponseEnitty containing the Error if 404 is returned
			e.printStackTrace();
			System.out.println(e.getRawStatusCode());
			System.out.println(e.getResponseBodyAsString());
		}
		
		System.out.println("======================================================================");
		List<User> users = rest.getForObject("http://localhost:8080/sia_p427_16.2.2_REST_http_message_converter/users", ArrayList.class);
		System.out.println(users);
		
		
	}
	
	private static void showResponseEntityContent(ResponseEntity<?> responseEntity){
		System.out.println("responseEntity.getBody(): " + responseEntity.getBody());
		System.out.println("responseEntity.getStatusCodeValue(): " + responseEntity.getStatusCodeValue());
		System.out.println("responseEntity.getHeaders(): " + responseEntity.getHeaders());
		System.out.println("responseEntity.hasBody(): " + responseEntity.hasBody());
		HttpHeaders headers = responseEntity.getHeaders();
		System.out.println("headers.getContentLength(): " + headers.getContentLength());
		System.out.println("headers.getDate(): " + headers.getDate());
		System.out.println("headers.getAccept(): " + headers.getAccept());
		System.out.println("headers.getContentType(): " + headers.getContentType());
		System.out.println("headers.get(headers.TRANSFER_ENCODING): " + headers.get(HttpHeaders.TRANSFER_ENCODING));
	}
}
