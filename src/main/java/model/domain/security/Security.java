package model.domain.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "security")
@XmlAccessorType(XmlAccessType.FIELD)
public class Security {

    @XmlElement(name = "constraint")
    private List<Constraint> constraints;


    public List<Constraint> getConstraints() {
        return constraints;
    }

    public Security() {
    }

    public Security(Security other) {
        this.constraints = other.constraints;
    }
}
