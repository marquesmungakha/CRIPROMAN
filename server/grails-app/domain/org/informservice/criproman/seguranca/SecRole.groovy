package org.informservice.criproman.seguranca

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
@Secured('ROLE_ADMIN')
@Resource(uri='/api/secRole')
class SecRole implements Serializable {

	private static final long serialVersionUID = 1

	String authority

	SecRole(String authority){
		this()
		this.authority = authority
	}

	static constraints = {
		authority nullable: false, blank: false, unique: true
	}

	static mapping = {
		cache true
	}

	String toString(){
		 authority
	}
}
