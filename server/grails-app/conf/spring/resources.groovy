import org.informservice.criproman.seguranca.SecUserPasswordEncoderListener
import org.informservice.criproman.seguranca.CorsFilter
import org.informservice.criproman.seguranca.CustomAppRestAuthTokenJsonRenderer
import org.informservice.criproman.seguranca.CustomSecurityEventListener
// Place your Spring DSL code here
beans = {
    secUserPasswordEncoderListener(SecUserPasswordEncoderListener)
    corsFilterTest(CorsFilter)
    accessTokenJsonRenderer(CustomAppRestAuthTokenJsonRenderer)
    customerSecurityEventListener(CustomSecurityEventListener)
}
