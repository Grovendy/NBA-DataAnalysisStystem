package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Service抽象工厂
 */
public interface ServiceFactory extends Remote{
	
	public PlayerService getPlayerService() throws RemoteException;
	
	public TeamService getTeamService() throws RemoteException;
	
	public MatchService getMatchService() throws RemoteException;
	
	public CommonService getCommonService() throws RemoteException;
	
	public StatsService getStatsService() throws RemoteException;
	
	public InferStatsService getInferStatsService() throws RemoteException;

}
