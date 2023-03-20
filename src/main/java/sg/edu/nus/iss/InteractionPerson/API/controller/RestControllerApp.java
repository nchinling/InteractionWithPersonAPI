package sg.edu.nus.iss.InteractionPerson.API.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.InteractionPerson.API.model.Person;
import sg.edu.nus.iss.InteractionPerson.API.service.PersonService;


@RestController
@RequestMapping
public class RestControllerApp implements Serializable {

    @Autowired
    private PersonService personService;
    
    @GetMapping(path="/retrieve")
        public ResponseEntity<String> getUser(@RequestParam(name="userId") String userId) throws IOException{
        Optional<Person> person = personService.getPerson(userId);

        if(person.isEmpty()){
            JsonObject error = Json.createObjectBuilder()
                    .add("message", "Person %s not found".formatted(userId))
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error.toString());
        }
        return ResponseEntity.ok(person.get().toJSON().toString());
        // return ResponseEntity.ok("All is well");
    
    }


    @PostMapping(
    path="/postdata",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postBody(@RequestParam MultiValueMap<String, String> formData) throws IOException{
    
        // Convert form data to JSON object and then use it to post to http;
    String responseBody = personService.toJsonString(formData);

    // Return response from external API
    return ResponseEntity.ok(responseBody);
    }


}
