package dao;

import java.util.List;

import entity.OpponentStatsPerGame;
import entity.OpponentStatsTotal;
import entity.TeamInfo;
import entity.TeamStatsAdvanced;
import entity.TeamStatsPerGame;
import entity.TeamStatsTotal;

/**
 * 原始Team数据解析抽象接口
 * 
 */
public interface RawTeamDao {
	
	public List<TeamInfo> getAllTeamInfo();

	public List<TeamStatsPerGame> getAllTeamPerGame();
	
	public List<TeamStatsTotal> getAllTeamTotal();
	
	public List<TeamStatsAdvanced> getAllTeamAdvanced();
	
	public List<OpponentStatsPerGame> getAllTeamOppPerGame();
	
	public List<OpponentStatsTotal> getAllTeamOppTotal();
}
