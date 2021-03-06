package vo;

import java.io.Serializable;

import util.FieldType;

/**
 * 热点球员信息VO类
 *
 */
public class HotPlayerInfoVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 球员姓名
	 */
	public String name;

	/**
	 * 球队缩写
	 */
	public String team;
	
	/**
	 * 位置
	 */
	public String position;
	
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
	
	public HotPlayerInfoVO(){}
}
