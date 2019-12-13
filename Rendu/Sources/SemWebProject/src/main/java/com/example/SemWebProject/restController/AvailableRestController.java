package com.example.SemWebProject.restController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.example.SemWebProject.JsonReader.readJsonArrayFromUrl;
import static com.example.SemWebProject.JsonReader.readJsonFromUrl;

@RestController
@RequestMapping("availableRest")
public class AvailableRestController {
    @GetMapping(produces = "application/json")
    public int getAvailable(@RequestParam(name = "id") Integer id, @RequestParam(name = "city") String city) throws IOException, JSONException {

        if (city.equals("Saint-Etienne")) {
            JSONObject response = readJsonFromUrl("https://saint-etienne-gbfs.klervi.net/gbfs/en/station_status.json");
            System.out.println(city + ": " + response);
            for (int i = 0; i < response.getJSONObject("data").getJSONArray("stations").length(); i++) {
                if (response.getJSONObject("data").getJSONArray("stations").getJSONObject(i).get("station_id").toString().equals(id.toString())) {
                    return Integer.parseInt(response.getJSONObject("data").getJSONArray("stations").getJSONObject(i).get("num_bikes_available").toString());

                }
            }
        } else if (city.equals("Rennes")) {
            JSONObject response = readJsonFromUrl("https://data.rennesmetropole.fr/api/records/1.0/search/?dataset=etat-des-stations-le-velo-star-en-temps-reel");
            System.out.println(city + ": " + response);
            for (int i = 0; i < response.getJSONArray("records").length(); i++) {
                if (response.getJSONArray("records").getJSONObject(i).getJSONObject("fields").get("idstation").toString().equals(id.toString())) {
                    return Integer.parseInt(response.getJSONArray("records").getJSONObject(i).getJSONObject("fields").get("nombrevelosdisponibles").toString());

                }
            }
        } else {
            JSONArray response = readJsonArrayFromUrl("https://api.jcdecaux.com/vls/v3/stations?contract=" + city + "&apiKey=4a9440953e549e83e6263734f3ced9b67c44b639");

            for (int i = 0; i < response.length(); i++) {
                if (response.getJSONObject(i).get("number").toString().equals(id.toString())) {
                    return Integer.parseInt(response.getJSONObject(i).getJSONObject("mainStands").getJSONObject("availabilities").get("bikes").toString());

                }
            }


        }
        return -1;
    }
}
