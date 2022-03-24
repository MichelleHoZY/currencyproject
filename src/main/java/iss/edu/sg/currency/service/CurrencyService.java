package iss.edu.sg.currency.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import iss.edu.sg.currency.model.Conversion;
import iss.edu.sg.currency.model.Currency;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonStructure;

@Service
public class CurrencyService {

    private static final String baseURL = "https://free.currconv.com/api/v7/";
    private static String apiKey = null;
    public Map<String, Currency> COUNTRY_CURRENCY_MAP;
    public List<String> COUNTRY_CURRENCY_MAP_KEYSET_SORTED;

    // @Value("${currconv.key}")
    // String apiKey;

    @PostConstruct
    private void init() {
        apiKey = "f042682f5295b6696410";
        COUNTRY_CURRENCY_MAP = getCurrencies();
        COUNTRY_CURRENCY_MAP_KEYSET_SORTED = new ArrayList<String>(COUNTRY_CURRENCY_MAP.keySet());
        Collections.sort(COUNTRY_CURRENCY_MAP_KEYSET_SORTED);
    }

    private Map<String, Currency> getCurrencies() {

        String currencyURL = UriComponentsBuilder
            .fromUriString(baseURL + "countries")
            .queryParam("apiKey", apiKey)
            .toUriString();

            RestTemplate template = new RestTemplate();
            RequestEntity<Void> req = RequestEntity
                .get(currencyURL)
                .accept(MediaType.APPLICATION_JSON)
                .build();

            ResponseEntity<String> resp = template.exchange(req, String.class);

            Map<String, Currency> currencyMap = new HashMap<>();

            try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
                JsonReader jsonResp = Json.createReader(is);
                JsonObject results = jsonResp.readObject().getJsonObject("results");
                for (String key : results.keySet()) {
                    JsonObject countryData = results.getJsonObject(key); // getting the value of each key as countryData
                    Currency countryCurr = Currency.create(countryData); // creating a currency for each country
                    String countryId = countryData.getString("currencyId"); // getting the string for key in map
                    currencyMap.put(countryId, countryCurr);
                    // System.out.println(countryId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return currencyMap;

        }

        public Optional<Conversion> convertCurrency(String from, String to, String value) {
            String fromAndTo = from + "_" + to;
            String currencyURL = UriComponentsBuilder
            .fromUriString(baseURL + "convert")
            .queryParam("q", fromAndTo)
            .queryParam("compact", "ultra")
            .queryParam("apiKey", apiKey)
            .toUriString();

            System.out.println(fromAndTo);

            RestTemplate template = new RestTemplate();
            RequestEntity<Void> req = RequestEntity
                .get(currencyURL)
                .accept(MediaType.APPLICATION_JSON)
                .build();

            ResponseEntity<String> resp = template.exchange(req, String.class);

            try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
                JsonReader reader = Json.createReader(is);
                JsonObject conversionRate = reader.readObject().asJsonObject();
                Double amountToConvert = Double.parseDouble(value);
                Conversion conversionResult = Conversion
                    .create(COUNTRY_CURRENCY_MAP.get(from),
                        COUNTRY_CURRENCY_MAP.get(to),
                        amountToConvert,
                        conversionRate.getJsonNumber(fromAndTo).doubleValue());
                return Optional.of(conversionResult);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            return Optional.empty();
        }





            // try {
            //     resp = template.getForEntity(currencyURL, String.class);
            //     City city = City.create(resp.getBody());
            //     // System.out.println("DOWNLOAD COMPLETE");
            //     return Optional.of(city);
            // } catch (Exception ex) {
            //     System.err.printf(">>> Error: ", ex.getMessage());
            //     ex.printStackTrace();
            // }
            // System.out.println("DOWNLOAD COMPLETE");
            // return Optional.empty();
            
    



}








    // private static final String baseURL = "https://free.currconv.com/api/v7/countries";

    // public List<Currency> saveCurrencies() throws Exception {

    //     String url = UriComponentsBuilder
    //         .fromUriString(baseURL)
    //         .queryParam("apiKey", "f042682f5295b6696410")
    //         .toUriString();

    //     RestTemplate template = new RestTemplate();
        
    //     ResponseEntity<String> resp = template.getForEntity(url, String.class);

    //     List<Currency> currencies = new LinkedList<>();
        
    //     try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
    //         JsonReader reader = Json.createReader(is);
    //         JsonObject object = reader.readObject();

    //         JsonObject results = object.getJsonObject("results");
    //         JsonArray resultsArr = results.asJsonArray();

    //         for (int i=0; i < resultsArr.size(); i++) {
    //             currencies.add(Currency.create(resultsArr.getJsonObject(i)));
    //         }

    //         System.out.println("OK");

    //         return currencies;
    //     }

    // }
 
    

