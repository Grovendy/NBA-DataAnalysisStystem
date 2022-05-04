package dao;

import java.util.List;

import entity.PlayerInfo;
import entity.PlayerSalary;
import entity.PlayerStatsAdvanced;
import entity.PlayerStatsPerGame;
import entity.PlayerStatsTotal;

/**
 * 原始Player数据解析抽象接口
 * 
 */
public interface RawPlayerDao {

	public List<PlayerInfo> getAllPlayerInfo();
	
	public List<PlayerSalary> getAllPlayerSalary();
	
	public List<PlayerStatsPerGame> getAllPlayerPerGame();
	
	public List<PlayerStatsTotal> getAllPlayerTotal();
	
	public List<PlayerStatsAdvanced> getAllPlayerAdvanced();
	
}
