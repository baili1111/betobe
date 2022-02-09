package com.sikiedu.betobe.recommend;

import com.sikiedu.betobe.domain.Video;

/**
 * @author zhu
 * @date 2021/5/8 16:59:42
 * @description
 */
public class PVideo implements Comparable{


	private Double pLike;
	private Double pDislike;

	private Video video;

	public Double getpLike() {
		return pLike;
	}

	public void setpLike(Double pLike) {
		this.pLike = pLike;
	}

	public Double getpDislike() {
		return pDislike;
	}

	public void setpDislike(Double pDislike) {
		this.pDislike = pDislike;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	@Override
	public int compareTo(Object o) {
		return (this.pLike - ((PVideo)o).pLike) > 0 ? -1:1;
	}



}
