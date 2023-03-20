package sg.edu.nus.iss.InteractionPerson.API.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fName;
    private String sName;
    private String dataId;
    
    public Person() {}

    public Person(String fName, String sName) {
        this.fName = fName;
        this.sName = sName;
    }

    public String getfName() {return fName;}
    public void setfName(String fName) {this.fName = fName;}

    public String getsName() {return sName;}
    public void setsName(String sName) {this.sName = sName;}
    
    public String getDataId() {return dataId;}
    public void setDataId(String dataId) {this.dataId = dataId;}


    public static Person createPersonObject(String json) throws IOException{
        Person p = new Person();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            p.setDataId(o.getString("dataId"));
            p.setfName(o.getString("fName"));
            p.setsName(o.getString("sName"));
        }
        return p;
    }


    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                .add("dataId", this.getDataId())
                .add("fName", this.getfName())
                .add("sName", this.getsName())  
                .build();
    }

    
}
