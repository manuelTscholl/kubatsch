/**
 * This file is part of KuBatsch. created on: 10.12.2010 filename: Location
 * project: KuBatsch
 */
public class Location 
{
    private String _name;

    /**
     * Initializes a new instance of the {@link Location} class.
     * @param name
     */
    public Location(String name)
    {
        super();
        _name = name;
    }
    /**
     * Gets the name.
     * @return the name
     */
    public String getName()
    {
        return _name;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return _name;
    }
}
