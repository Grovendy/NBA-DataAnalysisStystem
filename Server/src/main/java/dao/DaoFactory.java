package dao;

/**
 * Dao抽象工厂
 * 
 */
public interface DaoFactory {

	public SeasonDao getSeasonDao();

	public MatchDao getMatchDao();

	public RawMatchDao getRawMatchDao();
	
	public PlayerDao getPlayerDao();
	
	public RawPlayerDao getRawPlayerDao();
	
	public TeamDao getTeamDao();
	
	public RawTeamDao getRawTeamDao();

}
