package sg.edu.nus.iss.InteractionPerson.API.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.InteractionPerson.API.model.Person;

@Service
public class PersonService {

    @Value("${person.railway.url}")
    private String personRailwayUrl;
    
    public Optional<Person> getPerson(String userId)
    throws IOException{
        System.out.println("personRailwayUrl: " + personRailwayUrl);
       
        String railwayUrl = UriComponentsBuilder
                            .fromUriString(personRailwayUrl)
                            .queryParam("userId", userId)
                            .toUriString();
        
        System.out.println("railwayUrl: " + railwayUrl);

    
        RestTemplate template= new RestTemplate();
        ResponseEntity<String> r  = template.getForEntity(railwayUrl, 
                String.class);
        Person p = Person.createPersonObject(r.getBody());
        if(p != null)
            return Optional.of(p);
        return Optional.empty();

    }


    //Convert form data to JSON object
    public String toJsonString(MultiValueMap<String, String> formData){
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, List<String>> entry : formData.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            jsonObject.put(key, values.get(0));
        }

        String url = "https://person-api-production-3053.up.railway.app/response/postbody";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toString(), headers);
        ResponseEntity<String> responseEntity = new RestTemplate().exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseBody = responseEntity.getBody();
    
        return responseBody;
    }
    
}
