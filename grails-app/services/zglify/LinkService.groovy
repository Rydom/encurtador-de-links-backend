package zglify

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import org.apache.commons.lang.RandomStringUtils

@Service(Link)
abstract class LinkService {

    abstract Link get(Serializable id)

    abstract List<Link> list(Map args)

    abstract Long count()

    abstract void delete(Serializable id)

    abstract Link save(Link link)

    @Transactional
    Link register(Link link) {
        link.shortLink = generateShortLink()
        save(link)
        return link
    }

    private String generateShortLink() {
        def baseUrl = "http://localhost:8080/"
        def randomString = RandomStringUtils.randomAlphanumeric(5)
        return baseUrl + randomString
    }

}
