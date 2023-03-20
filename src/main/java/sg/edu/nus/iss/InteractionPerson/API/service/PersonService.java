package sg.edu.nus.iss.InteractionPerson.API.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
}
