package ru.clevertec.testWork.controllers.cache;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


@Controller
@RequestMapping("/cache")
public class CacheController  {
    private static String data;

    public static void setData(String data) {
        CacheController.data = data;
    }

    @GetMapping()
    @ResponseBody
    public String cache() {
        return "Result";
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    @ResponseBody
    public String getCache() {
        return  data;
    }
}
