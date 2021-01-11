package criproman

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils
import org.informservice.criproman.distibuicaoAdministrativa.Pais
import org.informservice.criproman.seguranca.SecRole
import org.informservice.criproman.seguranca.SecUser
import org.informservice.criproman.seguranca.SecUserSecRole

class BootStrap {

    def springSecurityService

    def init = {

        addTestUsers()

        def pais = new Pais()
        pais.codigo = '01'
        pais.designacao = 'Mocambique'
        pais.nacionalidade = 'Mocambicana'
        pais.save()

        println pais

    }

    @Transactional
    void addTestUsers(){

        SpringSecurityUtils.clientRegisterFilter("corsFilterTest",
                SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order - 1)

        def adminRole = new SecRole('ROLE_ADMIN').save()
        def userRole = new SecRole('ROLE_USER').save()

        def testUser = new SecUser('me', 'password','me').save()
        def adminUser = new SecUser('admin', 'admin','admin').save()

        SecUserSecRole.create testUser, userRole
        SecUserSecRole.create adminUser, adminRole

        SecUserSecRole.withSession {
            it.flush()
            it.clear()
        }

        assert SecUser.count() == 2
        assert SecRole.count() == 2
        assert SecUserSecRole.count() == 2

        println SecRole.findByAuthority('ROLE_ADMIN')
        println SecRole.findByAuthority('ROLE_USER')
        println SecUser.findByUsernameAndPassword('me', 'password')
        println SecUser.findByUsernameAndPassword('admin','admin')
        println SecUser.findByUsername('admin').password
    }

    def destroy = {
    }
}
