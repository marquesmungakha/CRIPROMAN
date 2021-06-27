package org.informservice.criproman.funcao

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/funcao')
class Funcao { //implements Auditable {

    String codigo
    String designacao
     String missao

    static mapping = {
        version false
    }

    static constraints = {
        codigo(nullable: false, unique: true, blank: false)
        designacao(nullable: false, unique: ["codigo"], size: 0..100, blank: false)
        missao(nullable: true, size: 0..999, blank: true)
    }

    String toString() {
        return "${designacao}"
    }
}
