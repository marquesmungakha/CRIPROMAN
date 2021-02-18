package org.informservice.criproman.individuo

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.distibuicaoAdministrativa.Pais
import org.informservice.criproman.distibuicaoAdministrativa.Provincia

// import grails.plugins.orm.auditable.Auditable

import org.informservice.criproman.profissao.Profissao
import org.informservice.criproman.tipoDocumentoIdentificacao.TipoDocumentoIdentificacao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/individuo')
class Individuo implements Auditable {

    String nome
    String apelido
    String sexo
    Date dataNascimento
    Integer idade
    String naturalidade
    Pais nacionalidade
    Provincia provincia
    String localNascimento
    String telemovel1
    String telemovel2
    String estadoCivil
    String morada
    String numDocumentoIndentificacao
    TipoDocumentoIdentificacao tipoDocumento
    Date documentoValidade
    Profissao profissao
    String ocupacao
    byte[] fotografia
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static constraints = {
        uuid nullable: true
        provincia(nullable: false, blank: false)
        numDocumentoIndentificacao(nullable: false, blank: false)
        documentoValidade(nullable: true, blank: true, validator: { documentoValidade, urc ->
            return ((documentoValidade > new Date()))
        })
        telemovel1(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        telemovel2(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        provincia(nullable: true)
        profissao(nullable: true)
        ocupacao(nullable: true)
        nacionalidade(nullable: true)
        sexo(inList: ['Masculino', 'Femenino'])
        estadoCivil(inList: ['Casado(a)', 'Solteiro(a)', 'Viúvo(a)', 'Disvorciado(a)', 'Vive Maritalmente(a)', 'Outro(a)'])
        naturalidade(nullable: true, maxSize: 50)
        morada(nullable: true, maxSize: 500)
        nome(nullable: false, unique: ['apelido', 'sexo', 'dataNascimento'], blank: false)
        apelido(nullable: false, maxSize: 50, blank: false)
        dataNascimento(nullable: false, blank: false, validator: { datanascimento, urc ->
            return ((datanascimento <= new Date()))
        })
        idade nullable: true
        localNascimento(nullable: true)
        fotografia(nullable: true)
    }

    String toString() {
        return nome + " " + apelido
    }
}
