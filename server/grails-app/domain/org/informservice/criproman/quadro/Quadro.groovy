package org.informservice.criproman.quadro

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/quadro')
class Quadro implements Auditable{
    String numero
    String nome
    String apelido
    String sexo
    Integer idade
    String telemovel1
    String telemovel2
    byte[] fotografia
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
        uuid generator: 'uuid'
    }
    static constraints = {
        telemovel1(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        telemovel2(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        sexo(inList: ['Masculino', 'Femenino'])
        nome(nullable: false, unique: ['apelido', 'sexo', 'idade'], blank: false)
        apelido(nullable: false, maxSize: 50, blank: false)
        idade(validator: { idade -> return idade >= 18 })
        fotografia(nullable: true)
        numero(unique: true)
    }
}
