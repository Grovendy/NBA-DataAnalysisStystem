package dao.impl;

import java.awt.MediaTracker;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import util.FieldType;
import util.Utility;
import vo.TeamFilter;
import dao.TeamDao;
import entity.HotTeamInfo;
import entity.OpponentStatsPerGame;
import entity.OpponentStatsTotal;
import entity.TeamInfo;
import entity.TeamStatsAdvanced;
import entity.TeamStatsPerGame;
import entity.TeamStatsTotal;

/**
 * TeamDao的具体实现
 *
 */
public class TeamDaoImpl implements TeamDao {

	private SqlManager sqlManager = SqlManager.getSqlManager();	
	
	@Override
	public List<ImageIcon> getAllTeamLogo() {
		List<ImageIcon> list = new ArrayList<ImageIcon>();
		String path = FileManager.DATA_PATH + "/teams/logo/";
		File f = new File(path);
		String[] logos = f.list();
		for(String team: logos){
			ImageIcon lg = new ImageIcon(path+team);
			if(lg.getImageLoadStatus()==MediaTracker.ERRORED)
				continue;
			lg.setDescription(team.substring(0,3));
			list.add(lg);
		}
		return list;
	}
	
	@Override
	public ImageIcon getTeamLogoByAbbr(String abbr) {
		String path = FileManager.DATA_PATH + "/teams/logo/"+abbr+".png";
		ImageIcon icon = new ImageIcon(path);
		if(icon.getImageLoadStatus()==MediaTracker.ERRORED)
			return null;
		icon.setDescription(abbr);
		return icon;
	}
	
	@Override
	public List<TeamInfo> getAllTeamInfo() {

        sqlManager.getConnection();

        List<TeamInfo> list = new ArrayList<TeamInfo>();
        String sql = "SELECT * FROM team_info ORDER BY abbr";
        List<Map<String, Object>> maplist = sqlManager.queryMulti(sql, null);
        for (Map<String, Object> map: maplist) {
            list.add(getTeamInfo(map));
        }

        sqlManager.releaseAll();
        return list;
	}
	

	@Override
	public TeamInfo getTeamInfoByAbbr(String abbr) {
        sqlManager.getConnection();

        String sql = "SELECT * FROM team_info WHERE abbr=?";
        Map<String, Object> object = sqlManager.querySimple(sql, new Object[]{abbr});
        sqlManager.releaseAll();
        
        return getTeamInfo(object);
	}

	@Override
	public List<TeamStatsTotal> getTeamTotalBySeason(String season) {
		sqlManager.getConnection();
		
		List<TeamStatsTotal> list = new ArrayList<TeamStatsTotal>();
		String sql = "SELECT * FROM team_total WHERE season=? ORDER BY abbr";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String,Object> map: maplist){
			list.add(getTeamTotal(map));
		}
		sqlManager.releaseAll();
		
		return list;
	}

	@Override
	public List<TeamStatsPerGame> getTeamPerGameBySeason(String season) {
		sqlManager.getConnection();
		
		List<TeamStatsPerGame> list = new ArrayList<TeamStatsPerGame>();
		String sql = "SELECT * FROM team_per_game WHERE season=? ORDER BY abbr";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String,Object> map: maplist){
			list.add(getTeamPerGame(map));
		}
		sqlManager.releaseAll();
		
		return list;
	}

	@Override
	public List<OpponentStatsTotal> getTeamOppTotalBySeason(String season) {
		sqlManager.getConnection();
		
		List<OpponentStatsTotal> list = new ArrayList<OpponentStatsTotal>();
		String sql = "SELECT * FROM team_opp_total WHERE season=? ORDER BY abbr";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String,Object> map: maplist){
			list.add(getOppTotal(map));
		}
		sqlManager.releaseAll();
		
		return list;
	}

	@Override
	public List<OpponentStatsPerGame> getTeamOppPerGameBySeason(String season) {
		sqlManager.getConnection();
		
		List<OpponentStatsPerGame> list = new ArrayList<OpponentStatsPerGame>();
		String sql = "SELECT * FROM team_opp_per_game WHERE season=? ORDER BY abbr";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String,Object> map: maplist){
			list.add(getOppPerGame(map));
		}
		sqlManager.releaseAll();
		
		return list;
	}

	@Override
	public List<TeamStatsTotal> getTeamTotalByAbbr(String abbr) {
		sqlManager.getConnection();
		
		List<TeamStatsTotal> list = new ArrayList<TeamStatsTotal>();
		String sql = "SELECT * FROM team_total WHERE abbr=? ";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{abbr});
		for(Map<String,Object> map: maplist){
			list.add(getTeamTotal(map));
		}
		sqlManager.releaseAll();
		
		return list;
	}

	@Override
	public List<TeamStatsPerGame> getTeamPerGameByAbbr(String abbr) {
		sqlManager.getConnection();
		
		List<TeamStatsPerGame> list = new ArrayList<TeamStatsPerGame>();
		String sql = "SELECT * FROM team_per_game WHERE abbr=? ";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{abbr});
		for(Map<String,Object> map: maplist){
			list.add(getTeamPerGame(map));
		}
		sqlManager.releaseAll();
		
		return list;
	}

	@Override
	public List<OpponentStatsTotal> getTeamOppTotalByAbbr(String abbr) {
		sqlManager.getConnection();
		
		List<OpponentStatsTotal> list = new ArrayList<OpponentStatsTotal>();
		String sql = "SELECT * FROM team_opp_total WHERE abbr=? ";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{abbr});
		for(Map<String,Object> map: maplist){
			list.add(getOppTotal(map));
		}
		sqlManager.releaseAll();
		
		return list;
	}

	@Override
	public List<OpponentStatsPerGame> getTeamOppPerGameByAbbr(String abbr) {
		sqlManager.getConnection();
		
		List<OpponentStatsPerGame> list = new ArrayList<OpponentStatsPerGame>();
		String sql = "SELECT * FROM team_opp_per_game WHERE abbr=? ";
		List<Map<String,Object>> maplist = sqlManager.queryMulti(sql, new Object[]{abbr});
		for(Map<String,Object> map: maplist){
			list.add(getOppPerGame(map));
		}
		sqlManager.releaseAll();
		
		return list;
	}

	@Override
	public List<TeamStatsTotal> getTeamTotalByFilter(TeamFilter filter) {
		sqlManager.getConnection();
		List<TeamStatsTotal> list = new ArrayList<TeamStatsTotal>();
		String sql = "SELECT tt.abbr, " +
        		"season, " +
        		"wins," +
        		"losses," +
        		"finish," +
        		"age," +
        		"height," +
        		"weight," +
        		"num_of_game," +
        		"minute," +
        		"fg," +
        		"fga," +
        		"fga_pct," +
        		"fg3," +
        		"fg3a," +
        		"fg3_pct," +
        		"fg2," +
        		"fg2a," +
        		"fg2_pct," +
        		"ft," +
        		"fta," +
        		"ft_pct," +
        		"orb," +
        		"drb," +
        		"trb," +
        		"ast," +
        		"stl," +
        		"blk," +
        		"tov," +
        		"pf," +
        		"pts " +
        		"FROM team_total as tt, team_info as ti "
        		+ "WHERE tt.abbr = ti.abbr "
        		+ "AND season=? ";
		List<Object> objects = new ArrayList<Object>();
		objects.add(filter.season);
        if (filter.league != null) {
            if (filter.league.equalsIgnoreCase("W")) {
                sql += " AND ti.league='W' ";
            } else {
                sql += " AND ti.league='E' ";
            }
        }
		if(filter.division!=null){
			sql += " AND ti.division=? ";
			objects.add(filter.division);
		}
		sql += " ORDER BY tt.abbr";
        List<Map<String, Object>> mapList = sqlManager.queryMultiByList(sql, objects);
        for (Map<String, Object> map: mapList) {
            list.add(getTeamTotal(map));
        }
		sqlManager.releaseAll();
		return list;
	}

	@Override
	public List<TeamStatsPerGame> getTeamPerGameByFilter(TeamFilter filter) {
		sqlManager.getConnection();
		List<TeamStatsPerGame> list = new ArrayList<TeamStatsPerGame>();
		String sql = "SELECT tp.abbr, " +
        		"season, " +
        		"minute," +
        		"fg," +
        		"fga," +
        		"fga_pct," +
        		"fg3," +
        		"fg3a," +
        		"fg3_pct," +
        		"fg2," +
        		"fg2a," +
        		"fg2_pct," +
        		"ft," +
        		"fta," +
        		"ft_pct," +
        		"orb," +
        		"drb," +
        		"trb," +
        		"ast," +
        		"stl," +
        		"blk," +
        		"tov," +
        		"pf," +
        		"pts " +
        		"FROM team_per_game as tp, team_info as ti "
        		+ "WHERE tp.abbr = ti.abbr "
        		+ "AND season=? ";
		List<Object> objects = new ArrayList<Object>();
		objects.add(filter.season);
        if (filter.league != null) {
            if (filter.league.equalsIgnoreCase("W")) {
                sql += " AND ti.league='W' ";
            } else {
                sql += " AND ti.league='E' ";
            }
        }
		if(filter.division!=null){
			sql += " AND ti.division=? ";
			objects.add(filter.division);
		}
		sql += " ORDER BY tp.abbr";
        List<Map<String, Object>> mapList = sqlManager.queryMultiByList(sql, objects);
        for (Map<String, Object> map: mapList) {
            list.add(getTeamPerGame(map));
        }
		sqlManager.releaseAll();
		return list;
	}
	
	@Override
	public TeamStatsTotal getTeamTotalBySeasonAbbr(String season,
			String abbr) {
		sqlManager.getConnection();		
		String sql = "SELECT * FROM team_total WHERE season=? AND abbr=?";
		Map<String,Object> map = sqlManager.querySimple(sql, new Object[]{season,abbr});
		sqlManager.releaseAll();
		return getTeamTotal(map);
	}

	@Override
	public TeamStatsPerGame getTeamPerGameBySeasonAbbr(String season,
			String abbr) {
		sqlManager.getConnection();		
		String sql = "SELECT * FROM team_per_game WHERE season=? AND abbr=?";
		Map<String,Object> map = sqlManager.querySimple(sql, new Object[]{season,abbr});
		sqlManager.releaseAll();
		return getTeamPerGame(map);
	}

	@Override
	public OpponentStatsTotal getTeamOppTotalBySeasonAbbr(String season,
			String abbr) {
		sqlManager.getConnection();		
		String sql = "SELECT * FROM team_opp_total WHERE season=? AND abbr=?";
		Map<String,Object> map = sqlManager.querySimple(sql, new Object[]{season,abbr});
		sqlManager.releaseAll();
		return getOppTotal(map);
	}

	@Override
	public OpponentStatsPerGame getTeamOppPerGameBySeasonAbbr(
			String season, String abbr) {
		sqlManager.getConnection();		
		String sql = "SELECT * FROM team_opp_per_game WHERE season=? AND abbr=?";
		Map<String,Object> map = sqlManager.querySimple(sql, new Object[]{season,abbr});
		sqlManager.releaseAll();
		return getOppPerGame(map);
	}

	@Override
	public List<TeamStatsAdvanced> getTeamAdvancedBySeason(String season) {
		List<TeamStatsAdvanced> list = new ArrayList<TeamStatsAdvanced>();
		sqlManager.getConnection();
		String sql = "SELECT * FROM team_advanced WHERE season=?";
		List<Map<String, Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String, Object> map: maplist){
			list.add(getTeamAdvanced(map));
		}
		sqlManager.releaseAll();
		return list;
	}

	@Override
	public List<TeamStatsAdvanced> getTeamAdvancedByAbbr(String abbr) {
		List<TeamStatsAdvanced> list = new ArrayList<TeamStatsAdvanced>();
		sqlManager.getConnection();
		String sql = "SELECT * FROM team_advanced WHERE abbr=?";
		List<Map<String, Object>> maplist = sqlManager.queryMulti(sql, new Object[]{abbr});
		for(Map<String, Object> map: maplist){
			list.add(getTeamAdvanced(map));
		}
		sqlManager.releaseAll();
		return list;
	}

	@Override
	public TeamStatsAdvanced getTeamAdvancedBySeasonAbbr(String season,
			String abbr) {
		sqlManager.getConnection();		
		String sql = "SELECT * FROM team_advanced WHERE season=? AND abbr=?";
		Map<String,Object> map = sqlManager.querySimple(sql, new Object[]{season,abbr});
		sqlManager.releaseAll();
		return getTeamAdvanced(map);
	}

	@Override
	public List<TeamStatsAdvanced> getTeamAdvancedByFilter(TeamFilter filter) {
		List<TeamStatsAdvanced> list = new ArrayList<TeamStatsAdvanced>();
		sqlManager.getConnection();
		String sql = "SELECT ta.abbr, "
        		+ "season, "
        		+ "pw, "
        		+ "pl, "
        		+ "pw, "
        		+ "mov, "
        		+ "sos, "
        		+ "srs, "
        		+ "off_rtg, "
        		+ "def_rtg, "
        		+ "pace, "
        		+ "fta_per_fga_pct, "
        		+ "fg3a_per_fga_pct, "
        		+ "off_efg_pct, "
        		+ "off_tov_pct, "
        		+ "orb_pct, "
        		+ "off_ft_rate, "
        		+ "opp_efg_pct, "
        		+ "opp_tov_pct, "
        		+ "drb_pct, "
        		+ "opp_ft_rate, "
        		+ "arena, "
        		+ "attendance "
        		+ "FROM team_advanced as ta, team_info as ti "
        		+ "WHERE ta.abbr = ti.abbr "
        		+ "AND season=? ";
		List<Object> objects = new ArrayList<Object>();
		objects.add(filter.season);
        if (filter.league != null) {
            if (filter.league.equalsIgnoreCase("W")) {
                sql += " AND ti.league='W' ";
            } else {
                sql += " AND ti.league='E' ";
            }
        }
		if(filter.division!=null){
			sql += " AND ti.division=? ";
			objects.add(filter.division);
		}
		sql += " ORDER BY ta.abbr";
		List<Map<String, Object>> maplist = sqlManager.queryMultiByList(sql, objects);
		for(Map<String, Object> map: maplist){
			list.add(getTeamAdvanced(map));
		}
		sqlManager.releaseAll();
		return list;
	}
	
	@Override
	public List<HotTeamInfo> getSeasonHotTeam(String season, FieldType field, int number) {
		List<HotTeamInfo> list = new ArrayList<HotTeamInfo>();
		sqlManager.getConnection();
		String sql ="";
		if(!isFieldAdvanced(field)){
			sql += "SELECT a.name, b.abbr, league, season, "
				+ field.toString()
				+ " FROM team_info as a, team_per_game as b "
				+ " WHERE a.abbr = b.abbr AND b.season = ?";
		}else{
			sql += "SELECT a.name, b.abbr, league, season, ? "
					+ "FROM team_info as a, team_advanced as b "
					+ "WHERE a.abbr = b.abbr AND b.season = ?";
		}
		sql += "ORDER BY " + field.toString() + " DESC LIMIT 0," + number;
		List<Map<String, Object>> maplist = sqlManager.queryMulti(sql, new Object[]{season});
		for(Map<String, Object> map: maplist){
			list.add(getHotTeamInfo(map, field));
		}
		sqlManager.releaseAll();
		return list;
	}
	
	@Override
	public void insertTeamInfo(List<TeamInfo> list) {
		System.out.println("Insert Team Info : " + list.size());
		if(list.size()==0)
			return;

		Connection connection = sqlManager.getConnection();

		for(TeamInfo info: list){
			List<Object> infoObjects = new ArrayList<Object>();
			String slt = "select name from team_info where name='"+info.getName() + "'";
			try {
				ResultSet resultSet = connection.createStatement().executeQuery(slt);
				if (resultSet.next()) continue;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			String sqlInfo = "INSERT INTO team_info (" +
					"name, " 			+ 	"abbr, " +
					"buildup_time, "	+ 	"location, " +
					"league, "			+ 	"division, " +
					"record, "			+ 	"playoff, " +
					"championship"			+ 
					") VALUES";
			infoObjects.add(info.getName());
			infoObjects.add(info.getAbbr());
			infoObjects.add(info.getBuildup_time());
			infoObjects.add(info.getLocation());
			infoObjects.add(info.getLeague());
			infoObjects.add(info.getDivision());
			infoObjects.add(info.getRecord());
			infoObjects.add(info.getPlayeroff_appearance());
			infoObjects.add(info.getChampionships());
			
			sqlInfo = sqlManager.appendSQL(sqlInfo, 9);
			sqlInfo = sqlManager.fillSQL(sqlInfo);
			sqlManager.executeUpdateByList(sqlInfo, infoObjects);
		}
		sqlManager.releaseConnection();
	}

	@Override
	public void insertTeamTotal(List<TeamStatsTotal> list) {
		System.out.println("Insert Team Total : " + list.size());
		if(list.size()==0)
			return;
		
		sqlManager.getConnection();
		
		for(TeamStatsTotal tst: list){
			List<Object> totalObjects = new ArrayList<Object>();			
			String sqlTotal = "INSERT INTO team_total (" + 
					"abbr, " 			+ 	"season, " +
					"wins, "			+ 	"losses, " +
					"finish, "			+ 	"age, " +
					"height, "			+ 	"weight, " +
					"num_of_game, "		+ 	"minute, " +
					"fg, "				+	"fga," +
					"fga_pct, "			+	"fg3," +
					"fg3a, " 			+	"fg3_pct, " +
					"fg2," 				+	"fg2a, " +
					"fg2_pct, "			+ 	"ft, "	+	
					"fta, " 			+	"ft_pct, " +
					"orb, " 			+	"drb, " +
					"trb, " 			+	"ast, " +
					"stl, " 			+	"blk, " +
					"tov, " 			+	"pf, " +
					"pts " +
					") VALUES";
			totalObjects.add(tst.getAbbr());
			totalObjects.add(tst.getSeason());
			totalObjects.add(tst.getWins());
			totalObjects.add(tst.getLosses());
			totalObjects.add(tst.getFinish());
			totalObjects.add(tst.getAge());
			totalObjects.add(tst.getHeight());
			totalObjects.add(tst.getWeight());
			totalObjects.add(tst.getNumOfGame());
			totalObjects.add(tst.getMinute());
			totalObjects.add(tst.getFg());
			totalObjects.add(tst.getFga());
			totalObjects.add(tst.getFgaPct());
			totalObjects.add(tst.getFg3());
			totalObjects.add(tst.getFg3a());
			totalObjects.add(tst.getFgaPct());
			totalObjects.add(tst.getFg2());
			totalObjects.add(tst.getFg2a());
			totalObjects.add(tst.getFg2Pct());
			totalObjects.add(tst.getFt());
			totalObjects.add(tst.getFta());
			totalObjects.add(tst.getFtPct());
			totalObjects.add(tst.getOrb());
			totalObjects.add(tst.getDrb());
			totalObjects.add(tst.getTrb());
			totalObjects.add(tst.getAst());
			totalObjects.add(tst.getStl());
			totalObjects.add(tst.getBlk());
			totalObjects.add(tst.getTov());
			totalObjects.add(tst.getPf());
			totalObjects.add(tst.getPts());
			
			sqlTotal = sqlManager.appendSQL(sqlTotal, 31);
			sqlTotal = sqlManager.fillSQL(sqlTotal);
			sqlManager.executeUpdateByList(sqlTotal, totalObjects);
		}
		sqlManager.releaseConnection();
	}

	@Override
	public void insertTeamPerGame(List<TeamStatsPerGame> list) {
		System.out.println("Insert Team PerGame : " + list.size());
		if(list.size()==0)
			return;
		
		sqlManager.getConnection();
		
		for(TeamStatsPerGame tsp: list){
			List<Object> pergameObjects = new ArrayList<Object>();		
			String sqlPergame = "INSERT INTO team_per_game (" + 
					"abbr, " 		+ 	"season, " +
					"minute, "		+	"fg, "	+	
					"fga, " 		+	"fga_pct, "	+	
					"fg3, " 		+	"fg3a, " +	
					"fg3_pct, " 	+	"fg2, " +	
					"fg2a, " 		+	"fg2_pct, " 	+ 	
					"ft, "			+	"fta, " +
					"ft_pct, "		+	"orb, " +	
					"drb, " 		+	"trb, " +	
					"ast, " 		+	"stl, " +	
					"blk, " 		+	"tov, " +	
					"pf, " 			+	"pts " +
					") VALUES";
			pergameObjects.add(tsp.getAbbr());
			pergameObjects.add(tsp.getSeason());
			pergameObjects.add(tsp.getMinute());
			pergameObjects.add(tsp.getFg());
			pergameObjects.add(tsp.getFga());
			pergameObjects.add(tsp.getFga_pct());
			pergameObjects.add(tsp.getFg3());
			pergameObjects.add(tsp.getFg3a());
			pergameObjects.add(tsp.getFg3_pct());
			pergameObjects.add(tsp.getFg2());
			pergameObjects.add(tsp.getFg2a());
			pergameObjects.add(tsp.getFg2_pct());
			pergameObjects.add(tsp.getFt());
			pergameObjects.add(tsp.getFta());
			pergameObjects.add(tsp.getFt_pct());
			pergameObjects.add(tsp.getOrb());
			pergameObjects.add(tsp.getDrb());
			pergameObjects.add(tsp.getTrb());
			pergameObjects.add(tsp.getAst());
			pergameObjects.add(tsp.getStl());
			pergameObjects.add(tsp.getBlk());
			pergameObjects.add(tsp.getTov());
			pergameObjects.add(tsp.getPf());
			pergameObjects.add(tsp.getPts());
			sqlPergame = sqlManager.appendSQL(sqlPergame, 24);
			sqlPergame = sqlManager.fillSQL(sqlPergame);
			sqlManager.executeUpdateByList(sqlPergame, pergameObjects);
		}
		sqlManager.releaseConnection();
	}

	@Override
	public void insertTeamAdvanced(List<TeamStatsAdvanced> list) {
		System.out.println("Insert Team Advanced : " + list.size());
		if(list.size()==0)
			return;
		
		sqlManager.getConnection();		
		for(TeamStatsAdvanced tadv: list){
			List<Object> advObjects = new ArrayList<Object>();		
			String sqlAdv = "INSERT INTO team_advanced (" + 
					"abbr, " 				+ 	"season, " +
					"pw, "					+	"pl, " 	+	
					"mov, "					+	"sos, " +	
					"srs, " 				+	"off_rtg, " 	+	
					"def_rtg, " 			+	"pace," +	
					"fta_per_fga_pct, " 	+ 	"fg3a_per_fga_pct, "	+	
					"off_efg_pct, " 		+	"off_tov_pct, "	+	
					"orb_pct, " 			+	"off_ft_rate, " 	+	
					"opp_efg_pct, " 		+	"opp_tov_pct, " +	
					"drb_pct, " 			+	"opp_ft_rate, " 	+	
					"arena, "				+	"attendance" +	
					") VALUES";
			advObjects.add(tadv.getAbbr());
			advObjects.add(tadv.getSeason());
			advObjects.add(tadv.getPw());
			advObjects.add(tadv.getPl());
			advObjects.add(tadv.getMov());
			advObjects.add(tadv.getSos());
			advObjects.add(tadv.getSrs());
			advObjects.add(tadv.getOff_rtg());
			advObjects.add(tadv.getDef_rtg());
			advObjects.add(tadv.getPace());
			advObjects.add(tadv.getFta_per_fga_pct());
			advObjects.add(tadv.getFg3a_per_fga_pct());
			advObjects.add(tadv.getOff_efg_pct());
			advObjects.add(tadv.getOff_tov_pct());
			advObjects.add(tadv.getOrb_pct());
			advObjects.add(tadv.getOff_ft_rate());
			advObjects.add(tadv.getOpp_efg_pct());
			advObjects.add(tadv.getOpp_tov_pct());
			advObjects.add(tadv.getDrb_pct());
			advObjects.add(tadv.getOff_ft_rate());
			advObjects.add(tadv.getArena());
			advObjects.add(tadv.getAttendance());
			
			sqlAdv = sqlManager.appendSQL(sqlAdv, 22);
			sqlAdv = sqlManager.fillSQL(sqlAdv);
			sqlManager.executeUpdateByList(sqlAdv, advObjects);
		}
		sqlManager.releaseConnection();
	}

	@Override
	public void insertTeamOppTotal(List<OpponentStatsTotal> list) {
		System.out.println("Insert Team_Opp Total : " + list.size());
		if(list.size()==0)
			return;
		
		sqlManager.getConnection();

		for(OpponentStatsTotal opt: list){			
			List<Object> optObjects = new ArrayList<Object>();		
			String sqlOpt = "INSERT INTO team_opp_total (" + 
					"abbr, " 			+ 	"season, " +
					"num_of_game, " 	+
					"minute, "			+	"fg, "	+	
					"fga, " 			+	"fga_pct, "	+	
					"fg3, " 			+	"fg3a, " +	
					"fg3_pct, " 		+	"fg2, " +	
					"fg2a, " 			+	"fg2_pct, " 	+ 	
					"ft, "				+	"fta, " +
					"ft_pct, "			+	"orb, " +	
					"drb, " 			+	"trb, " +	
					"ast, " 			+	"stl, " +	
					"blk, " 			+	"tov, " +	
					"pf, " 				+	"pts " +
					") VALUES";
			optObjects.add(opt.getAbbr());
			optObjects.add(opt.getSeason());
			optObjects.add(opt.getNumOfGame());
			optObjects.add(opt.getMinute());
			optObjects.add(opt.getFg());
			optObjects.add(opt.getFga());
			optObjects.add(opt.getFgaPct());
			optObjects.add(opt.getFg3());
			optObjects.add(opt.getFg3a());
			optObjects.add(opt.getFg3Pct());
			optObjects.add(opt.getFg2());
			optObjects.add(opt.getFg2a());
			optObjects.add(opt.getFg2Pct());
			optObjects.add(opt.getFt());
			optObjects.add(opt.getFta());
			optObjects.add(opt.getFtPct());
			optObjects.add(opt.getOrb());
			optObjects.add(opt.getDrb());
			optObjects.add(opt.getTrb());
			optObjects.add(opt.getAst());
			optObjects.add(opt.getStl());
			optObjects.add(opt.getBlk());
			optObjects.add(opt.getTov());
			optObjects.add(opt.getPf());
			optObjects.add(opt.getPts());
			
			sqlOpt = sqlManager.appendSQL(sqlOpt, 25);
			sqlOpt = sqlManager.fillSQL(sqlOpt);
			sqlManager.executeUpdateByList(sqlOpt, optObjects);
		}
		sqlManager.releaseConnection();
	}

	@Override
	public void insertTeamOppPerGame(List<OpponentStatsPerGame> list) {
		System.out.println("Insert Team_Opp PerGame : " + list.size());
		if(list.size()==0)
			return;
		
		sqlManager.getConnection();
	
		for(OpponentStatsPerGame tst: list){
			List<Object> opgObjects = new ArrayList<Object>();	
			String sqlOpg = "INSERT INTO team_opp_per_game (" + 
					"abbr, " 		+ 	"season, " +
					"minute, "		+	"fg, "	+	
					"fga, " 			+	"fga_pct, "	+	
					"fg3, " 			+	"fg3a, " +	
					"fg3_pct, " 		+	"fg2, " +	
					"fg2a, " 		+	"fg2_pct, " 	+ 	
					"ft, "			+	"fta, " +
					"ft_pct, "		+	"orb, " +	
					"drb, " 		+	"trb, " +	
					"ast, " 		+	"stl, " +	
					"blk, " 		+	"tov, " +	
					"pf, " 			+	"pts " +
					") VALUES";
			opgObjects.add(tst.getAbbr());
			opgObjects.add(tst.getSeason());
			opgObjects.add(tst.getMinute());
			opgObjects.add(tst.getFg());
			opgObjects.add(tst.getFga());
			opgObjects.add(tst.getFga_pct());
			opgObjects.add(tst.getFg3());
			opgObjects.add(tst.getFg3a());
			opgObjects.add(tst.getFg3_pct());
			opgObjects.add(tst.getFg2());
			opgObjects.add(tst.getFg2a());
			opgObjects.add(tst.getFg2_pct());
			opgObjects.add(tst.getFt());
			opgObjects.add(tst.getFta());
			opgObjects.add(tst.getFt_pct());
			opgObjects.add(tst.getOrb());
			opgObjects.add(tst.getDrb());
			opgObjects.add(tst.getTrb());
			opgObjects.add(tst.getAst());
			opgObjects.add(tst.getStl());
			opgObjects.add(tst.getBlk());
			opgObjects.add(tst.getTov());
			opgObjects.add(tst.getPf());
			opgObjects.add(tst.getPts());
			
			sqlOpg = sqlManager.appendSQL(sqlOpg, 24);
			sqlOpg = sqlManager.fillSQL(sqlOpg);
			sqlManager.executeUpdateByList(sqlOpg, opgObjects);
		}
		sqlManager.releaseConnection();
	}

	
	
	private TeamInfo getTeamInfo(Map<String, Object> map) {
		TeamInfo info = new TeamInfo();
        if (map.get("name") == null) {
            return null;
        }
        info.setName(map.get("name").toString());
        info.setAbbr(map.get("abbr").toString());
        info.setBuildup_time(map.get("buildup_time").toString());
        info.setLocation(map.get("location").toString());
        info.setLeague(map.get("league").toString());
        info.setDivision(map.get("division").toString());
        info.setRecord(map.get("record").toString());
        info.setPlayeroff_appearance(Utility.objectToInt(map.get("playoff")));
        info.setChampionships(Utility.objectToInt(map.get("championship")));
		return info;
	}
	
	private TeamStatsTotal getTeamTotal(Map<String, Object> map) {
		TeamStatsTotal tst = new TeamStatsTotal();
		if(map.get("abbr")==null){
			return null;
		}
		tst.setAbbr(map.get("abbr").toString());
		tst.setSeason(map.get("season").toString());
		tst.setWins(Utility.objectToInt(map.get("wins")));
		tst.setLosses(Utility.objectToInt(map.get("losses")));
		tst.setFinish(Utility.objectToInt(map.get("finish")));
		tst.setAge(Utility.objectToDouble(map.get("age")));
		tst.setHeight(map.get("height").toString());
		tst.setWeight(Utility.objectToDouble(map.get("weight")));
		tst.setNumOfGame(Utility.objectToInt(map.get("num_of_game")));
		tst.setMinute(Utility.objectToInt(map.get("minute")));
		tst.setFg(Utility.objectToInt(map.get("fg")));
		tst.setFga(Utility.objectToInt(map.get("fga")));
		tst.setFgaPct(Utility.objectToDouble(map.get("fga_pct")));
		tst.setFg3(Utility.objectToInt(map.get("fg3")));
		tst.setFg3a(Utility.objectToInt(map.get("fg3a")));
		tst.setFg3Pct(Utility.objectToDouble(map.get("fg3_pct")));
		tst.setFg2(Utility.objectToInt(map.get("fg2")));
		tst.setFg2a(Utility.objectToInt(map.get("fg2a")));
		tst.setFg2Pct(Utility.objectToDouble(map.get("fg2_pct")));
		tst.setFt(Utility.objectToInt(map.get("ft")));
		tst.setFta(Utility.objectToInt(map.get("fta")));
		tst.setFtPct(Utility.objectToDouble(map.get("ft_pct")));
		tst.setOrb(Utility.objectToInt(map.get("orb")));
		tst.setDrb(Utility.objectToInt(map.get("drb")));
		tst.setTrb(Utility.objectToInt(map.get("trb")));
		tst.setAst(Utility.objectToInt(map.get("ast")));
		tst.setStl(Utility.objectToInt(map.get("stl")));
		tst.setBlk(Utility.objectToInt(map.get("blk")));
		tst.setTov(Utility.objectToInt(map.get("tov")));
		tst.setPf(Utility.objectToInt(map.get("pf")));
		tst.setPts(Utility.objectToInt(map.get("pts")));
		return tst;
	}

	private TeamStatsPerGame getTeamPerGame(Map<String, Object> map) {
		TeamStatsPerGame tsp = new TeamStatsPerGame();
		if(map.get("abbr")==null){
			return null;
		}
		tsp.setAbbr(map.get("abbr").toString());
		tsp.setSeason(map.get("season").toString());
		tsp.setMinute(Utility.objectToDouble(map.get("minute")));
		tsp.setFg(Utility.objectToDouble(map.get("fg")));
		tsp.setFga(Utility.objectToDouble(map.get("fga")));
		tsp.setFga_pct(Utility.objectToDouble(map.get("fga_pct")));
		tsp.setFg3(Utility.objectToDouble(map.get("fg3")));
		tsp.setFg3a(Utility.objectToDouble(map.get("fg3a")));
		tsp.setFg3_pct(Utility.objectToDouble(map.get("fg3_pct")));
		tsp.setFg2(Utility.objectToDouble(map.get("fg2")));
		tsp.setFg2a(Utility.objectToDouble(map.get("fg2a")));
		tsp.setFg2_pct(Utility.objectToDouble(map.get("fg2_pct")));
		tsp.setFt(Utility.objectToDouble(map.get("ft")));
		tsp.setFta(Utility.objectToDouble(map.get("fta")));
		tsp.setFt_pct(Utility.objectToDouble(map.get("ft_pct")));
		tsp.setOrb(Utility.objectToDouble(map.get("orb")));
		tsp.setDrb(Utility.objectToDouble(map.get("drb")));
		tsp.setTrb(Utility.objectToDouble(map.get("trb")));
		tsp.setAst(Utility.objectToDouble(map.get("ast")));
		tsp.setStl(Utility.objectToDouble(map.get("stl")));
		tsp.setBlk(Utility.objectToDouble(map.get("blk")));
		tsp.setTov(Utility.objectToDouble(map.get("tov")));
		tsp.setPf(Utility.objectToDouble(map.get("pf")));
		tsp.setPts(Utility.objectToDouble(map.get("pts")));
		return tsp;
	}
	
	private OpponentStatsTotal getOppTotal(Map<String, Object> map) {
		OpponentStatsTotal ost = new OpponentStatsTotal();
		if(map.get("abbr")==null){
			return null;
		}
		ost.setAbbr(map.get("abbr").toString());
		ost.setSeason(map.get("season").toString());
		ost.setMinute(Utility.objectToInt(map.get("minute")));
		ost.setFg(Utility.objectToInt(map.get("fg")));
		ost.setFga(Utility.objectToInt(map.get("fga")));
		ost.setFgaPct(Utility.objectToDouble(map.get("fga_pct")));
		ost.setFg3(Utility.objectToInt(map.get("fg3")));
		ost.setFg3a(Utility.objectToInt(map.get("fg3a")));
		ost.setFg3Pct(Utility.objectToDouble(map.get("fg3_pct")));
		ost.setFg2(Utility.objectToInt(map.get("fg2")));
		ost.setFg2a(Utility.objectToInt(map.get("fg2a")));
		ost.setFg2Pct(Utility.objectToDouble(map.get("fg2_pct")));
		ost.setFt(Utility.objectToInt(map.get("ft")));
		ost.setFta(Utility.objectToInt(map.get("fta")));
		ost.setFtPct(Utility.objectToDouble(map.get("ft_pct")));
		ost.setOrb(Utility.objectToInt(map.get("orb")));
		ost.setDrb(Utility.objectToInt(map.get("drb")));
		ost.setTrb(Utility.objectToInt(map.get("trb")));
		ost.setAst(Utility.objectToInt(map.get("ast")));
		ost.setStl(Utility.objectToInt(map.get("stl")));
		ost.setBlk(Utility.objectToInt(map.get("blk")));
		ost.setTov(Utility.objectToInt(map.get("tov")));
		ost.setPf(Utility.objectToInt(map.get("pf")));
		ost.setPts(Utility.objectToInt(map.get("pts")));
		return ost;
	}
	
	private OpponentStatsPerGame getOppPerGame(Map<String, Object> map) {
		OpponentStatsPerGame osp = new OpponentStatsPerGame();
		if(map.get("abbr")==null){
			return null;
		}
		osp.setAbbr(map.get("abbr").toString());
		osp.setSeason(map.get("season").toString());
		osp.setMinute(Utility.objectToDouble(map.get("minute")));
		osp.setFg(Utility.objectToDouble(map.get("fg")));
		osp.setFga(Utility.objectToDouble(map.get("fga")));
		osp.setFga_pct(Utility.objectToDouble(map.get("fga_pct")));
		osp.setFg3(Utility.objectToDouble(map.get("fg3")));
		osp.setFg3a(Utility.objectToDouble(map.get("fg3a")));
		osp.setFg3_pct(Utility.objectToDouble(map.get("fg3_pct")));
		osp.setFg2(Utility.objectToDouble(map.get("fg2")));
		osp.setFg2a(Utility.objectToDouble(map.get("fg2a")));
		osp.setFg2_pct(Utility.objectToDouble(map.get("fg2_pct")));
		osp.setFt(Utility.objectToDouble(map.get("ft")));
		osp.setFta(Utility.objectToDouble(map.get("fta")));
		osp.setFt_pct(Utility.objectToDouble(map.get("ft_pct")));
		osp.setOrb(Utility.objectToDouble(map.get("orb")));
		osp.setDrb(Utility.objectToDouble(map.get("drb")));
		osp.setTrb(Utility.objectToDouble(map.get("trb")));
		osp.setAst(Utility.objectToDouble(map.get("ast")));
		osp.setStl(Utility.objectToDouble(map.get("stl")));
		osp.setBlk(Utility.objectToDouble(map.get("blk")));
		osp.setTov(Utility.objectToDouble(map.get("tov")));
		osp.setPf(Utility.objectToDouble(map.get("pf")));
		osp.setPts(Utility.objectToDouble(map.get("pts")));		
		return osp;
	}

	private HotTeamInfo getHotTeamInfo(Map<String, Object> map, FieldType field) {
		if(map.get("name")==null){
			return null;
		}
		HotTeamInfo info = new HotTeamInfo();
		info.setName(map.get("name").toString());
		info.setAbbr(map.get("abbr").toString());
		info.setField(field);
		info.setLeague(map.get("league").toString());
		info.setSeason(map.get("season").toString());
		info.setValue(map.get(field.toString()).toString());
		return info;
	}

	private TeamStatsAdvanced getTeamAdvanced(Map<String, Object> map) {
		if(map.get("abbr")==null){
			return null;
		}
		TeamStatsAdvanced tsa = new TeamStatsAdvanced();
		tsa.setAbbr(map.get("abbr").toString());
		tsa.setSeason(map.get("season").toString());
		tsa.setPw(Utility.objectToDouble(map.get("pw")));
		tsa.setPl(Utility.objectToDouble(map.get("pl")));
		tsa.setMov(Utility.objectToDouble(map.get("mov")));
		tsa.setSos(Utility.objectToDouble(map.get("sos")));
		tsa.setSrs(Utility.objectToDouble(map.get("srs")));
		tsa.setOff_rtg(Utility.objectToDouble(map.get("off_rtg")));
		tsa.setDef_rtg(Utility.objectToDouble(map.get("def_rtg")));
		tsa.setPace(Utility.objectToDouble(map.get("pace")));
		tsa.setFta_per_fga_pct(Utility.objectToDouble(map.get("fta_per_fga_pct")));
		tsa.setFg3a_per_fga_pct(Utility.objectToDouble(map.get("fg3a_per_fga_pct")));
		tsa.setOff_efg_pct(Utility.objectToDouble(map.get("off_efg_pct")));
		tsa.setOff_tov_pct(Utility.objectToDouble(map.get("off_tov_pct")));
		tsa.setOrb_pct(Utility.objectToDouble(map.get("orb_pct")));
		tsa.setOff_ft_rate(Utility.objectToDouble(map.get("off_ft_rate")));
		tsa.setOpp_efg_pct(Utility.objectToDouble(map.get("opp_efg_pct")));
		tsa.setOpp_tov_pct(Utility.objectToDouble(map.get("off_tov_pct")));
		tsa.setDrb_pct(Utility.objectToDouble(map.get("drb_pct")));
		tsa.setOpp_ft_rate(Utility.objectToDouble(map.get("opp_ft_rate")));
		tsa.setArena(map.get("arena").toString());
		tsa.setAttendance(Utility.objectToInt(map.get("attendance")));
		return tsa;
	}
	
	private boolean isFieldAdvanced(FieldType field){
		switch(field){
		case DEF_RTG:
		case OFF_RTG:
		case DRB_PCT:
		case ORB_PCT:
			return true;
		default:
			return false;
		}
	}
	
}
