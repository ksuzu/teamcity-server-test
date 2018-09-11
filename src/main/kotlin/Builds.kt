import java.util.ArrayList
import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement

data class Builds(@XmlAttribute val count: String?,
                  @XmlElement val build: MutableList<Build> = ArrayList()) {
    constructor() : this(
            null,
            ArrayList())
}