package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.TestDomain;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.search.engine.Lexicon;
import com.sikiedu.betobe.search.engine.SearchEngine;
import com.sikiedu.betobe.search.engine.SearchUtils;
import com.sikiedu.betobe.service.TestService;
import com.sikiedu.betobe.service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zhu
 * @date 2021/4/3 11:00:07
 * @description
 */

@Controller
public class TestController {

	@Autowired
	private TestService testService;

	@Autowired
	private SearchUtils searchUtils;

	@Autowired
	private VideoService videoService;

	@Autowired
	private SearchEngine searchEngine;

	@RequestMapping("/test")
	public String test() {
		return "test.html";
	}

	@RequestMapping("/test4")
	public String test4() throws IOException {
		searchEngine.searchByDoc("Druid视频");

		return "redirect:/index";
	}

	@RequestMapping("/testSearch")
	public String testSearch(Model model, String str) throws IOException {

		List<Video> videos = videoService.findAllVideo();
		for (Video video : videos) {
			searchUtils.getLexicon(video);
		}

		System.out.println(Lexicon.lexicon);
		List<Map.Entry<String, Double>> listEntry = searchEngine.searchByWord(str);

		List<Video> videoList = new ArrayList<>();

		for (Map.Entry<String, Double> id : listEntry) {
			Video video = videoService.findVideoById(id.getKey());
			videoList.add(video);
		}

		model.addAttribute("videoList", videoList);

		return "search-results.html";
	}

	@RequestMapping("/submit")
	public String submit(String data) {

		TestDomain testDomain = new TestDomain(null, data);
		testService.save(testDomain);

		return "test.html";
	}

	@RequestMapping("/find")
	public ModelAndView find(Model model) {

		// 调用service查找数据
		TestDomain testDomain = testService.find();
		// 将数据放入model中
		model.addAttribute("test", testDomain);

		return new ModelAndView("test.html", "testModel", model);
	}


}
