1. 소프트웨어 사용 사례: 날씨 검색 및 음악 검색 후 음악 재생
    1-1. 시퀀스다이아그램:

    1-2.코드내용
(url)
public class mobile { public static void main(String[] args)} String tool='검색
print.ln("무엇을 검색하시겠습니까?)
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherApp {

public static void main(String[] args) {
    // OpenWeatherMap API URL 및 키
    String apiKey = "여기_당신의_API_키_입력";
    String city = "Seoul"; // 서울의 날씨 정보를 검색
    String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric&lang=kr";

    try {
        // API 호출
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // 응답 읽기
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // JSON 파싱
        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONObject main = jsonResponse.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");

        // 날씨 정보 출력
        System.out.println("도시: " + city);
        System.out.println("온도: " + temperature + "°C");
        System.out.println("습도: " + humidity + "%");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
`import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.RateLimitException;
import com.wrapper.spotify.models.Track;
import com.wrapper.spotify.models.TrackSimplified;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.BitstreamDecoder;
import javazoom.jl.decoder.BitstreamEncoder;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class MusicSearch {

private static Api spotifyApi;

public static void main(String[] args) {
    // Spotify API 인증
    String clientId = "your-client-id";
    String clientSecret = "your-client-secret";
    spotifyApi = Api.builder()
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();

    // 검색할 음악 제목 입력
    Scanner scanner = new Scanner(System.in);
    System.out.println("검색할 음악 제목을 입력하세요: ");
    String query = scanner.nextLine();

    searchAndPlayMusic(query);
}

public static void searchAndPlayMusic(String query) {
    try {
        // 음악 검색
        List<TrackSimplified> tracks = spotifyApi.searchTracks(query).build().get();
        System.out.println("검색 결과:");
        for (int i = 0; i < tracks.size(); i++) {
            TrackSimplified track = tracks.get(i);
            System.out.println(i + 1 + ". " + track.getName() + " - " + track.getArtists());
        }

        // 사용자 선택
        Scanner scanner = new Scanner(System.in);
        System.out.println("재생할 곡 번호를 선택하세요:");
        int choice = scanner.nextInt();
        Track selectedTrack = tracks.get(choice - 1);
        String trackUrl = selectedTrack.getPreviewUrl();

        // 곡을 재생하는 부분 (MP3로 재생하기)
        playMusic(trackUrl);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public static void playMusic(String trackUrl) {
    try {
        // MP3 URL로 음악 스트리밍 재생 (실제 재생 구현은 더 복잡할 수 있음)
        InputStream audioStream = new URL(trackUrl).openStream();
        BitstreamDecoder decoder = new BitstreamDecoder();
        decoder.decode(audioStream);
        audioStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

