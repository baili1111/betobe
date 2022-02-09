package com.sikiedu.betobe.dto;

/**
 * @author zhu
 * @date 2021/5/26 16:46:48
 * @description
 */
public class BarrageDTO {

	private Long id;
	private String barrage;
	private String time;

	public BarrageDTO(Long id, String barrage, String time) {
		this.id = id;
		this.barrage = barrage;
		this.time = time;
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
}
