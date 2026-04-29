package com.Alejandro.BolsaDeValores.marketdata;
import com.Alejandro.BolsaDeValores.marketdata.MarketDataDto.MarketDataResponseDto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ConectionApi {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;



    //TODO: Substituir por variáveis de ambiente ou configuração externa
    //@Value("${api key connection}")
    //private String apiKey;

    //@Value("${market-data.base-url:https://www.alphavantage.co/query}")
    //private String baseUrl;


    public MarketDataDto.MarketDataResponseDto fetchMarketData(String ticker) throws Exception {
        //i im using local data for testing, adding url later on development
        //String baseUrl = "https://www.alphavantage.co/query";

        //PARA TESTES LOCAIS
        JsonNode rootNode = objectMapper.createObjectNode();
        JsonNode quoteNode = rootNode.path("Global Quote");


        return parseResponse(ticker, quoteNode);
    }

    private MarketDataDto.MarketDataResponseDto parseResponse(String ticker, JsonNode node) {
        BigDecimal price = new BigDecimal(node.get("05. price").asText());
        BigDecimal prevClose = new BigDecimal(node.get("08. previous close").asText());
        BigDecimal dailyChange = price.subtract(prevClose);

        BigDecimal dailyChangePercent = prevClose.compareTo(BigDecimal.ZERO) != 0
                ? dailyChange.divide(prevClose, 4, java.math.RoundingMode.HALF_UP).multiply(new BigDecimal("100"))
                : BigDecimal.ZERO;

        return new MarketDataDto.MarketDataResponseDto(
                ticker.toUpperCase(),
                price,
                prevClose,
                dailyChange,
                dailyChangePercent,
                null, // high
                null, // low
                null, // volume
                LocalDateTime.now(),
                "Alpha Vantage"
        );
    }


}
