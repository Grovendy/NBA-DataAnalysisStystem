package vo;

import java.io.Serializable;

/**
 * TeamInfoVO类
 * 
 */
public class TeamInfoVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 球队名
	 */
	public String name;
	
	/**
	 * 球队缩写
	 */
	public String abbr;
	
	/**
	 * 建立时间
	 */
	public String buildup_time;
	
	/**
	 * 地点
	 */
	public String location;
	
	/**
	 * 分区
	 */
	public String division;
	
	/**
	 * 联盟
	 */
	public String league;
	
	/**
	 * record
	 */
	public String record;
	
	/**
	 * 参加季后赛次数
	 */
	public Integer playeroff_appearance;
	
	/**
	 * 夺冠次数
	 */
	public Integer championships;
	
	/**
	 * 无参构造函数2
	 */
	public TeamInfoVO(){}
	
}
