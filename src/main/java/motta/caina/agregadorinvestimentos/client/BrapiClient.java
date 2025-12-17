package motta.caina.agregadorinvestimentos.client;

import motta.caina.agregadorinvestimentos.client.dto.BrapiReponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "BrapiClient", url = "https://brapi.dev")
public interface BrapiClient {

    @GetMapping(value = "/api/quote/{stockId}")
    BrapiReponseDTO getQuote(@RequestParam("token") String token,
                             @PathVariable("stockId") String id);
}
