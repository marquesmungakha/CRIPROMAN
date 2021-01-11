package org.informservice.criproman.denuncia

import grails.gorm.services.Service

@Service(Denuncia)
interface DenunciaService {

    Denuncia get(Serializable id)

    List<Denuncia> list(Map args)

    Long count()

    Denuncia delete(Serializable id)

    Denuncia save(Denuncia denuncia)

}
