package com.web.curation.controller.curation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.api.KakaoApi;
import com.web.curation.dao.review.RestaurantDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.review.Restaurant;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
public class CurationController {

	@Autowired
	RestaurantDao restDao;

	static String clientId = "dapi.kakao.com"; // 애플리케이션 클라이언트 아이디값"
	static String clientSecret = "KakaoAK e4cd88afa207146436293dbd18d2b89f"; // 애플리케이션 클라이언트 시크릿값"

	// 장소를 중심으로 검색
	@GetMapping("/curation")
	@ApiOperation(value = "장소를 중심으로 검색")
//	public List<Restaurant> curation(@RequestParam(required = true) final String location) {
	public Map<String, Object> curation(@RequestParam(required = true) final String location) {

		String text = new String();
		//img url 찾기위한 api
		KakaoApi kakao = new KakaoApi();

		try {
			text = URLEncoder.encode(location, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}
		// 5페이지 반복
		List<Restaurant> list = new ArrayList<Restaurant>();
		for (int page = 1; page <= 1; page++) {

			// text 동에있는 모든 음식점 가져오기
			String apiURL = " http://dapi.kakao.com/v2/local/search/keyword.json?query=" + text
					+ "&category_group_code=FD6&page=" + page + "&size=15"; // json 결과

			Map<String, String> requestHeaders = new HashMap<>();
			requestHeaders.put("Host", clientId);
			requestHeaders.put("Authorization", clientSecret);
			String responseBody = get(apiURL, requestHeaders);

			// 정보를 담을 리스트 생성
			System.out.println("responseBody");
			System.out.println(responseBody);
			// json으로 받아온 값을 식당이름/분류로 나눔
			try {
				JSONParser jsonParse = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParse.parse(responseBody);

				JSONArray store = (JSONArray) jsonObj.get("documents");
				System.out.println(store.size());
				for (int i = 0; i < store.size(); i++) {
					// JsonObject로 변환
					JSONObject temp = (JSONObject) store.get(i);

					// 음식 종류 가져오기 준비
					StringTokenizer st = new StringTokenizer((String) temp.get("category_name"), ">");
					st.nextToken();

					// 가게 이름이 DB에 있는지 확인
					System.out.println((String) temp.get("place_name"));
					Optional<Restaurant> isRest = restDao.selectRestNameByName((String) temp.get("place_name"));

					Restaurant rest = new Restaurant();

					// 가게이름이 있을경우
					if (isRest.isPresent()) {
						list.add(isRest.get());
					}
					// 가게이름이 DB에 없을 경우
					else {
						rest.setName((String) temp.get("place_name"));
						rest.setTelphone((String) temp.get("phone"));
						rest.setLocation((String) temp.get("address_name"));
						rest.setUrl((String) temp.get("place_url"));
						rest.setCategory(st.nextToken().trim());
						rest.setLike("0");
						rest.setReview("0");
						rest.setScrap("0");
						rest.setImg(kakao.searchImg((String)temp.get("place_name")));
						list.add(rest);
					}

				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

//		for (Restaurant ad : list) {
//			System.out.println(ad);
//		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}

	// 통신을 위한 함수
	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
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

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
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
