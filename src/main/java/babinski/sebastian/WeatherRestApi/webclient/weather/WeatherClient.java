package babinski.sebastian.WeatherRestApi.webclient.weather;

import babinski.sebastian.WeatherRestApi.model.WeatherDto;
import babinski.sebastian.WeatherRestApi.webclient.weather.dto.OpenWeatherWeatherDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {

    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "0a6c689903d013c33a6baf2e9f426f46";
    private RestTemplate restTemplate = new RestTemplate();

    public WeatherDto getWeatherForCity(String city) {
        OpenWeatherWeatherDto openWeatherWeatherDto = callGetMethod("weather?q={city}&appid={apiKey}&units=metric&lang=pl",
                OpenWeatherWeatherDto.class, city, API_KEY);
        return WeatherDto.builder()
                .temperature(openWeatherWeatherDto.getMain().getTemp())
                .pressure(openWeatherWeatherDto.getMain().getPressure())
                .humidity(openWeatherWeatherDto.getMain().getHumidity())
                .windSpeed(openWeatherWeatherDto.getWind().getSpeed())
                .build();
    }

    public String getForecast(double lat, double lon) {
        return callGetMethod("onecall?lat={lat}&lon={lon}&exclude=minutely, hourly&appid={apiKey}&units=metric&lang=pl",
                String.class, lat, lon, API_KEY);
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(WEATHER_URL + url,
                responseType, objects);
    }

}
