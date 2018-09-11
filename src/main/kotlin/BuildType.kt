import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class BuildType(@XmlAttribute val id: String?,
                     @XmlAttribute val internalId: String?,
                     @XmlAttribute val name: String?,
                     @XmlAttribute val templateFlag: Boolean?,
                     @XmlAttribute val type: String?,
                     @XmlAttribute val paused: Boolean?,
                     @XmlAttribute val uuid: String?,
                     @XmlAttribute val description: String?,
                     @XmlAttribute val projectName: String?,
                     @XmlAttribute val projectId: String?,
                     @XmlAttribute val projectInternalId: String?) {
    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null)
}