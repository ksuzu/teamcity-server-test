package dto

import javax.xml.bind.annotation.XmlAttribute

data class BuildType(@XmlAttribute val id: String,
                     @XmlAttribute val name: String,
                     @XmlAttribute val projectName: String,
                     @XmlAttribute val projectId: String) {

    private constructor() : this(
            "",
            "",
            "",
            "")
}