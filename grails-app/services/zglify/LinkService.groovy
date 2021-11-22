package zglify

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import org.apache.commons.lang.RandomStringUtils
import zglify.base.DefaultService

@Service(Link)
abstract class LinkService extends DefaultService {

/*  abstract Link get(Serializable id)
    abstract List<Link> list(Map args)
    abstract void delete(Serializable id)
    abstract Link save(Link link) */

    abstract Long count()


    @Transactional
    Link register(Link link) {
        if (!link.originalLink.contains('http://') && !link.originalLink.contains('https://')) {
            link.originalLink = 'https://' + link.originalLink
        }
        link.shortLink = generateShortLink()
        save(link)

        return link
    }

    String getRedirectUrl(String shortLink) {
        Link link = Link.createCriteria().get {
            eq('shortLink', shortLink)
        }
        incrementRedirectCounter(link)
        return link?.originalLink
    }

    def getDetails() {
        Map details = [:]
        def detailsAux = Link.createCriteria().get {
            projections {
                count('id')
                sum('redirectCounter')
            }
        }
        details.urlEncurtadas = detailsAux[0]
        details.acessos = detailsAux[1]
        return details
     }

    private String generateShortLink() {
        def randomUrlLength = 5
        def randomShortUrl = RandomStringUtils.randomAlphanumeric(randomUrlLength)

        return randomShortUrl
    }

    private incrementRedirectCounter(Link link) {
        link.redirectCounter += 1
        save(link)
    }
}
