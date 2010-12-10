
/**
 * author: Daniel Kuschny (dku2375)
 * created on: 10.12.2010
 * filename: Location.java
 * project: KuBaTschQuadPuck
 */

public class Location implements Comparable<Location> {
	String _location;
	Integer _priority;

	/**
	 * Initializes a new instance of the {@link Location} class.
	 * 
	 * @param location
	 * @param priority
	 */
	public Location(String location, Integer priority) {
		super();
		_location = location;
		_priority = priority;
	}

	/**
	 * Gets the location.
	 * 
	 * @return the location
	 */
	public String getLocation() {
		return _location;
	}

	/**
	 * Sets the location.
	 * 
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		_location = location;
	}

	/**
	 * Gets the priority.
	 * 
	 * @return the priority
	 */
	public Integer getPriority() {
		return _priority;
	}

	/**
	 * Sets the priority.
	 * 
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Integer priority) {
		_priority = priority;
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Location o) {
		// TODO Auto-generated method stub
		return o.getPriority().compareTo(getPriority());
	}

}
