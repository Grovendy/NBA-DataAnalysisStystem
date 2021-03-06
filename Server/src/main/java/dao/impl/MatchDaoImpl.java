package dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.Utility;
import vo.MatchFilter;
import dao.MatchDao;
import entity.Match;
import entity.MatchInfo;
import entity.MatchPlayerAdvanced;
import entity.MatchPlayerBasic;

/**
 * MatchDao的具体实现
 * 
 */
public class MatchDaoImpl implements MatchDao {

	private SqlManager sqlManager = SqlManager.getSqlManager();	
	
	@Override
	public MatchInfo getMatchInfoByGameId(String gameid) {
		sqlManager.getConnection();
		String sql = "SELECT * FROM match_info WHERE game_id=?";
		Map<String, Object> map = sqlManager.querySimple(sql, new Object[]{gameid});
		sqlManager.releaseAll();
		return getMatchInfo(map);
	}

	@Override
	public List<List<Integer>> getSectionScoreByGameId(String gameid) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		sqlManager.getConnection();
		String sql = "SELECT home_point, guest_point FROM match_score WHERE game_id=?"
				+ " ORDER BY section ASC";
		List<Map<String, Object>> maplist = sqlManager.queryMulti(sql, new Object[]{gameid});
		for(Map<String, Object> map : maplist){
			List<Integer> pts = new ArrayList<Integer>();
			pts.add(Utility.objectToInt(map.get("home_point")));
			pts.add(Utility.objectToInt(map.get("guest_point")));
			list.add(pts);
		}
		sqlManager.releaseAll();
		return list;
	}
	
	@Override
	public List<MatchInfo> getMatchInfoByFilter(MatchFilter filter) {
		sqlManager.getConnection();
		List<MatchInfo> list = new ArrayList<MatchInfo>();
		String sql = "SELECT DISTINCT a.game_id, "
				+ "season, "
				+ "is_normal, "
				+ "date, "
				+ "location, "
				+ "home_team, "
				+ "guest_team, "
				+ "home_point, "
				+ "guest_point, "
				+ "time "
				+ " FROM match_info a, match_player_basic b "
				+ "WHERE a.game_id = b.game_id ";
		List<Object> objects = new ArrayList<Object>();
		if(filter.season!=null){
			sql += " AND season=? ";
			objects.add(filter.season);
		}
		if(filter.regular != null){
			sql += "AND is_normal=" + filter.regular; 
		}
		if(filter.team != null){
			if(filter.home == null){
				sql += " AND a.game_id LIKE '%" + filter.team + "%'";
			}else{
				if(filter.home){
				sql += " AND home_team=? ";
				}else{
					sql += " AND guest_team=? ";
				}
				objects.add(filter.team);
			}
		}
		if(filter.begin_date != null){
			sql += " AND date >= ?";
			objects.add(filter.begin_date);
		}
		if(filter.end_date != null){
			sql += " AND date <= ?";
			objects.add(filter.end_date);
		}
		if(filter.player != null){
			sql += " AND b.player_name=? ";
			if(filter.home!=null){
				if(filter.home){
					sql += " AND b.team_abbr=a.home_team ";
				}else{
					sql += " AND b.team_abbr=a.guest_team ";
				}
			}
			objects.add(filter.player);
		}
		sql += " ORDER BY date " + filter.order;
		if(filter.limit != null){
			sql +=  " LIMIT 0,"+filter.limit;
		}		
		List<Map<String, Object>> maplist = sqlManager.queryMultiByList(sql, objects);
		for(Map<String, Object> map: maplist){
			list.add(getMatchInfo(map));
		}
		sqlManager.releaseAll();
		return list;
	}
	
	@Override
	public List<MatchInfo> getRegularMatchInfoBySeason(String season) {
		sqlManager.getConnection();
		List<MatchInfo> list = new ArrayList<MatchInfo>();
		String sql = "SELECT * FROM match_info WHERE season=? and is_normal=1 ORDER BY date ";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String,Object> map: maplist){
			list.add(getMatchInfo(map));
		}
		sqlManager.releaseAll();
		return list;
	}

	@Override
	public List<MatchInfo> getPlayOffMatchInfoBySeason(String season) {
		sqlManager.getConnection();
		List<MatchInfo> list = new ArrayList<MatchInfo>();
		String sql = "SELECT * FROM match_info WHERE season=? and is_normal=0 ORDER BY date";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String,Object> map: maplist){
			list.add(getMatchInfo(map));
		}
		sqlManager.releaseAll();
		return list;
	}
	
	@Override
	public List<MatchInfo> getMatchInfoByDate(String begin, String end) {
		sqlManager.getConnection();
		List<MatchInfo> list = new ArrayList<MatchInfo>();
		String sql = "SELECT * FROM match_info WHERE date >= ? and date <= ? ORDER BY date";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{begin,end});
		for(Map<String,Object> map: maplist){
			list.add(getMatchInfo(map));
		}
		sqlManager.releaseAll();
		return list;
	}

	@Override
	public List<MatchPlayerAdvanced> getMatchPlayerAdvancedByGameIdTeam(
			String gameid, String abbr) {
		sqlManager.getConnection();
		List<MatchPlayerAdvanced> list = new ArrayList<MatchPlayerAdvanced>();
		String sql = "SELECT * FROM match_player_advanced WHERE game_id=? and team_abbr=? ";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{gameid,abbr});
		for(Map<String,Object> map: maplist){
			list.add(getMatchPlayerAdvanced(map));
		}
		sqlManager.releaseAll();
		return list;
	}

	@Override
	public List<MatchPlayerBasic> getMatchPlayerBasicByGameIdTeam(
			String gameid, String abbr) {
		sqlManager.getConnection();
		List<MatchPlayerBasic> list = new ArrayList<MatchPlayerBasic>();
		String sql = "SELECT * FROM match_player_basic WHERE game_id=? and team_abbr=? ";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{gameid,abbr});
		for(Map<String,Object> map: maplist){
			list.add(getMatchPlayerBasic(map));
		}
		sqlManager.releaseAll();
		return list;
	}

	public MatchPlayerBasic getMatchPlayerByGameIdNameAbbr(String gameid, String name, String abbr){
		sqlManager.getConnection();
		String sql = "SELECT * FROM match_player_basic WHERE game_id=? and player_name=? and team_abbr=? ";
		Map<String,Object> map = sqlManager.querySimple(sql, new Object[]{gameid,name,abbr});
		sqlManager.releaseAll();
		return getMatchPlayerBasic(map);
	}
	
	public List<MatchPlayerBasic> getGuestHomeTeamTotalBySeason(String season, boolean home){
		List<MatchPlayerBasic> list = new ArrayList<MatchPlayerBasic>();
		sqlManager.getConnection();
		String sql = "SELECT DISTINCT "
				+ "a.game_id, "
				+ "player_name, "
				+ "team_abbr, "
				+ "starter, "
				+ "minute, "
				+ "fg, "
				+ "fga, "
				+ "fga_pct, "
				+ "fg3, "
				+ "fg3a, "
				+ "fg3_pct, "
				+ "ft, "
				+ "fta, "
				+ "ft_pct, "
				+ "orb, "
				+ "drb, "
				+ "trb, "
				+ "ast, "
				+ "stl, "
				+ "blk, "
				+ "tov, "
				+ "pf, "
				+ "pts, "
				+ "plus_minus "
				+ "FROM match_player_basic as a, match_info as b "
				+ "WHERE a.game_id = b.game_id "
				+ "AND player_name='Team Totals' "
				+ "AND b.season=? ";
		if(home){
			sql += "AND a.team_abbr = b.home_team ";
		}else{
			sql += "AND a.team_abbr = b.guest_team ";
		}
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String,Object> map: maplist){
			list.add(getMatchPlayerBasic(map));
		}
		sqlManager.releaseAll();
		return list;
	}
	
	@Override
	public List<MatchPlayerBasic> getMatchPlayerBasicByPlayerName(String name,
			String season, String abbr, int regular) {
		sqlManager.getConnection();
		List<MatchPlayerBasic> list = new ArrayList<MatchPlayerBasic>();
		String sql = "SELECT "
				+ "DISTINCT "
				+ "a.game_id, "
				+ "b.season, "
				+ "b.date, "
				+ "player_name, "
				+ "team_abbr, "
				+ "starter, "
				+ "minute, "
				+ "fg, "
				+ "fga,"
				+ "fga_pct, "
				+ "fg3, "
				+ "fg3a, "
				+ "fg3_pct, "
				+ "ft, "
				+ "fta, "
				+ "ft_pct, "
				+ "orb, "
				+ "drb, "
				+ "trb, "
				+ "ast, "
				+ "stl, "
				+ "blk, "
				+ "tov, "
				+ "pf, "
				+ "pts, "
				+ "plus_minus "
				+ " FROM match_player_basic as a, match_info as b "
				+ "WHERE a.game_id = b.game_id "
				+ "AND a.player_name=? ";
		List<Object> objects = new ArrayList<Object>();
		objects.add(name);
		if(season!=null){
				sql += " AND b.season=? ";
				objects.add(season);
		}
		if(abbr!=null){
			sql += " AND a.team_abbr=?";
			objects.add(abbr);
		}
		if(regular==0||regular ==1){
			sql += " AND b.is_normal="+regular;
		}
		List<Map<String,Object>> maplist = sqlManager.queryMultiByList(sql, objects);
		for(Map<String,Object> map: maplist){
			list.add(getMatchPlayerBasic(map));
		}
		sqlManager.releaseAll();
		return list;
	}

	@Override
	public List<MatchPlayerAdvanced> getMatchPlayerAdvancedByPlayerName(
			String name, String season, String abbr, int regular) {
		sqlManager.getConnection();
		List<MatchPlayerAdvanced> list = new ArrayList<MatchPlayerAdvanced>();
		String sql = "SELECT "
				+ "DISTINCT "
				+ "a.game_id, "
				+ "b.season, "
				+ "b.date, "
				+ "player_name, "
				+ "team_abbr, "
				+ "starter, "
				+ "minute, "
				+ "ts_pct, "
				+ "efg_pct,"
				+ "fa3a_per_fga_pct, "
				+ "fta_per_fga_pct, "
				+ "orb_pct, "
				+ "drb_pct, "
				+ "trb_pct, "
				+ "ast_pct, "
				+ "stl_pct, "
				+ "tov_pct, "
				+ "blk_pct, "
				+ "usg_pct, "
				+ "off_rtg, "
				+ "def_rtg "
				+ " FROM match_player_advanced as a, match_info as b "
				+ "WHERE a.game_id=b.game_id "
				+ "AND a.player_name = ? ";
		List<Object> objects = new ArrayList<Object>();
		objects.add(name);
		if(season!=null){
				sql += "AND b.season=? ";
				objects.add(season);
		}
		if(abbr!=null){
			sql += " AND a.team_abbr=?";
			objects.add(abbr);
		}
		if(regular==0||regular ==1){
			sql += " AND b.is_normal="+regular;
		}
		List<Map<String,Object>> maplist = sqlManager.queryMultiByList(sql, objects);
		for(Map<String,Object> map: maplist){
			list.add(getMatchPlayerAdvanced(map));
		}
		sqlManager.releaseAll();
		return list;
	}

	@Override
	public void insertMatch(List<Match> list) {
		System.out.println("Insert Match: " + list.size());
		
		if(list.size()==0)	return;
		
		sqlManager.getConnection();
		
		for(Match match: list){
						
			List<Object> infoObjects = new ArrayList<Object>();
			List<Object> scoreObjects = new ArrayList<Object>();
			List<Object> basicObjects = new ArrayList<Object>();
			List<Object> advancedObjects = new ArrayList<Object>();
			
			String sqlInfo = "INSERT INTO match_info (" +
	                "game_id," 		+ "season," +
	                "is_normal," 	+ "date," +
	                "location," 	+ "home_team," +
	                "guest_team," 	+ "home_point," +
	                "guest_point," 	+ "time" + 
	                ") VALUES ";
			
			String sqlScore = "INSERT INTO match_score (" +
					"game_id," 		+"section," +
	                "home_point," 	+"guest_point" +
					") VALUES ";
			
			String sqlBasic = "INSERT INTO match_player_basic (" +
					"game_id," 		+ "player_name," +
	                "team_abbr," 	+ "starter," +
	                "minute," 		+ "fg," +
	                "fga," 			+ "fga_pct," +
	                "fg3," 			+ "fg3a," +
	                "fg3_pct," 		+ "ft," +
	                "fta," 			+ "ft_pct," +
	                "orb," 			+ "drb," +
	                "trb," 			+ "ast," +
	                "stl," 			+ "blk," +
	                "tov,"			+ "pf," +
	                "pts," 			+ "plus_minus" +
					") VALUES";
			
			String sqlAdvanced = "INSERT INTO match_player_advanced (" +
	                "game_id," 		+ "player_name," +
	                "team_abbr," 	+ "starter," +
	                "minute," 		+ "ts_pct," +
	                "efg_pct,"		+ "fa3a_per_fga_pct," +
	                "fta_per_fga_pct," + 
	                "orb_pct," 		+ "drb_pct," + 
	                "trb_pct," 		+ "ast_pct," +
	                "stl_pct," 		+ "tov_pct," + 
	                "blk_pct," 		+ "usg_pct," + 
	                "off_rtg," 		+ "def_rtg" +
					") VALUES";
			
			//处理MatchInfo
			infoObjects.add(match.getGame_id());
			infoObjects.add(match.getSeason());
			infoObjects.add(match.isNormal());
			infoObjects.add(match.getDate());
			infoObjects.add(match.getLocation());
			infoObjects.add(match.getHome_team());
			infoObjects.add(match.getGuest_team());
			infoObjects.add(match.getHome_point());
			infoObjects.add(match.getGuest_point());
			infoObjects.add(match.getTime());
			
			sqlInfo = sqlManager.appendSQL(sqlInfo, 10);
			
			//处理MatchScore
            for (int i = 0; i < match.getHome_pts().size(); i++) {
                scoreObjects.add(match.getGame_id());
                scoreObjects.add(i);
                scoreObjects.add(match.getHome_pts().get(i));
                scoreObjects.add(match.getGuest_pts().get(i));
                
                sqlScore = sqlManager.appendSQL(sqlScore, 4);
            }
            
            //处理MatchPlayerBasic
            for(MatchPlayerBasic b: match.getHome_basic_list()){
            	basicObjects.add(b.getGame_id());
            	basicObjects.add(b.getPlayer_name());
            	basicObjects.add(b.getTeam_abbr());
            	basicObjects.add(b.getStarter());
            	basicObjects.add(b.getMinute());
            	basicObjects.add(b.getFg());
            	basicObjects.add(b.getFga());
            	basicObjects.add(b.getFga_pct());
            	basicObjects.add(b.getFg3());
            	basicObjects.add(b.getFg3a());
            	basicObjects.add(b.getFg3_pct());
            	basicObjects.add(b.getFt());
            	basicObjects.add(b.getFta());
            	basicObjects.add(b.getFt_pct());
            	basicObjects.add(b.getOrb());
            	basicObjects.add(b.getDrb());
            	basicObjects.add(b.getTrb());
            	basicObjects.add(b.getAst());
            	basicObjects.add(b.getStl());
            	basicObjects.add(b.getBlk());
            	basicObjects.add(b.getTov());
            	basicObjects.add(b.getPf());
            	basicObjects.add(b.getPts());
            	basicObjects.add(b.getPlus_minus());
            	
            	sqlBasic = sqlManager.appendSQL(sqlBasic, 24);
            }
            for(MatchPlayerBasic b: match.getGuest_basic_list()){
            	basicObjects.add(b.getGame_id());
            	basicObjects.add(b.getPlayer_name());
            	basicObjects.add(b.getTeam_abbr());
            	basicObjects.add(b.getStarter());
            	basicObjects.add(b.getMinute());
            	basicObjects.add(b.getFg());
            	basicObjects.add(b.getFga());
            	basicObjects.add(b.getFga_pct());
            	basicObjects.add(b.getFg3());
            	basicObjects.add(b.getFg3a());
            	basicObjects.add(b.getFg3_pct());
            	basicObjects.add(b.getFt());
            	basicObjects.add(b.getFta());
            	basicObjects.add(b.getFt_pct());
            	basicObjects.add(b.getOrb());
            	basicObjects.add(b.getDrb());
            	basicObjects.add(b.getTrb());
            	basicObjects.add(b.getAst());
            	basicObjects.add(b.getStl());
            	basicObjects.add(b.getBlk());
            	basicObjects.add(b.getTov());
            	basicObjects.add(b.getPf());
            	basicObjects.add(b.getPts());
            	basicObjects.add(b.getPlus_minus());
            	
            	sqlBasic = sqlManager.appendSQL(sqlBasic, 24);
            }
            //处理MatchPlayerAdvanced
            for(MatchPlayerAdvanced a: match.getHome_advanced_list()){
            	advancedObjects.add(a.getGame_id());
            	advancedObjects.add(a.getPlayer_name());
            	advancedObjects.add(a.getTeam_abbr());
            	advancedObjects.add(a.getStarter());
            	advancedObjects.add(a.getMinute());
            	advancedObjects.add(a.getTs_pct());
            	advancedObjects.add(a.getEfg_pct());
            	advancedObjects.add(a.getFa3a_per_fga_pct());
            	advancedObjects.add(a.getFta_per_fga_pct());
            	advancedObjects.add(a.getOrb_pct());
            	advancedObjects.add(a.getDrb_pct());
            	advancedObjects.add(a.getTrb_pct());
            	advancedObjects.add(a.getAst_pct());
            	advancedObjects.add(a.getStl_pct());
            	advancedObjects.add(a.getTov_pct());
            	advancedObjects.add(a.getBlk_pct());
            	advancedObjects.add(a.getUsg_pct());
            	advancedObjects.add(a.getOff_rtg());
            	advancedObjects.add(a.getDef_rtg());
            	
            	sqlAdvanced = sqlManager.appendSQL(sqlAdvanced, 19);
            }
            for(MatchPlayerAdvanced a: match.getGuest_advanced_list()){
            	advancedObjects.add(a.getGame_id());
            	advancedObjects.add(a.getPlayer_name());
            	advancedObjects.add(a.getTeam_abbr());
            	advancedObjects.add(a.getStarter());
            	advancedObjects.add(a.getMinute());
            	advancedObjects.add(a.getTs_pct());
            	advancedObjects.add(a.getEfg_pct());
            	advancedObjects.add(a.getFa3a_per_fga_pct());
            	advancedObjects.add(a.getFta_per_fga_pct());
            	advancedObjects.add(a.getOrb_pct());
            	advancedObjects.add(a.getDrb_pct());
            	advancedObjects.add(a.getTrb_pct());
            	advancedObjects.add(a.getAst_pct());
            	advancedObjects.add(a.getStl_pct());
            	advancedObjects.add(a.getTov_pct());
            	advancedObjects.add(a.getBlk_pct());
            	advancedObjects.add(a.getUsg_pct());
            	advancedObjects.add(a.getOff_rtg());
            	advancedObjects.add(a.getDef_rtg());
            	
            	sqlAdvanced = sqlManager.appendSQL(sqlAdvanced, 19);
            }
            
            sqlInfo = sqlManager.fillSQL(sqlInfo);
            sqlScore = sqlManager.fillSQL(sqlScore);
            sqlBasic = sqlManager.fillSQL(sqlBasic);
            sqlAdvanced = sqlManager.fillSQL(sqlAdvanced);
            if(infoObjects.size()>0)
                sqlManager.executeUpdateByList(sqlInfo, infoObjects);
            if(scoreObjects.size()>0)
                sqlManager.executeUpdateByList(sqlScore, scoreObjects);
            if(basicObjects.size()>0)
                sqlManager.executeUpdateByList(sqlBasic, basicObjects);
            if(advancedObjects.size()>0)
            	sqlManager.executeUpdateByList(sqlAdvanced, advancedObjects);
		}
		
		sqlManager.releaseConnection();
	}
    

	private MatchInfo getMatchInfo(Map<String, Object> map) {
		MatchInfo info = new MatchInfo();
		if(map.get("game_id")==null){
			return null;
		}
		info.setGame_id(map.get("game_id").toString());
		info.setSeason(map.get("season").toString());
		info.setIs_normal(Utility.objectToInt(map.get("is_normal")));
		info.setDate(map.get("date").toString());
		info.setLocation(map.get("location").toString());
		info.setHome_team(map.get("home_team").toString());
		info.setGuest_team(map.get("guest_team").toString());
		info.setHome_point(Utility.objectToInt(map.get("home_point")));
		info.setGuest_point(Utility.objectToInt(map.get("guest_point")));
		info.setTime(map.get("time").toString());
		return info;
	}

	private MatchPlayerAdvanced getMatchPlayerAdvanced(Map<String, Object> map) {
		MatchPlayerAdvanced mpa = new MatchPlayerAdvanced();
		if(map.get("game_id")==null){
			return null;
		}
		mpa.setGame_id(map.get("game_id").toString());
		if(map.get("season")!=null)
			mpa.setSeason(map.get("season").toString());
		if(map.get("date")!=null)
			mpa.setDate(map.get("date").toString());
		mpa.setPlayer_name(map.get("player_name").toString());
		mpa.setTeam_abbr(map.get("team_abbr").toString());
		mpa.setStarter(map.get("starter").toString());
		mpa.setMinute(Utility.objectToDouble(map.get("minute")));
		mpa.setTs_pct(Utility.objectToDouble(map.get("ts_pct")));
		mpa.setEfg_pct(Utility.objectToDouble(map.get("efg_pct")));
		mpa.setFa3a_per_fga_pct(Utility.objectToDouble(map.get("fa3a_per_fga_pct")));
		mpa.setFta_per_fga_pct(Utility.objectToDouble(map.get("fta_per_fga_pct")));
		mpa.setOrb_pct(Utility.objectToDouble(map.get("orb_pct")));
		mpa.setDrb_pct(Utility.objectToDouble(map.get("drb_pct")));
		mpa.setTrb_pct(Utility.objectToDouble(map.get("drb_pct")));
		mpa.setAst_pct(Utility.objectToDouble(map.get("ast_pct")));
		mpa.setStl_pct(Utility.objectToDouble(map.get("stl_pct")));
		mpa.setTov_pct(Utility.objectToDouble(map.get("tov_pct")));
		mpa.setBlk_pct(Utility.objectToDouble(map.get("blk_pct")));
		mpa.setUsg_pct(Utility.objectToDouble(map.get("usg_pct")));
		mpa.setOff_rtg(Utility.objectToDouble(map.get("off_rtg")));
		mpa.setDef_rtg(Utility.objectToDouble(map.get("def_rtg")));
		return mpa;
	}

	private MatchPlayerBasic getMatchPlayerBasic(Map<String, Object> map) {
		MatchPlayerBasic mpb = new MatchPlayerBasic();
		if(map.get("game_id")==null){
			return null;
		}
		mpb.setGame_id(map.get("game_id").toString());
		if(map.get("season")!=null)
			mpb.setSeason(map.get("season").toString());
		if(map.get("date")!=null)
			mpb.setDate(map.get("date").toString());
		mpb.setPlayer_name(map.get("player_name").toString());
		mpb.setTeam_abbr(map.get("team_abbr").toString());
		mpb.setStarter(map.get("starter").toString());
		mpb.setMinute(Utility.objectToDouble(map.get("minute")));
		mpb.setFg(Utility.objectToInt(map.get("fg")));
		mpb.setFga(Utility.objectToInt(map.get("fga")));
		mpb.setFga_pct(Utility.objectToDouble(map.get("fga_pct")));
		mpb.setFg3(Utility.objectToInt(map.get("fg3")));
		mpb.setFg3a(Utility.objectToInt(map.get("fg3a")));	
		mpb.setFg3_pct(Utility.objectToDouble(map.get("fg3_pct")));
		mpb.setFt(Utility.objectToInt(map.get("ft")));
		mpb.setFta(Utility.objectToInt(map.get("fta")));
		mpb.setFt_pct(Utility.objectToDouble(map.get("ft_pct")));
		mpb.setOrb(Utility.objectToInt(map.get("orb")));
		mpb.setDrb(Utility.objectToInt(map.get("drb")));
		mpb.setTrb(Utility.objectToInt(map.get("trb")));
		mpb.setAst(Utility.objectToInt(map.get("ast")));
		mpb.setStl(Utility.objectToInt(map.get("stl")));
		mpb.setBlk(Utility.objectToInt(map.get("blk")));
		mpb.setTov(Utility.objectToInt(map.get("tov")));
		mpb.setPf(Utility.objectToInt(map.get("pf")));
		mpb.setPts(Utility.objectToInt(map.get("pts")));
		mpb.setPlus_minus(Utility.objectToDouble(map.get("plus_minus")));
		return mpb;
	}

	
}
