package com.sikiedu.betobe.controller;

import com.sikiedu.betobe.domain.Barrage;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.service.BarrageService;
import com.sikiedu.betobe.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhu
 * @date 2021/5/26 15:35:11
 * @description
 */
@Controller
public class BarrageController {

	@Autowired
	private BarrageService barrageService;

	@Autowired
	private VideoService videoService;

	@RequestMapping("/addBarrage")
	@ResponseBody
	public String addBarrage(Barrage barrage, String barrageText, String videoId) {
		barrage.setBarrage(barrageText);

		Video video = videoService.findVideoById(videoId);
		barrage.setVideo(video);

		barrageService.saveBarrage(barrage);


		return "{\"success\":" + true + "}";
	}

}
