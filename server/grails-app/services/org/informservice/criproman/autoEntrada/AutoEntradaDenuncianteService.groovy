package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaDenunciante)
interface AutoEntradaDenuncianteService {

    AutoEntradaDenunciante get(Serializable id)

    List<AutoEntradaDenunciante> list(Map args)

    Long count()

    AutoEntradaDenunciante delete(Serializable id)

    AutoEntradaDenunciante save(AutoEntradaDenunciante autoEntradaDenunciante)

}
