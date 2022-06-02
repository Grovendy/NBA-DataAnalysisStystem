/**
 * 模糊查询接口
 */
package ui.util;

import java.util.ArrayList;

public interface FuzzySearch {

	/**
	 * 返回模糊查找结果
	 * @param keyword 
	 * @return 
	 */
	public ArrayList<String> getFuzzyResult(String keyword);
	
}
