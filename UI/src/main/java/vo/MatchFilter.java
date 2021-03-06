package vo;

import java.io.Serializable;

/**
 * 比赛数据查看筛选器
 *
 */
public class MatchFilter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 赛季
	 */
	public String season;
	
	/**
	 * 常规赛/季后赛  = 1/0
	 */
	public Integer regular;
	
	/**
	 * 参赛队伍
	 */
	public String team;
	
	/**
	 * 主/客队 = true/false, null=不区分
	 */
	public Boolean home;
	
	/**
	 * 开始日期
	 */
	public String begin_date;
	
	/**
	 * 结束日期
	 */
	public String end_date;
	
	/**
	 * 参赛球员
	 */
	public String player;
	
	/**
	 * date的排序,默认逆序
	 */
	public String order = "DESC";
	
	/**
	 * 个数
	 */
	public Integer limit;
	

}
