package model.domain.security;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "constraint")
@XmlAccessorType(XmlAccessType.FIELD)
public class Constraint {
    @XmlElement(name = "url-pattern")
    private String urlPattern;

    @XmlElement(name = "role")
    private String role;

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Constraint() {
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "urlPattern='" + urlPattern + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
