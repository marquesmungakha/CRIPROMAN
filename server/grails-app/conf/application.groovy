//grails.plugin.springsecurity.filterChain.chainMap = [
//        //Stateless chain
////        [
////                pattern: '/**',
////                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
////        ],
//        [
//                pattern: '/api/**',
//                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
//        ],
//
//
//        //Traditional, stateful chain
//        [pattern: '/**', filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
//]

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.password.algorithm = 'bcrypt'

grails.plugin.springsecurity.userLookup.userDomainClassName = 'org.informservice.criproman.seguranca.SecUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'org.informservice.criproman.seguranca.SecUserSecRole'
grails.plugin.springsecurity.authority.className = 'org.informservice.criproman.seguranca.SecRole'

grails.plugin.springsecurity.useSecurityEventListener = true

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']],
        [pattern: '/api/management/**', access: ['isAuthenticated()']],
        [pattern: '/api/login', access: ['IS_AUTHENTICATED_ANONYMOUSLY']],
        [pattern: '/api/logout', access: ['isAuthenticated()']],
        [pattern: '/api/pais', access: ['permitAll']],
        [pattern: '/api/**', access: ['permitAll']]
//        [pattern: '/api/pais', access: ['isFullyAuthenticated()']],
//        [pattern: '/api/provincia', access: ['isFullyAuthenticated()']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/auth', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/api/**', filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
        [pattern: '/**', filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]

grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/'

grails.plugin.springsecurity.password.algorithm = 'bcrypt'


// Added by the Audit-Logging plugin:
grails.plugin.auditLog.auditDomainClassName = 'org.informservice.criproman.seguranca.Auditoria'


//grails.plugin.springsecurity.rest.token.storage.useJwt=true
//grails.plugin.springsecurity.rest.token.storage.jwt.useSignedJwt=true
//grails.plugin.springsecurity.rest.token.storage.jwt.useEncryptedJwt = true
grails.plugin.springsecurity.rest.token.storage.jwt.secret = 'edK2l1P0D4770W56B6Rckf1TErImPWcu'
grails.plugin.springsecurity.rest.token.storage.jwt.expiration = 3600
//grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'org.informservice.criproman.seguranca.AuthenticationToken'

grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'

grails.plugin.springsecurity.logout.postOnly = false

//grails.plugin.springsecurity.rest.token.rendering.usernamePropertyName='id'

/*
//gorm
grails.plugin.springsecurity.rest.token.storage.useGorm = true
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = 'org.sali.Rest.AuthenticationToken'
grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = 'token'
grails.plugin.springsecurity.rest.token.storage.gorm.usernamePropertyName = 'username'


//login
grails.plugin.springsecurity.rest.login.active=true
grails.plugin.springsecurity.rest.login.useJsonCredentials = true
grails.plugin.springsecurity.rest.login.failureStatusCode = 401
grails.plugin.springsecurity.rest.login.usernamePropertyName = 'username'
grails.plugin.springsecurity.rest.login.passwordPropertyName='password'
grails.plugin.springsecurity.rest.login.endpointUrl='/api/login'
grails.plugin.springsecurity.rest.login.useRequestParamsCredentials = false

//logout
grails.plugin.springsecurity.rest.logout.endpointUrl='/api/logout'

//token generation
grails.plugin.springsecurity.rest.token.generation.useUUID=false
grails.plugin.springsecurity.rest.token.generation.useSecureRandom=true

//token rendering
grails.plugin.springsecurity.rest.token.rendering.usernamePropertyName='username'
grails.plugin.springsecurity.rest.token.rendering.authoritiesPropertyName='roles'
grails.plugin.springsecurity.rest.token.rendering.tokenPropertyName='token'

//token validate
grails.plugin.springsecurity.rest.token.validation.useBearerToken = true

//if disable 'Bearer', you can configure a custom header.
//grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
//grails.plugin.springsecurity.rest.token.rendering.tokenPropertyName   access_token
//grails.plugin.springsecurity.rest.token.validation.headerName = 'x-auth-token'
grails.plugin.springsecurity.rest.token.validation.active=true
grails.plugin.springsecurity.rest.token.validation.endpointUrl='/api/validate'



//grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'
//grails.plugin.springsecurity.rest.token.validation.useBearerToken = false

//grails.plugin.springsecurity.password.algorithm = 'SHA-256'
//grails.plugin.springsecurity.password.hash.iterations = 1

//cors

cors.enabled=true
cors.url.pattern = '/api/ * '
cors.headers=[
		'Access-Control-Allow-Origin': '*',
		'Access-Control-Allow-Credentials': true,
		'Access-Control-Allow-Headers': 'origin, authorization, accept, content-type, x-requested-with,X-Auth-Token',
		'Access-Control-Allow-Methods': 'GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS',
		'Access-Control-Max-Age': 3600
]
*/