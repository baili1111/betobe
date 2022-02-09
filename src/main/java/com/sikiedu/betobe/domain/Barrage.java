package com.sikiedu.betobe.domain;

import javax.persistence.*;

/**
 * @author zhu
 * @date 2021/5/26 15:28:17
 * @description
 */
@Entity
public class Barrage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String barrage;

	// 这个弹幕播放的事件
	private String time;

	@ManyToOne(targetEntity = Video.class)
	@JoinColumn(name = "video_id")
	private Video video;

	protected Barrage() {
	}

	public Barrage(Long id, String barrage, String time, Video video) {
		this.id = id;
		this.barrage = barrage;
		this.time = time;
		this.video = video;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBarrage() {
		return barrage;
	}

	public void setBarrage(String barrage) {
		this.barrage = barrage;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
}
