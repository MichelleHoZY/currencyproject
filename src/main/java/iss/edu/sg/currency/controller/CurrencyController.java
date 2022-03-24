package iss.edu.sg.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iss.edu.sg.currency.model.Conversion;
import iss.edu.sg.currency.service.CurrencyService;

@Controller
@RequestMapping(path={"/", "index.html"}, produces = MediaType.TEXT_HTML_VALUE)
public class CurrencyController {

    @Autowired
    private CurrencyService cSvc;

    @GetMapping
    public String downloadCurrency(Model model) {

        model.addAttribute("Country_Currency_Map", cSvc.COUNTRY_CURRENCY_MAP);
        model.addAttribute("Sorted_keySet", cSvc.COUNTRY_CURRENCY_MAP_KEYSET_SORTED);

        // Optional<City> city = cSvc.getCurrencies();
        // model.addAttribute("city", city);

        // Map<Currency> currencies = cSvc.getCurrencies();
        // model.addAttribute("currencies", currency);

        return "index";
        
    }

    @GetMapping("/convert")
    public String convertValue(@RequestParam String from, @RequestParam String to, @RequestParam String amount, Model model) {

        Conversion conv = new Conversion();

        conv = cSvc.convertCurrency(from, to, amount).orElse(null);

        model.addAttribute("Country_Currency_Map", cSvc.COUNTRY_CURRENCY_MAP);
        model.addAttribute("Sorted_keySet", cSvc.COUNTRY_CURRENCY_MAP_KEYSET_SORTED);
        model.addAttribute("convertedCurrency", conv);

        return "index";
    }
    
}
