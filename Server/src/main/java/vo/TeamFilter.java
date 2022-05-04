package vo;

import java.io.Serializable;

/**
 * 球队数据查看
 *
 */
public class TeamFilter implements Serializable {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 赛季
	 */
	public String season;
	
	/**
	 * 分区
	 */
	public String division;
	
	/**
	 * 联盟
	 */
	public String league;

}
