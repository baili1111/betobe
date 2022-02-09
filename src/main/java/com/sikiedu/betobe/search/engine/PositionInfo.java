package com.sikiedu.betobe.search.engine;

import java.util.List;

/**
 * @author zhu
 * @date 2021/5/20 15:02:07
 * @description 倒排列表
 */
public class PositionInfo {

	// 该词出现在哪个文档
	private String docId;

	// 该词在文档中出现的次数
	private Integer wordFrequency;

	// 该词在文档中出现的位置信息
	private List<Integer> positionList;


	@Override
	public String toString() {
		return "PositionInfo{" +
				"docId='" + docId + '\'' +
				", wordFrequency=" + wordFrequency +
				", positionList=" + positionList +
				'}';
	}

	public PositionInfo(String docId, Integer wordFrequency, List<Integer> positionList) {
		this.docId = docId;
		this.wordFrequency = wordFrequency;
		this.positionList = positionList;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Integer getWordFrequency() {
		return wordFrequency;
	}

	public void setWordFrequency(Integer wordFrequency) {
		this.wordFrequency = wordFrequency;
	}

	public List<Integer> getPositionList() {
		return positionList;
	}

	public void setPositionList(List<Integer> positionList) {
		this.positionList = positionList;
	}
}
