package dto

import javax.xml.bind.annotation.XmlAttribute

data class Project(@XmlAttribute val id: String) {
    private constructor():this(""){}
}
