package dto

import dto.Build
import java.util.ArrayList
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class Builds(@XmlAttribute val count: Long,
                  @XmlElement var build: MutableList<Build> = ArrayList()) {
    private constructor() : this(
            0,
            ArrayList())
}