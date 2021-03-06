package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.ImageIcon;


public interface StatsService extends Remote {

	/**
	 * 获得指定球员的某个赛季的常规赛/季后赛的雷达图
	 * 
	 * @param name
	 *            球员名字
	 * @param season
	 *            赛季 生涯为Career
	 * @param regular
	 *            常规赛/季后赛
	 * @return 雷达图
	 */
	public ImageIcon getPlayerRadar(String name, String season, int regular)
			throws RemoteException;

	/**
	 * 获得指定两个球员某赛季的常规赛/季后赛的雷达图
	 * 
	 * @param playerA
	 *            球员A
	 * @param playerB
	 *            球员B
	 * @param season
	 *            赛季，生涯为Career
	 * @param regular
	 *            常规赛/季后赛
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getPlayerCompareRadar(String playerA, String playerB,
			String season, int regular) throws RemoteException;

	/**
	 * 获得指定球员指定数据的常规赛/季后赛历史折线图
	 * 
	 * @param player
	 *            球员姓名
	 * @param field
	 *            指定数据
	 * @param regular
	 *            常规赛/季后赛
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getPlayerCareerLineChart(String player, int fieldNum,
			int regular) throws RemoteException;

	/**
	 * 获得指定两个球队的指定基本数据的常规赛/季后赛历史对比直方图
	 * 
	 * @param playerA
	 *            球员A姓名
	 * @param playerB
	 *            球员B姓名
	 * @param season
	 *            赛季 ，生涯为Career
	 * @param fields (范围： pts, ast, blk, stl, trb, orb, drb,tov, pf)
	 *            带比较的数据
	 * @param regular
	 *            常规赛/季后赛
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getPlayerBasicCompareBarChart(String playerA,
			String playerB, String season, List<Integer> fields, int regular)
			throws RemoteException;

	/**
	 * 获得指定两个球队的指定高阶数据的常规赛/季后赛历史对比直方图
	 * @param playerA 球员A姓名
	 * @param playerB
	 * @param season
	 * @param fields (范围： per, orb_pct, drb_pct, trb_pct, ast_pct, stl_pct, blk_pct, tov_pct, usg_pct)
	 * @param regular
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getPlayerAdvancedCompareBarChart(String playerA,
			String playerB, String season, List<Integer> fields, int regular)
			throws RemoteException;
	
	/**
	 * 获得指定两个球队指定xx率的常规赛/季后赛历史对比直方图
	 * @param playerA
	 * @param playerB
	 * @param season
	 * @param fields (范围： fg3_pct, fga_pct, ft_pct, ts_pct)
	 * @param regular
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getPlayerPctCompareBarChart(String playerA,
			String playerB, String season, List<Integer> fields, int regular)
			throws RemoteException;
	
	/**
	 * 球队某赛季六项雷达图
	 * @param team 球队缩写
	 * @param season 赛季
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getTeamRadar(String team, String season) throws RemoteException;

	/**
	 * 两只球队某赛季六项雷达图
	 * @param teamA 球队A缩写
	 * @param teamB 球队B缩写
	 * @param season 赛季
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getTeamCompareRadar(String teamA, String teamB, String season) throws RemoteException;
	
	/**
	 * 获得某场比赛的球队六项雷达图
	 * @param gameid
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getTeamCompareRadarByGameId(String gameid) throws RemoteException;
	
	/**
	 * 某球队某数据属性历史折线图
	 * @param team 球队缩写
	 * @param field 数据属性
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getTeamCareerLineChart(String team, int fieldNum) throws RemoteException;

	/**
	 * 获得两只球队某赛季基本属性对比直方图
	 * @param teamA 球队A缩写
	 * @param teamB 球队B缩写
	 * @param season 赛季
	 * @param field 对比属性(pts,ast,blk,stl,trb,drb,orb,tov,pf)
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getTeamBasicCompareBarChart(String teamA, String teamB, String season, List<Integer> field) throws RemoteException;

	/**
	 * 获得两只球队某赛季高阶属性对比直方图
	 * @param teamA 球队A缩写
	 * @param teamB 球队B缩写
	 * @param season 赛季
	 * @param field 对比高阶属性(orb_pct, drb_pct, off_rtg, def_rtg)
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getTeamAdvancedCompareBarChart(String teamA, String teamB, String season, List<Integer> field) throws RemoteException;

	/**
	 * 获得两只球队xx率属性对比直方图
	 * @param teamA 球队A缩写 
	 * @param teamB 球队B缩写
	 * @param season 赛季
	 * @param field 对比xx率属性(fga_pct, fg3_pct, ft_pct)
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getTeamPctCompareBarChart(String teamA, String teamB, String season, List<Integer> field) throws RemoteException;
	
	/**
	 * 获得某赛季某球员各比赛属性折线图
	 * @param name 球员姓名
	 * @param season 赛季
	 * @param list 数据属性
	 * @param regular 常规赛/季后赛
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getMatchPlayerLineChart(String name, String season, int fieldNum) throws RemoteException;
	
	/**
	 * 获得某赛季球队各属性折线图
	 * @param abbr
	 * @param season
	 * @param list
	 * @param regular
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getMatchTeamLineChart(String abbr, String season, int field) throws RemoteException;
	
	/**
	 * 获得球员对所在球队贡献比例图
	 * @param abbr
	 * @param season
	 * @return
	 * @throws RemoteException
	 */
	public ImageIcon getPlayerContribution(String abbr, String season) throws RemoteException;
	
}
