package entity;

import lombok.*;
import util.Utility;

import java.util.Objects;

/**
 * 球队赛季总数据
 * 
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamStatsTotal {

	/**
	 * 球队（缩写）
	 */
	private String abbr;
	
	/**
	 * 赛季
	 */
	private String season;
	
	/**
	 * 胜场
	 */
	private Integer wins;
	
	/**
	 * 输场
	 */
	private Integer losses;
	
	/**
	 * regular season finish
	 */
	private Integer finish;
	
	/**
	 * 球员平均年龄
	 */
	private Double age;
	
	/**
	 * 球员平均身高
	 */
	private String height;
	
	/**
	 * 球员平均体重
	 */
	private Double weight;
	
	/**
	 * 比赛场数
	 */
	private Integer numOfGame;
	
	/**
	 * 时间
	 */
	private Integer minute;
	
	/**
	 * 投篮命中数
	 */
	private Integer fg;
	
	/**
	 * 投篮出手数
	 */
	private Integer fga;
	
	/**
	 * 投篮命中率
	 */
	private Double fgaPct;
	
	/**
	 * 三分命中数 
	 */
	private Integer fg3;
	
	/**
	 * 三分出手数
	 */
	private Integer fg3a;
	
	/**
	 * 三分命中率
	 */
	private Double fg3Pct;
	
	/**
	 * 两分命中数
	 */
	private Integer fg2;
	
	/**
	 * 两分投篮数
	 */
	private Integer fg2a;
	
	/**
	 * 两分命中率
	 */
	private Double fg2Pct;
	
	/**
	 * 罚球命中数
	 */
	private Integer ft;
	
	/**
	 * 罚球出手数
	 */
	private Integer fta;
	
	/**
	 * 罚球命中率
	 */
	private Double ftPct;
	
	/**
	 * 进攻篮板
	 */
	private Integer orb;
	
	/**
	 * 防守篮板
	 */
	private Integer drb;
	
	/**
	 * 总篮板
	 */
	private Integer trb;
	
	/**
	 * 助攻
	 */
	private Integer ast;
	
	/**
	 * 抢断
	 */
	private Integer stl;
	
	/**
	 * 盖帽
	 */
	private Integer blk;
	
	/**
	 * 失误
	 */
	private Integer tov;
	
	/**
	 * 犯规
	 */
	private Integer pf;
	
	/**
	 * 得分
	 */
	private Integer pts;

	public static TeamStatsTotal of(String[] data) {
		TeamStatsTotalBuilder builder = TeamStatsTotal.builder();
		assert data.length>=23 : "Data 的长度小于22！";
		return builder.numOfGame(Utility.stringToInt(data[0]))
				.minute(Utility.stringToInt(data[1]))
				.fg(Utility.stringToInt(data[2]))
				.fga(Utility.stringToInt(data[3]))
				.fgaPct(Utility.stringToDouble(data[4]))
				.fg3(Utility.stringToInt(data[5]))
				.fg3a(Utility.stringToInt(data[6]))
				.fg3Pct(Utility.stringToDouble(data[7]))
				.fg2(Utility.stringToInt(data[8]))
				.fg2a(Utility.stringToInt(data[9]))
				.fg2Pct(Utility.stringToDouble(data[10]))
				.ft(Utility.stringToInt(data[11]))
				.fta(Utility.stringToInt(data[12]))
				.ftPct(Utility.stringToDouble(data[13]))
				.orb(Utility.stringToInt(data[14]))
				.drb(Utility.stringToInt(data[15]))
				.trb(Utility.stringToInt(data[16]))
				.ast(Utility.stringToInt(data[17]))
				.stl(Utility.stringToInt(data[18]))
				.blk(Utility.stringToInt(data[19]))
				.tov(Utility.stringToInt(data[20]))
				.pf(Utility.stringToInt(data[21]))
				.pts(Utility.stringToInt(data[22]))
				.build();
	}
}
