import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import java.util.ArrayList

/**
 * BuildTypes
 */
@XmlAccessorType(XmlAccessType.FIELD)
class BuildTypes {
    @XmlAttribute
    private val count: Int? = null

    @XmlAttribute
    private val href: String? = null

    @XmlAttribute
    private val nextHref: String? = null

    @XmlAttribute
    private val prevHref: String? = null

    @XmlElement(name = "buildType")
    var buildType: List<BuildType> = ArrayList()
}

