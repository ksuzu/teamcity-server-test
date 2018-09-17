package dto

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import java.util.ArrayList

/**
 * dto.BuildTypes
 */
@XmlAccessorType(XmlAccessType.FIELD)
class BuildTypes {
    @XmlAttribute
    private val count: Int? = null

    @XmlElement(name = "buildType")
    var buildType: List<BuildType> = ArrayList()
}

