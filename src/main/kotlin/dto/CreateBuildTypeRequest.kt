package dto

import javax.xml.bind.annotation.XmlAttribute

data class CreateBuildTypeRequest(
        @XmlAttribute val name: String,
        @XmlAttribute val projectId: String) {

}
