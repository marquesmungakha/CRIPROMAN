package org.informservice.criproman.seguranca

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
@Secured('ROLE_ADMIN')
@Resource(uri='/api/SecUser')
class SecUser implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    String fullName
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    SecUser(String username,String password, String fullname){
        this()
        this.username = username
        this.password = password
        this.fullName = fullname
    }

    Set<SecRole> getAuthorities() {
        (SecUserSecRole.findAllBySecUser(this) as List<SecUserSecRole>)*.secRole as Set<SecRole>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        fullName nullable: false, blank: false
    }

    static mapping = {
	    password column: '`password`'
    }
}
