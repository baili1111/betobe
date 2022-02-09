package com.sikiedu.betobe.search.engine;

import com.chenlb.mmseg4j.example.Complex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author zhu
 * @date 2021/5/20 16:08:44
 * @description
 */
@Component
public class SearchEngine {

	@Autowired
	private SearchUtils searchUtils;

	// 一次一文档
	public List<Map.Entry<String, Double>> searchByDoc(String query) throws IOException {

		// 分词
		Complex segW = new Complex();
		String words = segW.segWords(query, "|");
		String[] split = words.split("\\|");


		// 拿到所有包含分词中单词的文档id（不重复）
		List<String> docList = new ArrayList<>();
		for (String s : split) {
			InvertedIndex invertedIndex = Lexicon.lexicon.get(s.hashCode());
			List<PositionInfo> positionInfo = invertedIndex.getPositionInfo();
			for (PositionInfo info : positionInfo) {
				if (!docList.contains(info.getDocId())) {
					docList.add(info.getDocId());
				}
			}
		}

		docList.forEach(s -> System.out.print(s + "  "));
		System.out.println();
		// 给文档打分      i行j列  id行，词列  3 8 9 11 10
		double[][] vectorSpaceModel = new double[docList.size() + 1][split.length + 1];
		int row = 0;
		for (String id : docList) {
			// TF*IDF
			for (int i = 0; i < split.length; i++) {
				double num = 0d;
				double TF = searchUtils.getTFByWordNew(split[i], id);
				double IDF = searchUtils.getIDFByWord(split[i]);
				num = TF*IDF;
				vectorSpaceModel[row][i] = num;
			}
			row++;
		}

		// 给查询文档打分，循环列（词）
		for (int i = 0; i < split.length; i++) {
			double num = 0d;
			// 查看该词在整个搜索语句中出现了几次
			Double TF = searchUtils.getQueryTFByWordNew(split[i], query);
			Double IDF = searchUtils.getIDFByWord(split[i]);
			num = TF*IDF;
			vectorSpaceModel[row][i] = num;
		}

		for (int i = 0; i <= row; i++) {
			for (int j = 0; j < split.length; j++) {
				System.out.print(vectorSpaceModel[i][j] + "  ");
			}
			System.out.println();
		}

		// 角度的Map videoId -> 夹角cos值
		Map<String, Double> cosMap = new HashMap<>();

		// 遍历文档计算夹角 cos值
		for (int i = 0; i < row; i++) {
			// 分子
			double numerator = 0d;
			double denominator = 0d;
			// 分母
			double denominatorVideo = 0d;
			double denominatorQuery = 0d;

			for (int j = 0; j < split.length; j++) {
				// x1x2 + y1y2 分子
				numerator += vectorSpaceModel[i][j] * vectorSpaceModel[row][j];

				// x1^2 + y1^2
				denominatorVideo += vectorSpaceModel[i][j] * vectorSpaceModel[i][j];
				// x2^2 + y2^2
				denominatorQuery += vectorSpaceModel[row][j] * vectorSpaceModel[row][j];
			}

			denominator = Math.sqrt(denominatorVideo) * Math.sqrt(denominatorQuery);
			double cosValue = numerator / denominator;
			cosMap.put(docList.get(i), cosValue);
		}

		// 输出夹角
		Set<Map.Entry<String, Double>> entrySet = cosMap.entrySet();
		for (Map.Entry<String, Double> entry : entrySet) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

		List<Map.Entry<String, Double>> cosList = new ArrayList<>(cosMap.entrySet());

		// 排序
		Collections.sort(cosList, new Comparator<Map.Entry<String, Double>>() {

			@Override
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return -o1.getValue().compareTo(o2.getValue());
			}
		});
		System.out.println();
		cosList.forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));

		return cosList;
	}

	// 一次一单词
	public List<Map.Entry<String, Double>> searchByWord(String query) throws IOException {

		Complex segW = new Complex();
		String word = segW.segWords(query, "|");
		// 分词
		String[] split = word.split("\\|");

		// 创建分数 -> 优先队列
		Map<String, Double> score = new HashMap<>();

		// 遍历所有query中的词
		for (String s : split) {
			//System.out.println(s);
			// 获取包含该词的所有文档的id
			List<String> documentIds = Lexicon.getDocumentsByWord(s);

			// 遍历所有包含该词的文档
			for (String id : documentIds) {
				double num = 0d;
				// 遍历每个文档，并计算分数 TF*IDF
				Double TF = searchUtils.getTFByWord(s, id);
				Double IDF = searchUtils.getIDFByWord(s);
				num = TF * IDF;

				// 将分数放入map中
				if (score.containsKey(id)) {

					Double docScore = score.get(id);
					docScore += num;
					score.remove(id);
					score.put(id, docScore);

				} else {
					score.put(id, num);
				}
			}

		}

		// 对Map进行排序(系统自带的排序)
		// 遍历分数map
		Set<Map.Entry<String, Double>> entrySet = score.entrySet();
		for (Map.Entry<String, Double> entry : entrySet) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

		List<Map.Entry<String, Double>> list = new ArrayList<>(score.entrySet());
		// 对list进行排序

		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				// >0 o1大
				return -(o1.getValue().compareTo(o2.getValue()));
			}
		});

		return list;
	}



}
