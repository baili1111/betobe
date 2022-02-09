package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.search.engine.Lexicon;
import com.sikiedu.betobe.search.engine.SearchEngine;
import com.sikiedu.betobe.service.VideoService;
import com.sikiedu.betobe.utils.BaseDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhu
 * @date 2021/5/21 22:30:05
 * @description
 */

@Controller
public class SearchController {

	@Autowired
	private SearchEngine searchEngine;

	@Autowired
	private VideoService videoService;

	@Autowired
	private BaseDataUtils baseDataUtils;

	@RequestMapping("/searchByDoc")
	public String searchByDoc(String word, Model model) throws IOException {
		// 获得推荐视频
		baseDataUtils.getRecommendVideo(model);
		// 获取右侧栏数据
		baseDataUtils.getNormalRightData(model);


		List<Map.Entry<String, Double>> listEntry = searchEngine.searchByDoc(word);


		List<Video> videoList = new ArrayList<>();
		// 通过entry中的key获取videoI d，通过videoId拿到video
		for (Map.Entry<String, Double> id : listEntry) {
			Video video = videoService.findVideoById(id.getKey());
			videoList.add(video);
		}


		model.addAttribute("videoList", videoList);
		model.addAttribute("word", word);

		return "search-results.html";
	}

	@RequestMapping("/searchByWord")
	public String search(String word, Model model) throws IOException {
		// 获得推荐视频
		baseDataUtils.getRecommendVideo(model);
		// 获取右侧栏数据
		baseDataUtils.getNormalRightData(model);

		// 拿到entry list（已经排序好了）
		// 11 : 0.6931471805599453
		// 3 : 0.6931471805599453
		// 8 : 0.6931471805599453
		// 9 : 0.6931471805599453
		List<Map.Entry<String, Double>> listEntry = searchEngine.searchByWord(word);


		List<Video> videoList = new ArrayList<>();
		// 通过entry中的key获取videoI d，通过videoId拿到video
		for (Map.Entry<String, Double> id : listEntry) {
			Video video = videoService.findVideoById(id.getKey());
			videoList.add(video);
		}


		model.addAttribute("videoList", videoList);
		model.addAttribute("word", word);

		return "search-results.html";
	}

}
