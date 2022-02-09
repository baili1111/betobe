package com.sikiedu.betobe.search.engine;

import com.chenlb.mmseg4j.example.Complex;
import com.sikiedu.betobe.domain.Video;
import com.sikiedu.betobe.service.VideoService;
import com.sikiedu.betobe.utils.TFEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zhu
 * @date 2021/5/20 16:08:25
 * @description
 */
@Component
public class SearchUtils {

	@Autowired
	private VideoService videoService;


	// 查看该词在整个搜索语句中出现了几次
	public Double getQueryTFByWordNew(String word, String split) throws IOException {
		Integer N = getWordCountInDoc(word, split);
		double MaxN = MaxTFByDoc(split);
		double a = 0.4d;
		return a + (1 - a) * 1.0 * N / MaxN;
	}

	// 得到新的TF 词频因子
	public double getTFByWordNew(String word, String docId) throws IOException {
		Video video = videoService.findVideoById(docId);
		// 获得content 去掉html
		String str = video.getContent().replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");

		// word在str出现的次数， 词在文档中出现的次数
		int N = getWordCountInDoc(word, str);

		// 在这个文档中，出现次数最多的那个单词， 他出现的次数
		double MaxN = MaxTFByDoc(str);

		// a + (1 - a) * N / Max(N)
		// N 一个单词在文档中出现的次数

		// Max(N) 表示文档中所有单词中出现最多的那个单词所对应的词频数目

		double a = 0.4;

		return a + (1 - a) * 1.0 * N / MaxN;
	}

	// 在这个文档中，出现次数最多的那个单词， 他出现的次数
	private double MaxTFByDoc(String str) throws IOException {

		// 分词
		Complex segW = new Complex();
		String words = segW.segWords(str, "|");
		String[] split = words.split("\\|");

		List<TFEntry<String, Double>> list = new ArrayList<>();

		for (int i = 0; i < split.length; i++) {
			//TFEntry<String, Double> temp = new TFEntry<String, Double>(split[i], 1d);
			TFEntry<String, Double> temp = new TFEntry<>(split[i], new Double(1));
			if (!list.contains(temp)) {
				list.add(temp);
			} else {

				for (int j = 0; j < list.size(); j++) {
					//if (list.get(j).getKey().equals(temp.getKey())) {
					//	list.get(j).setValue(list.get(j).getValue() + 1);
					//}

					if(list.get(j).getKey().equals(split[i])) {
						Double num = list.get(j).getValue()+1;
						list.get(j).setValue(num);
					}
				}

			}

		}

		// 找出最大值
		double max = 0d;

		for (TFEntry<String, Double> tf : list) {
			if (tf.getValue() > max) {
				max = tf.getValue();
			}
		}

		return max;
	}

	// TF 词频因子 表示一个单词在文档中出现的次数
	public Double getTFByWord(String word, String docId) throws IOException {
		Video video = videoService.findVideoById(docId);

		// 获得content
		String str = video.getContent().replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");

		// word在str出现的次数
		Integer countInDoc = getWordCountInDoc(word, str);


		return Math.log(countInDoc) + 1;
	}

	// 统计该单词word 在文本 str中出现的次数
	private Integer getWordCountInDoc(String word, String str) throws IOException {
		int count = 0;

		// 分词
		Complex segW = new Complex();
		String words = segW.segWords(str, "|");
		String[] split = words.split("\\|");

		for (String s : split) {
			if (word.equals(s)) {
				count++;
			}
		}

		return count;
	}

	// IDF 单词带有的信息含量，词典不变，IDF就是定值
	public Double getIDFByWord(String word) {

		// N 文档集合中的所有文档
		List<Video> videos = videoService.findAllVideo();
		int n = videos.size();

		// nk 有多少个文档出现过单词k（word）文档频率
		int hashCode = word.hashCode();
		Integer nk = Lexicon.lexicon.get(hashCode).getDocFrequency();

		return Math.log(n/nk);
	}




	// 得到搜索的字典
	public void getLexicon(Video video) throws IOException {

		// 拿到文本
		//System.out.println(video.getContent());

		// 去除html
		String str = video.getContent().replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");

		// 分词
		Complex segW = new Complex();
		String words = segW.segWords(str, "|");
		String[] split = words.split("\\|");

		// 遍历分词的结果
		for (int i = 0; i < split.length; i++) {
			InvertedIndex invertedIndex = Lexicon.lexicon.get(split[i].hashCode());
			// 判断词是否出现过
			if (invertedIndex != null && invertedIndex.getWord().equals(split[i])) {

				// 遍历倒排列表，确定是哪种情况
				List<PositionInfo> positionInfo = invertedIndex.getPositionInfo();
				boolean isAdd = false;
				for (int j = 0; j < positionInfo.size(); j++){
					if (positionInfo.get(j).getDocId().equals(video.getId().toString())) {
						isAdd = true;
						// 相同文档的词出现第二（n）次，维护之前添加的信息

						// 在本文档中出现的次数+1
						positionInfo.get(j).setWordFrequency(positionInfo.get(j).getWordFrequency() + 1);

						// 在本文档中新增出现的位置信息
						positionInfo.get(j).getPositionList().add(i);

						/*// 文档频率（词在全部文档中出现的频率）
						invertedIndex.setDocFrequency(invertedIndex.getDocFrequency() + 1);*/

						//isAdd = true;
						//PositionInfo info = positionInfo.get(j);
						//Integer num = info.getWordFrequency();
						//info.setWordFrequency(num + 1);
						//info.getPositionList().add(i);
						break;
					}
				}

				// 如果该单词已经在词典不存在
				if(!isAdd){

					// 不同文档中出现的词，直接添加即可

					// 该单词在这篇文档中出现的位置
					ArrayList<Integer> position = new ArrayList<>();
					position.add(i);

					// 倒排列表（该单词在哪篇文档中出现过、该单词在这篇文档中出现的频率、该单词在这篇文档中出现的位置）
					PositionInfo info = new PositionInfo(video.getId().toString(), 1, position);
					positionInfo.add(info);

				}

				// 文档频率（词在全部文档中出现的频率）
				invertedIndex.setDocFrequency(invertedIndex.getDocFrequency() + 1);

			} else {
				// 词不存在，直接添加位置信息

				// 该单词在这篇文档中出现的位置
				ArrayList<Integer> positionList = new ArrayList<>();
				positionList.add(i);

				// 倒排列表（该单词在哪篇文档中出现过、该单词在这篇文档中出现的频率、该单词在这篇文档中出现的位置）
				ArrayList<PositionInfo> list = new ArrayList<>();
				list.add(new PositionInfo(video.getId().toString(),1, positionList));

				// 倒排索引（词Id，词，文档频率，倒排列表）
				InvertedIndex index = new InvertedIndex(
						UUID.randomUUID().toString(),
						split[i],
						1,
						list);

				Lexicon.lexicon.put(split[i].hashCode(), index);

			}
		}


	}

}
