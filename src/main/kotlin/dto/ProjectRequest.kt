package dto

import javax.xml.bind.annotation.XmlAttribute

data class ProjectRequest(@XmlAttribute val name: String?) {
    private constructor():this(null){}
}
