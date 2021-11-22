package zglify.base

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class DefaultServiceSpec extends Specification implements ServiceUnitTest<DefaultService>{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
