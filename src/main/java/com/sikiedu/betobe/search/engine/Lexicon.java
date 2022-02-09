package com.sikiedu.betobe.search.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author zhu
 * @date 2021/5/20 14:53:56
 * @description 词典
 */
public class Lexicon {
	/**
	 * Integer 存的是词的hashCode，
	 * InvertedIndex 存的是倒排索引（词Id，词，文档频率，倒排列表）
	 */
	public final static HashMap<Integer, InvertedIndex> lexicon;

	// 静态代码块
	static {
		lexicon = new HashMap<>();
	}

	// 获取包含某个字符串的所有文档ID
	public static List<String> getDocumentsByWord(String word) {

		// 获取该单词的hash值
		int hashCode = word.hashCode();

		// 通过hash值找到该单词的倒排索引
		InvertedIndex invertedIndex = lexicon.get(hashCode);

		// 通过倒排索引拿到倒排列表
		List<PositionInfo> positionInfo = invertedIndex.getPositionInfo();

		// 获得包含某个字符串的所有文档ID
		List<String> target = new ArrayList<>();

		for (PositionInfo info : positionInfo) {
			// 拿到ID
			target.add(info.getDocId());
		}

		return target;
	}

}
