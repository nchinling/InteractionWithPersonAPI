package sg.edu.nus.iss.InteractionPerson.API.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.InteractionPerson.API.model.Person;
import sg.edu.nus.iss.InteractionPerson.API.service.PersonService;


@RestController
@RequestMapping(path="/retrieve")
public class RestControllerApp implements Serializable {

    @Autowired
    private PersonService personService;
    
    @GetMapping
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

}
