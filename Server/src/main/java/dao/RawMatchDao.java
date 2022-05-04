package dao;

import java.util.List;

import entity.Match;

/**
 * 原始Match数据解析接口
 * 
 */
public interface RawMatchDao {

	/**
	 * 解析Match文件
	 */
	public List<Match> getAllMatch();
	
}
