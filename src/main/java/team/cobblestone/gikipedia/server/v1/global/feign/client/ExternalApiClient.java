package team.cobblestone.gikipedia.server.v1.global.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import team.cobblestone.gikipedia.server.v1.global.feign.dto.ExternalSearchResponse;

@FeignClient(name = "externalApiClient", url = "${feign.external-api.url:https://api.example.com}")
public interface ExternalApiClient {

    @GetMapping("/search")
    ExternalSearchResponse search(@RequestParam("q") String query);

}
