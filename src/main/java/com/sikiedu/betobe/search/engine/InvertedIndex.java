package com.sikiedu.betobe.search.engine;

import java.util.List;


/**
 * @author zhu
 * @date 2021/5/20 14:58:22
 * @description 倒排索引
 */
public class InvertedIndex {

	// 词id
	private String id;

	// 词
	private String word;

	// 文档频率（词在全部文档中出现的频率）
	private Integer docFrequency;

	// 倒排列表（该单词在哪篇文档中出现过、该单词在这篇文档中出现的频率、该单词在这篇文档中出现的位置）
	private List<PositionInfo> positionInfo;


	public InvertedIndex(String id, String word, Integer docFrequency, List<PositionInfo> positionInfo) {
		this.id = id;
		this.word = word;
		this.docFrequency = docFrequency;
		this.positionInfo = positionInfo;
	}

	@Override
	public String toString() {
		return "InvertedIndex{" +
				"id='" + id + '\'' +
				", word='" + word + '\'' +
				", docFrequency=" + docFrequency +
				", positionInfo=" + positionInfo +
				'}';
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getDocFrequency() {
		return docFrequency;
	}

	public void setDocFrequency(Integer docFrequency) {
		this.docFrequency = docFrequency;
	}

	public List<PositionInfo> getPositionInfo() {
		return positionInfo;
	}

	public void setPositionInfo(List<PositionInfo> positionInfo) {
		this.positionInfo = positionInfo;
	}
}
