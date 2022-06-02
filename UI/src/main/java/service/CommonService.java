package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 公共数据RMI服务接口
 *
 */
public interface CommonService extends Remote{

	public List<String> getAllSeason() throws RemoteException;
	
}
