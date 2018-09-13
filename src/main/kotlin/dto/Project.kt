package dto

import javax.xml.bind.annotation.XmlAttribute

data class Project(@XmlAttribute val id: String?) {
    @XmlAttribute
    private val internalId: String? = null
    @XmlAttribute
    private val uuid: String? = null
    @XmlAttribute
    private val name: String? = null
    @XmlAttribute
    private val parentProjectId: String? = null
    @XmlAttribute
    private val parentProjectInternalId: String? = null
    @XmlAttribute
    private val parentProjectName: String? = null
    @XmlAttribute
    private val archived: Boolean? = null
    @XmlAttribute
    private val description: String? = null
    @XmlAttribute
    private val href: String? = null
    @XmlAttribute
    private val webUrl: String? = null
    @XmlAttribute
    private val parentProject: String? = null

    private constructor():this(null){}
}
