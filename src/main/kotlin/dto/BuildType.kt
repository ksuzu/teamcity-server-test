package dto

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class BuildType(@XmlAttribute val id: String?,
                     @XmlAttribute val name: String?,
                     @XmlAttribute val projectName: String?,
                     @XmlAttribute val projectId: String?) {
    private constructor() : this(
            null,
            null,
            null,
            null)

    constructor(name: String, projectId: String?) : this(
            null,
            name,
            null,
            projectId)


    @XmlAttribute
    val description: String? = null
    @XmlAttribute
    val internalId: String? = null
    @XmlAttribute
    val templateFlag: Boolean? = false
    @XmlAttribute
    val type: String? = null
    @XmlAttribute
    val paused: Boolean? = false
    @XmlAttribute
    val uuid: String? = null
    @XmlAttribute
    val projectInternalId: String? = null
}