import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.List;

/**
 * BuildTypes
 */
public class BuildTypes {
  private Integer count = null;

  @XmlAttribute
  private String href = null;

  private String nextHref = null;

  private String prevHref = null;

  private List<BuildType> buildType = new ArrayList<BuildType>();

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getNextHref() {
    return nextHref;
  }

  public void setNextHref(String nextHref) {
    this.nextHref = nextHref;
  }

  public String getPrevHref() {
    return prevHref;
  }

  public void setPrevHref(String prevHref) {
    this.prevHref = prevHref;
  }

  public List<BuildType> getBuildType() {
    return buildType;
  }

  public void setBuildType(List<BuildType> buildType) {
    this.buildType = buildType;
  }
}

