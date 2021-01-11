package org.informservice.criproman.distibuicaoAdministrativa

import grails.gorm.services.Service

@Service(PostoAdministrativo)
interface PostoAdministrativoService {

    PostoAdministrativo get(Serializable id)

    List<PostoAdministrativo> list(Map args)

    Long count()

    PostoAdministrativo delete(Serializable id)

    PostoAdministrativo save(PostoAdministrativo postoAdministrativo)

}
