package vo;

import java.io.Serializable;

/**
 * TeamOpponenttotal数据VO类
 *
 */
public class TeamOppTotalVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 球队
	 */
	public String abbr;
	
	/**
	 * 赛季
	 */
	public String season;
	
	/**
	 * 比赛场数
	 */
	public Integer num_of_game;
	
	/**
	 * 在场时间
	 */
	public Integer minute;
	
	/**
	 * 投篮命中数
	 */
	public Integer fg;
	
	/**
	 * 投篮出手数
	 */
	public Integer fga;
	
	/**
	 * 投篮命中率
	 */
	public Double fga_pct;
	
	/**
	 * 三分命中数 
	 */
	public Integer fg3;
	
	/**
	 * 三分出手数
	 */
	public Integer fg3a;
	
	/**
	 * 三分命中率
	 */
	public Double fg3_pct;
	
	/**
	 * 两分命中数
	 */
	public Integer fg2;
	
	/**
	 * 两分投篮数
	 */
	public Integer fg2a;
	
	/**
	 * 两分命中率
	 */
	public Double fg2_pct;
	
	/**
	 * 罚球命中数
	 */
	public Integer ft;
	
	/**
	 * 罚球出手数
	 */
	public Integer fta;
	
	/**
	 * 罚球命中率
	 */
	public Double ft_pct;
	
	/**
	 * 进攻篮板
	 */
	public Integer orb;
	
	/**
	 * 防守篮板
	 */
	public Integer drb;
	
	/**
	 * 总篮板
	 */
	public Integer trb;
	
	/**
	 * 助攻
	 */
	public Integer ast;
	
	/**
	 * 抢断
	 */
	public Integer stl;
	
	/**
	 * 盖帽
	 */
	public Integer blk;
	
	/**
	 * 失误
	 */
	public Integer tov;
	
	/**
	 * 犯规
	 */
	public Integer pf;
	
	/**
	 * 得分
	 */
	public Integer pts;

	/**
	 * 无参构造函数
	 */
	public TeamOppTotalVO(){}
	
}