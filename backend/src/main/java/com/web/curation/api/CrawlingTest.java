package com.web.curation.api;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

public class CrawlingTest {

	@Test
	public void getRestImg() {
		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
		String url = "https://place.map.kakao.com/533327865";
		Document doc = null; // Document에는 페이지의 전체 소스가 저장된다

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
		Elements element = doc.select("meta");
		System.out.println(doc.text());
		System.out.println("============================================================");
		for(Element e : element) {
			System.out.println(e.text());
		}
//		System.out.println(element.select(query));
		// Iterator을 사용하여 하나씩 값 가져오기
				Iterator<Element> ie1 = element.select("div.details_present").iterator();
//				System.out.println(ie1.next());
//				Iterator<Element> ie1 = element.select("strong.rank").iterator();
//				Iterator<Element> ie2 = element.select("strong.title").iterator();

				while (ie1.hasNext()) {
					System.out.println(ie1.next());
				}
//				while (ie1.hasNext()) {
//					System.out.println(ie1.next().text()+"\t"+ie2.next().text());
//				}
//				
		System.out.println("============================================================");
	}


}
