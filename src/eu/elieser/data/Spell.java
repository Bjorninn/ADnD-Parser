
package eu.elieser.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Spell
{
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("reversible")
    @Expose
    private Boolean reversible;
    @SerializedName("sphere")
    @Expose
    private String sphere;
    @SerializedName("range")
    @Expose
    private String range;
    @SerializedName("components")
    @Expose
    private List<String> components = null;
    @SerializedName("duration:")
    @Expose
    private String duration;
    @SerializedName("casting time")
    @Expose
    private String castingTime;
    @SerializedName("aoe")
    @Expose
    private String aoe;
    @SerializedName("save")
    @Expose
    private String save;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("component")
    @Expose
    private String component;

    public List<String> bodyLines = new ArrayList<>();

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSchool()
    {
        return school;
    }

    public void setSchool(String school)
    {
        this.school = school;
    }

    public Boolean getReversible()
    {
        return reversible;
    }

    public void setReversible(Boolean reversible)
    {
        this.reversible = reversible;
    }

    public String getSphere()
    {
        return sphere;
    }

    public void setSphere(String sphere)
    {
        this.sphere = sphere;
    }

    public String getRange()
    {
        return range;
    }

    public void setRange(String range)
    {
        this.range = range;
    }

    public List<String> getComponents()
    {
        return components;
    }

    public void setComponents(List<String> components)
    {
        this.components = components;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getCastingTime()
    {
        return castingTime;
    }

    public void setCastingTime(String castingTime)
    {
        this.castingTime = castingTime;
    }

    public String getAoe()
    {
        return aoe;
    }

    public void setAoe(String aoe)
    {
        this.aoe = aoe;
    }

    public String getSave()
    {
        return save;
    }

    public void setSave(String save)
    {
        this.save = save;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getComponent()
    {
        return component;
    }

    public void setComponent(String component)
    {
        this.component = component;
    }

    public String toString()
    {
        String s = "";
        s += "Level: " + level + "\n";
        s += "Name: " + name + "\n";
        s += "School: " + school + "\n";
        s += "Reversible: " + reversible + "\n";
        s += "Sphere: " + sphere + "\n";
        s += "Range: " + range + "\n";

        String c = "";

        for (String component1 : components)
        {
            c += component1 + ", ";
        }

        c = c.substring(0, c.length() - 2);

        s += "Components: " + c + "\n";
        s += "Duration: " + duration + "\n";
        s += "Casting Time: " + castingTime + "\n";
        s += "AoE: " + aoe + "\n";
        s += "Saving Throw: " + save + "\n";
        s += description + "\n";
        s += "Component: " + component + "\n";

        return s;

    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }
}
