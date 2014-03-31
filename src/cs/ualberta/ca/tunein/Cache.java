package cs.ualberta.ca.tunein;

import java.util.ArrayList;

/**
 * Model
 * Cache Class:
 * This is using a singleton design pattern.
 * This class contains a list of all comments that are
 * currently saved by the user. Adding to cache is 
 * through the CacheController.
 *
 */
public class Cache {

	private static Cache INSTANCE;
	
	public ArrayList<Comment> cache;
	public ArrayList<String> cacheIDs;
	
    // Private constructor prevents instantiation from other classes
    private Cache() {}
    
    public static Cache getInstance() {
    	if(INSTANCE == null)
    	{
    		INSTANCE = new Cache();
    	}
        return INSTANCE;
    }
    
    public static void setInstance(Cache cache)
    {
    	INSTANCE = cache;
    }
}
