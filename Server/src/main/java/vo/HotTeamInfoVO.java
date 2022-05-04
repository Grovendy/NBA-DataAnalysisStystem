package vo;

import java.io.Serializable;

import util.FieldType;

/**
 * 热点球队信息类
 * 

 */
public class HotTeamInfoVO implements Serializable{
	

	private static final long serialVersionUID = 1L;

	/**
	 * 队名
	 */
	public String name;
	
	/**
	 * 缩写
	 */
	public String abbr;
	
	/**
	 * 所在联盟
	 */
	public String league;
	
	/**
	 * 赛季
	 */
	public String season;
	
	/**
	 * 热门属性
	 */
	public FieldType field;
	
	/**
	 * 热门属性值
	 */
	public String value;
}
