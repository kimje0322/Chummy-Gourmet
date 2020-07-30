package com.web.curation.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dao.review.RestaurantDao;
import com.web.curation.model.review.Restaurant;

@Repository
public class KakaoApi {
	
	@Autowired
	RestaurantDao restDao;

	static String clientId = "dapi.kakao.com"; //애플리케이션 클라이언트 아이디값"
	static String clientSecret = "KakaoAK e4cd88afa207146436293dbd18d2b89f"; //애플리케이션 클라이언트 시크릿값"
	
	public List<Restaurant> searchRest(String location){
		String text = new String();
		
		try {
			text = URLEncoder.encode(location, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패",e);
		}
		
		//text 동에있는 모든 음식점 가져오기
		String apiURL = " http://dapi.kakao.com/v2/local/search/keyword.json?query="+text+"&category_group_code=FD6";    // json 결과
		
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Host", clientId);
		requestHeaders.put("Authorization", clientSecret);
		String responseBody = get(apiURL,requestHeaders);
		
		//정보를 담을 리스트 생성
		List<Restaurant> list = new ArrayList<Restaurant>();
		
		//json으로 받아온 값을 식당이름/분류로 나눔
		try {
			JSONParser jsonParse = new JSONParser();
			JSONObject jsonObj = (JSONObject) jsonParse.parse(responseBody);
			
			JSONArray store =(JSONArray) jsonObj.get("documents");
			System.out.println(store.size());
			for(int i = 0 ; i<store.size();i++) {
				//JsonObject로 변환
				JSONObject temp =(JSONObject) store.get(i);
				
				//음식 종류 가져오기 준비
				StringTokenizer st = new StringTokenizer((String) temp.get("category_name"),">");
				st.nextToken();
				
				//가게 이름이 DB에 있는지 확인
				System.out.println((String) temp.get("place_name"));
//				Restaurant isRest = restDao.selectRestNameByName((String)temp.get("place_name"));
//				Optional<Restaurant> isRest = restDao.selectRestNameByName((String)temp.get("place_name"));
				Optional<Restaurant> isRest = restDao.selectRestNameByName("맛찬들");
				
				Restaurant rest = new Restaurant();
				
				//가게이름이 있을경우
//				if(rest!=null) {}
				if(isRest.isPresent()) {
					list.add(isRest.get());
				}
				//가게이름이 DB에 없을 경우
				else {
					rest.setName((String)temp.get("place_name"));
					rest.setName((String)temp.get("phone"));
					rest.setName((String)temp.get("address_name"));
					rest.setName((String)temp.get("place_url"));
					rest.setCategory(st.nextToken());
					list.add(rest);
				}
				
//				name.add((String) temp.get("place_name"));
//				restDao(name)
//				System.out.println(temp.get("place_name"));
//				name += "'"+temp.get("place_name")+"'";
//				if(!(i==store.size()-1))name += ",";
//				System.out.println(st.nextToken().trim());
//				System.out.println(temp.get("road_address_name"));
//				System.out.println("----------------------------");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println(name);
		return list;
	}
	
    public static void main(String location) {
        
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}