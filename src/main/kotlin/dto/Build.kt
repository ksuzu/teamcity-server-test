package dto

import javax.xml.bind.annotation.XmlAttribute

data class Build(@XmlAttribute val id: Int,
                 @XmlAttribute val buildTypeId: String,
                 @XmlAttribute val taskId: String,
                 @XmlAttribute val status: String,
                 @XmlAttribute val name: String
) {
    private constructor() : this(0, "", "", "", "")
}