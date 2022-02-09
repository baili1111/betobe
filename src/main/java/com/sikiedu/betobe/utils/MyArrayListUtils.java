package com.sikiedu.betobe.utils;

import java.util.ArrayList;

/**
 * @author zhu
 * @date 2021/5/13 12:52:02
 * @description
 */
public class MyArrayListUtils {

	public boolean isEmpty(ArrayList list) {

		if (list == null) {
			return true;
		}
		if (list.size() == 0) {
			return true;
		}
		return false;
	}

}
